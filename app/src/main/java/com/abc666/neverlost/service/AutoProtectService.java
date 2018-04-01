package com.abc666.neverlost.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;

import com.blankj.ALog;
import com.abc666.neverlost.module.SoundPlayer;
import com.abc666.neverlost.ui.MainActivity;


public class AutoProtectService extends Service {


    private SoundPlayer soundPlayer;

    private boolean isUsbGuard = false;
    private boolean isUsbIn = false;


    private final BroadcastReceiver BatteryChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                return;
            }
            int values = intent.getIntExtra("plugged", 0);
            ALog.d(values);
            if (values != BatteryManager.BATTERY_STATUS_CHARGING) {
                isUsbIn = false;
                if (isUsbGuard) {
                    //ServiceUtils.startService(WarningService.class);
                    Vibrator mvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    mvibrator.vibrate(new long[]{100,10,100,1000}, 0);
                }
            } else {
                if (!isUsbGuard && !isUsbIn) {
                    //没插USB, isUsbGuard为false的情况下，接通USB，就播放防护提示音
                    //soundPlayer.playProtectTone();

                }
                isUsbIn = true;
                isUsbGuard = true;
            }
        }
    };



    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        soundPlayer = new SoundPlayer(this);
        final IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(BatteryChangedReceiver, mIntentFilter);
        Intent intent = new Intent(this, MainActivity.class);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundPlayer.release();
        unregisterReceiver(BatteryChangedReceiver);
        stopForeground(true);
    }

}
