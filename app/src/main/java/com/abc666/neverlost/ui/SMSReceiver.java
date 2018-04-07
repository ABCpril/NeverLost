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
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        String sphone_number = SharedUtils.getString(context,"sphone_number","未知错误");
        String security_message = SharedUtils.getString(context,"security_message","未知错误");
        String return_loc = SharedUtils.getString(context,"return_loc","未知错误");
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                if (msg.getOriginatingAddress().equals("+86"+sphone_number)) {
                    if(msg.getMessageBody().equals(security_message)){
                        SmsManager sm = SmsManager.getDefault();
                        sm.sendTextMessage(sphone_number,null,return_loc,null,null);
                        this.abortBroadcast();
                    }
                }
            }
        }
    }
}