package edu.sfsu.cs.orange.ocr;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.json.JSONException;

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
                if (selectedOption.equals("JIRA")) {
                    try {
                        String type = "BLI";
                        String [] SummaryData = {"sum1","sum2","sum3"};
                        String bliParent = "";

//                        String type = "TASK";
//                        String [] SummaryData = ["task1","task2","task3"];
//                        bliParent = "";

                        sendToJira(type , SummaryData , bliParent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void sendToJira(final String type , final String [] SummaryData , final String bliParent) throws JSONException {

        ConnectionManager.getInstance(HandleResultActivity.this).getCookie(SummaryData ,new ConnectionManager.ServerRequestListener() {

            @Override
            public void onSuccess(Object data) {
                try {
                    String cookie = "";
                    createJiraItem(SummaryData , type , cookie , bliParent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Object data) {
<<<<<<< HEAD
                try {
                    int v = 9;


                } catch (Exception e) {
                    e.printStackTrace();
                }
=======
>>>>>>> http call

            }
        });
    }

<<<<<<< HEAD
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
=======
    }

    private void createJiraItem(String [] SummaryData , String type, String cookie , String bliParent ) throws JSONException {
        switch (type) {
            case "BLI":
                ConnectionManager.getInstance(HandleResultActivity.this).createBLI(cookie , SummaryData ,new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {


                    }

                    @Override
                    public void onError(Object data) {

                    }
                });
                break;
            case "TASK":
                ConnectionManager.getInstance(HandleResultActivity.this).createTask(cookie,SummaryData, bliParent , new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {


                    }

                    @Override
                    public void onError(Object data) {

                    }
                });

                break;
        }
>>>>>>> http call
    }
}
