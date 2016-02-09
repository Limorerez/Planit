package edu.sfsu.cs.orange.ocr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HandleResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_result);
        String cature = getIntent().getExtras().getString("capture");
        EditText captureResult = (EditText) findViewById(R.id.captureValue);
        captureResult.setText(cature);
        Button jiraBtn = (Button) findViewById(R.id.sendToJiraBtn);
        jiraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToJira();
            }
        });
    }
    private void sendToJira(){
        //yossi put your code here !!!!!
    }
}
