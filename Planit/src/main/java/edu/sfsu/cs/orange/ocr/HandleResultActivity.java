package edu.sfsu.cs.orange.ocr;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.sfsu.cs.orange.ocr.network.ConnectionManager;
import edu.sfsu.cs.orange.ocr.utils.SendToEnum;
import edu.sfsu.cs.orange.ocr.utils.TextAnalizator;

public class HandleResultActivity extends Activity {

    private int sendType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_result);
        String capture = getIntent().getExtras().getString("capture");
        final TextAnalizator textAnalizator = TextAnalizator.fromString(capture);
        final EditText captureResult = (EditText) findViewById(R.id.captureValue);
        captureResult.setText(textAnalizator.getBodyAsString());

        final Spinner spnSendTo = (Spinner) findViewById(R.id.spn_sendto);
        ArrayAdapter<SendToEnum> adapter = new ArrayAdapter<SendToEnum>(this, android.R.layout.simple_spinner_item, SendToEnum.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSendTo.setAdapter(adapter);
        int pos = adapter.getPosition(textAnalizator.getSendTo());
        spnSendTo.setSelection(pos);

        ImageButton jiraBtn = (ImageButton) findViewById(R.id.sendToJiraBtn);
        jiraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void sendToJira(final String type, final String[] SummaryData, final String bliParent) throws JSONException {

        // ConnectionManager.getInstance(HandleResultActivity.this).getCookie(SummaryData ,new ConnectionManager.ServerRequestListener() {

        //   @Override
        // public void onSuccess(Object data) {
        try {
            String cookie = "DAE930B38B6BB9B0E9D7CADDC5734034";
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

    private void createJiraItem(final String [] SummaryData , String type, final String cookie , final String bliParent ) throws JSONException {
        switch (type) {
            case "JIRA BLI":
                ConnectionManager.getInstance(HandleResultActivity.this).createBLI(cookie, SummaryData, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
    int x = 1;

                    }

                    @Override
                    public void onError(Object data) {
int x = 2;
                    }
                });
                break;
            case "JIRA TASK":
                ConnectionManager.getInstance(HandleResultActivity.this).getBLIID(cookie, bliParent, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data)  {
                        try {
                            JSONObject json = new JSONObject(data.toString());
                            ConnectionManager.getInstance(HandleResultActivity.this).createTask(cookie, SummaryData, json.getString("id"), new ConnectionManager.ServerRequestListener() {
                                @Override
                                public void onSuccess(Object data) {
    int x = 10;

                                }

                                @Override
                                public void onError(Object data) {
int x = 11;
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
            intent.setType("text/plain"); // put here your mime type
            intent.putExtra(Intent.EXTRA_TEXT, sText);

            if (packageName.equals("com.whatsapp")) {
                intent.setPackage(packageName);
                intentList.add(intent);
           }else if (packageName.equals("com.android.mms")) {
                Intent smsIntent = new Intent(Intent.ACTION_SEND);
                smsIntent.setComponent(intent.resolveActivity(pm));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body", sText);
                smsIntent.setPackage(packageName);
                intentList.add(smsIntent);
            }
            else if (packageName.equals("ru.ok.android")) {
                intent.setPackage(packageName);
                intentList.add(intent);
            }
            else if (packageName.equals("com.google.android.gm")) {
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


}
