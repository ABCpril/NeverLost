package com.abc666.neverlost.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abc666.neverlost.R;

public class SecurityActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        Button btn_ok = findViewById(R.id.btn_sphoneOk);
        Button btn_cancle = findViewById(R.id.btn_sphoneCancle);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_sphone_number=(EditText)findViewById(R.id.et_sphoneNumber);
                EditText et_security_message=(EditText)findViewById(R.id.et_locKey);
                if(!et_sphone_number.getText().toString().isEmpty()){
                    if(!et_security_message.getText().toString().isEmpty()){
                        Toast.makeText(SecurityActivity.this,"当您的手机丢失后，只要使用安全手机向您的丢失手机发送安全短信内容，则会回复您丢失手机的当前位置",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.putExtra("sphone_number",et_sphone_number.getText().toString());
                        intent.putExtra("security_message",et_security_message.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }else{
                        Toast.makeText(SecurityActivity.this,"请输入您设置的安全短信内容",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(SecurityActivity.this,"请输入您绑定的安全手机号码",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
