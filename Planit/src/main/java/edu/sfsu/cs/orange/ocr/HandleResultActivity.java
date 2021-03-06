package edu.sfsu.cs.orange.ocr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sfsu.cs.orange.ocr.network.ConnectionManager;
import edu.sfsu.cs.orange.ocr.utils.SendToEnum;
import edu.sfsu.cs.orange.ocr.utils.TextAnalizator;

public class HandleResultActivity extends Activity {
    private int bliCounter = 0;
    private int taskCounter = 0;
    private int sendType = 0;
    private HashMap bliResult = new HashMap();

    private TextAnalizator textAnalizator;
    private EditText captureResult;
    private Spinner spnSendTo;
    EditText task_number_edt;
    HashMap<String, String> hmBLIs = new HashMap<String, String>();
    Menu bliMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_result);
        String capture = getIntent().getExtras().getString("capture");
        textAnalizator = TextAnalizator.fromString(capture);
        captureResult = (EditText) findViewById(R.id.captureValue);
        captureResult.setText(textAnalizator.getBodyAsString());

        spnSendTo = (Spinner) findViewById(R.id.spn_sendto);
        final ArrayAdapter<SendToEnum> adapter = new ArrayAdapter<SendToEnum>(this, android.R.layout.simple_spinner_item, SendToEnum.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSendTo.setAdapter(adapter);
        int pos = adapter.getPosition(textAnalizator.getSendTo());
        spnSendTo.setSelection(pos);
        spnSendTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SendToEnum selVal = (SendToEnum) spnSendTo.getSelectedItem();
                LinearLayout llTask = (LinearLayout) findViewById(R.id.ll_task_number);
                llTask.setVisibility(selVal == SendToEnum.JIRA_TASK ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        task_number_edt = (EditText)findViewById(R.id.task_num);
        if (textAnalizator.getSendTo() == SendToEnum.JIRA_TASK){
            task_number_edt.setText(textAnalizator.getId());
        }

        task_number_edt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hmBLIs.clear();
                getBLI();
                return true;
            }
        });

        /*ImageButton jiraBtn = (ImageButton) findViewById(R.id.sendToJiraBtn);
        jiraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void submitUpdateResult(View v) {
                textAnalizator.setUpdatedBody(captureResult.getText().toString());
                String selectedOption = spnSendTo.getSelectedItem().toString();
                if (selectedOption.contains("JIRA")) {
                    try {
                        //      String type = "JIRA TASK";
                        // String [] SummaryData = {"sum11","sum22","sum33"};
                        // String [] SummaryData = {"sum1"};
                        // String bliParent = "";

//                        String type = "TASK";
                        // String [] SummaryData = {"task1","task2","task3"};
                        // String bliParent = "104";

                        sendToJira(selectedOption, textAnalizator.getBody(), textAnalizator.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (selectedOption.equals("Mail")) {
                    createMailItem(captureResult.getText());
                }
            }
        });*/
    }

    public void submitUpdateResult(View v) {
        textAnalizator.setUpdatedBody(captureResult.getText().toString());
        SendToEnum sendToType = (SendToEnum)spnSendTo.getSelectedItem();
        if (sendToType == SendToEnum.JIRA_TASK){
            textAnalizator.setId(task_number_edt.getText().toString());
        }
        switch (sendToType) {
            case JIRA_TASK:
            case JIRA_BLI:
                    try {
                        //      String type = "JIRA TASK";
                        // String [] SummaryData = {"sum11","sum22","sum33"};
                        // String [] SummaryData = {"sum1"};
                        // String bliParent = "";

//                        String type = "TASK";
                        // String [] SummaryData = {"task1","task2","task3"};
                        // String bliParent = "104";

                        sendToJira(sendToType.toString(), textAnalizator.getBody(), textAnalizator.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            case mail:
                createMailItem(captureResult.getText());
                break;
        }

    }

    private void sendToJira(final String type, final String[] SummaryData, final String bliParent) throws JSONException {

        // ConnectionManager.getInstance(HandleResultActivity.this).getCookie(SummaryData ,new ConnectionManager.ServerRequestListener() {

        //   @Override
        // public void onSuccess(Object data) {
        try {
            //String cookie = "DAE930B38B6BB9B0E9D7CADDC5734034";
            String cookie =  "D65970D30760D44FE4371C08281FF659";
            createJiraItem(SummaryData, type, cookie, bliParent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



          //  @Override
           // public void onError(Object data) {
            //    try {
             //       int v = 9;


     //           } catch (Exception e) {
      //              e.printStackTrace();
        //        }

          //  }
       // });

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    private void getBLI(){

        try {
            ConnectionManager.getInstance(HandleResultActivity.this).getBLIfromProject(new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    try {
                        JSONObject json = new JSONObject(data.toString());

                        JSONArray issuesArray = json.getJSONArray("issues");
                        for (int i = 0; i < issuesArray.length(); i++) {

                            JSONObject obj = issuesArray.getJSONObject(i);
                            String key = obj.getString("key");
                            JSONObject fieldObj = obj.getJSONObject("fields");

                            String summary = fieldObj.getString("summary");
                            JSONObject issueTypeObject = fieldObj.getJSONObject("issuetype");
                            String type = issueTypeObject.getString("id");
                            if (type.equals("6")) {
                                hmBLIs.put(key, summary);
                            }
                        }
                        onPrepareOptionsMenu(bliMenu);
                        //hm is the result of BLI


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Object data) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void createJiraItem(final String [] SummaryData , String type, final String cookie , final String bliParent ) throws JSONException {
        bliCounter = 0;
        switch (type) {
            case "JIRA BLI":
                ConnectionManager.getInstance(HandleResultActivity.this).createBLI(cookie, SummaryData, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        try {
                            bliCounter++;
                            JSONObject json = new JSONObject(data.toString());
                            String SBliLink = "https://sapjira.wdf.sap.corp/browse/" + json.get("key");
                            bliResult.put(json.get("key"),SBliLink);
                            if ( bliCounter == SummaryData.length){
                                showSuccessDialog("Back Log items", bliResult);
                                bliResult.clear();
                                bliCounter = 0;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Object data) {
                    }
                });
                break;
            case "JIRA Task":
                ConnectionManager.getInstance(HandleResultActivity.this).getBLIID(cookie, bliParent, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data)  {
                        taskCounter = 0;
                        try {
                            JSONObject json = new JSONObject(data.toString());
                            ConnectionManager.getInstance(HandleResultActivity.this).createTask(cookie, SummaryData, json.getString("id"), new ConnectionManager.ServerRequestListener() {
                                @Override
                                public void onSuccess(Object data) {
                                    try {
                                        taskCounter++;
                                        JSONObject json = new JSONObject(data.toString());
                                        String SBliLink = "https://sapjira.wdf.sap.corp/browse/" + json.get("key");
                                        bliResult.put(json.get("key"),SBliLink);
                                        if ( taskCounter == SummaryData.length){
                                            showSuccessDialog("New Created Task ", bliResult);
                                            bliResult.clear();
                                            bliCounter = 0;
                                        }

                                    } catch (JSONException e) {

                                    }

                                }

                                @Override
                                public void onError(Object data) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object data) {

                    }
                });
                break;
        }
    }

    private void createMailItem(Editable sText){
        PackageManager pm = getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<Intent> intentList = new ArrayList<Intent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;

            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));

            if (packageName.equals("com.whatsapp")) {
                intent.setPackage(packageName);
                intent.putExtra("chat", true);
                intentList.add(intent);
           }else if (packageName.equals("com.android.mms")) {
                Intent smsIntent = new Intent(Intent.ACTION_SEND);
                smsIntent.setComponent(intent.resolveActivity(pm));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body", sText);
                smsIntent.setPackage(packageName);
                intentList.add(smsIntent);
            }
            else if (packageName.equals("com.google.android.gm")) {
                sText.insert(0,"Hi,\n\nPlease review the MOM of today meeting.\n\n");
                sText.append("\n\nBest regards,\nLimor Erez");
                intent.setType("text/plain"); // put here your mime type
                intent.putExtra(Intent.EXTRA_TEXT, sText);
                intent.putExtra(Intent.EXTRA_SUBJECT, "MOM of today meeting");
                intent.setPackage(packageName);
                intentList.add(intent);
            }
        }

        // convert intentList to array
        Intent openInChooser = Intent.createChooser(intentList.remove(0),"Message option choose");
        Intent[] extraIntents = intentList.toArray( new Intent[ intentList.size() ]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(openInChooser);
    }

    private void showSuccessDialog(String title, HashMap<String, String> jiraResults){
        final TextView messageCtrl = new TextView(this);
        String resMessage = "";
        for (String key: jiraResults.keySet()) {
            SpannableString s = new SpannableString(jiraResults.get(key));
            resMessage += key + " : " +  s + "\n\n";
        }

        messageCtrl.setText(resMessage);
        messageCtrl.setAutoLinkMask(RESULT_OK);
        messageCtrl.setMovementMethod(LinkMovementMethod.getInstance());
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(R.drawable.planitlogo2)
                .setView(messageCtrl)
                .create();

        d.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*HashMap<String, String> hash = new HashMap<String, String>();
        hash.put("AAA", "BBB");
        hash.put("CCC", "DDD");*/
        int iKey = 0;
        for (String key: hmBLIs.keySet() ) {
            menu.add(Menu.NONE,  0, iKey, key);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bli_menu, menu);
        bliMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        task_number_edt.setText(item.getTitle());
        return true;
    }
}
