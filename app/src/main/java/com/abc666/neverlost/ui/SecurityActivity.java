package com.abc666.neverlost.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
                //Determine if a secure phone number is entered
                if(!et_sphone_number.getText().toString().isEmpty()) {
                    if (et_sphone_number.getText().toString().length() != 11) {
                        Toast.makeText(SecurityActivity.this, "Please enter the correct phone number", Toast.LENGTH_LONG).show();
                    } else {
                        //Determine whether the secure message content is entered
                        if (!et_security_message.getText().toString().isEmpty()) {
                            Intent intent = new Intent();
                            intent.putExtra("sphone_number", et_sphone_number.getText().toString());
                            intent.putExtra("security_message", et_security_message.getText().toString());
                            setResult(RESULT_OK, intent);
                            AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(SecurityActivity.this);
                            alertdialogbuilder.setMessage("When your mobile phone is lost, as long as you use a secure phone to send a secure SMS message to your lost phone, it will reply to the current location of your lost phone.");
                            alertdialogbuilder.setPositiveButton("OK", click);
                            AlertDialog alertdialog1 = alertdialogbuilder.create();
                            alertdialog1.show();
                            //finish();
                        } else {
                            Toast.makeText(SecurityActivity.this, "Please enter the security message content you set", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(SecurityActivity.this,"Please enter your secured mobile phone number",Toast.LENGTH_LONG).show();
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
    private DialogInterface.OnClickListener click=new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            finish();
        }
    };
}
