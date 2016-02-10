package edu.sfsu.cs.orange.ocr.utils;

import android.content.Context;

import com.google.gson.Gson;

import edu.sfsu.cs.orange.ocr.R;

/**
 * Created by I049014 on 10/02/2016.
 */
public class TextAnalizator {
    private SendToEnum sendTo = SendToEnum.JIRA;
    private String body;
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
                    sendTo = SendToEnum.JIRA;
                }
                else {
                    isTypeFound = false;
                }
            }
            if (valOfSendSplit.length > 1) {
                id = valOfSendSplit[1];
            }
        }

        body = "";
        int iStart = isTypeFound ? 1 : 0;
        for(int i = iStart; i < split.length ; i++){
            body += split[i] + "\n";
        }
    }

    public SendToEnum getSendTo() {
        return sendTo;
    }


    public String getBody() {
        return body;
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
}
