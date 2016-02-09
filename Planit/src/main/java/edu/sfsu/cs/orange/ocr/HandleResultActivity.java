package edu.sfsu.cs.orange.ocr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class HandleResultActivity extends Activity {

    private int sendType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_result);
        String capture = getIntent().getExtras().getString("capture");
        EditText captureResult = (EditText) findViewById(R.id.captureValue);
        captureResult.setText(capture);

        final Spinner spnSendTo = (Spinner) findViewById(R.id.spn_sendto);
        ArrayAdapter<SendToEnum> adapter = new ArrayAdapter<SendToEnum>(this, android.R.layout.simple_spinner_item, SendToEnum.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSendTo.setAdapter(adapter);

        ImageButton jiraBtn = (ImageButton) findViewById(R.id.sendToJiraBtn);
        jiraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedOption = spnSendTo.getSelectedItem().toString();
                if (selectedOption.equals("JIRA")) {
                    sendToJira();
                }

            }
        });



    }
    private void sendToJira(){
        //yossi put your code here !!!!!
    }
}
