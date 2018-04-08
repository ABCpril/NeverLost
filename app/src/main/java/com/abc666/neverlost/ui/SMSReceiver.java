package com.abc666.neverlost.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import com.abc666.neverlost.util.SharedUtils;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        this.abortBroadcast();
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        //Get user-configured secure phone number and secure SMS
        String sphone_number = SharedUtils.getString(context,"sphone_number","unknown errors");
        String security_message = SharedUtils.getString(context,"security_message","unknown errors");
        //Get current location
        String return_loc = SharedUtils.getString(context,"return_loc","unknown errors");
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                //Determine whether to send location SMS
                Log.i("tag",msg.getOriginatingAddress());
                Log.i("tag",msg.getMessageBody());
                Log.i("tag",return_loc);
                Log.i("tag",sphone_number);
                Log.i("tag",security_message);
                if (msg.getOriginatingAddress().equals("+86"+sphone_number)) {
                    if(msg.getMessageBody().equals(security_message)){
                        SmsManager sm = SmsManager.getDefault();
                        sm.sendTextMessage(sphone_number,null,return_loc,null,null);
                       }
                }
            }
        }
    }
}