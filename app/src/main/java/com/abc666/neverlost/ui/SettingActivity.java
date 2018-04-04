package com.abc666.neverlost.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.abc666.neverlost.R;

public class SettingActivity extends AppCompatActivity {

    public String sphone_number = "";
    public String security_message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button btn_sphone = (Button)findViewById(R.id.btn_select_sphone);
        btn_sphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,SecurityActivity.class);
                startActivityForResult(intent,1);
            }
        });
        Intent intent_number = new Intent();
        Intent intent_message = new Intent();
        intent_number.putExtra("sphone_number",sphone_number);
        intent_message.putExtra("security_message",security_message);
        setResult(RESULT_OK,intent_number);
        setResult(RESULT_OK,intent_message);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    sphone_number = data.getStringExtra("sphone_number");
                    security_message = data.getStringExtra("security_message");
                }
        }
    }
}