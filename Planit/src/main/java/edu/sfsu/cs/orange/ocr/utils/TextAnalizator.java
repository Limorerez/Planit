package edu.sfsu.cs.orange.ocr.utils;

import android.content.Context;

import com.google.gson.Gson;

import edu.sfsu.cs.orange.ocr.R;

/**
 * Created by I049014 on 10/02/2016.
 */
public class TextAnalizator {
    private SendToEnum sendTo = SendToEnum.JIRA_BLI;
    private String[] body;
    private String id;
    final static Gson gson = new Gson();

    public TextAnalizator(Context context, String text) {
        boolean isTypeFound = true;
        String[] split = text.split("\n");
        if (split.length > 1) {
            String valOfSend = split[0].trim();
            String[] valOfSendSplit = valOfSend.split("-");
            if (valOfSendSplit.length > 0) {
                if (valOfSendSplit[0].toUpperCase().equals(context.getString(R.string.mail_identifier))) {
                    sendTo = SendToEnum.mail;
                } else if (valOfSendSplit[0].toUpperCase().equals(context.getString(R.string.jira_identifier))){
                    sendTo = SendToEnum.JIRA_BLI;
                }
                else if (valOfSendSplit[0].toUpperCase().equals(context.getString(R.string.task_identifier))){
                    sendTo = SendToEnum.JIRA_TASK;
                }
                else {
                    isTypeFound = false;
                }
            }
            if (valOfSendSplit.length > 1) {
                id = valOfSendSplit[1];
            }
        }
        else{
            isTypeFound = false;
        }

        int iStart = isTypeFound ? 1 : 0;
        int iBodyCounter = 0;
        body = new String[split.length - iStart];
        for(int i = iStart; i < split.length ; i++){
            body[iBodyCounter] = split[i] + "\n";
            iBodyCounter++;
        }
    }

    public SendToEnum getSendTo() {
        return sendTo;
    }

    public String[] getBody() {
        return body;
    }

    public String getBodyAsString() {
        String res = "";
        for(int i = 0; i < body.length ; i++){
            res += body[i];
        }
        return res;
    }

    public void setId(String sId) {
        id = sId;
    }
    public String getId() {
        return id;
    }

    public String asString() {
        return gson.toJson(this);
    }

    public static TextAnalizator fromString(String objAsString){
        TextAnalizator txtAnalizator =  gson.fromJson(objAsString, TextAnalizator.class);
        return txtAnalizator;
    }

    public void setUpdatedBody(String text){
        body = text.split("\n");
    }
}
