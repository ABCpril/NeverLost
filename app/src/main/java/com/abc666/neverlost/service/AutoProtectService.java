package com.abc666.neverlost.service;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;

import com.blankj.ALog;
import com.abc666.neverlost.module.SoundPlayer;
import com.abc666.neverlost.ui.MainActivity;


public class AutoProtectService extends Service {

    private SensorManager sensorManager;
    private KeyguardManager keyguardManager;
    private SoundPlayer soundPlayer;

    private boolean isUsbGuard = false;
    private boolean isUsbIn = false;
    private Handler handler = new Handler();

    private enum PocketProtectState {
        UNLOCK,
        IN_POCKET,
        START_GUARD,
        WARNING_READY
    }


    private PocketProtectState pocketProtectState = PocketProtectState.UNLOCK;

    private final SensorEventListener functionSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() != Sensor.TYPE_PROXIMITY) {
                return;
            }
            float proximityValue = event.values[0];

            pocketProtectState = PocketProtectState.UNLOCK;

            if (proximityValue == 0.0) {
                if (pocketProtectState == PocketProtectState.UNLOCK
                        && keyguardManager.inKeyguardRestrictedInputMode()) {

                    pocketProtectState = PocketProtectState.IN_POCKET;
                    handler.postDelayed(pocketGuardRunnable,5000);
//                    handler.postDelayed(pocketGuardRunnable,
//                            SPUtils.getInstance().getInt(SPConfig.PROTECT_DELAY, SPConfig.PROTECT_DELAY_DEFAULT) * 1000);

                }
            } else {
                if (pocketProtectState == PocketProtectState.START_GUARD) {

                    pocketProtectState = PocketProtectState.WARNING_READY;
//                    ServiceUtils.startService(WarningService.class);
                    Vibrator myvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    myvibrator.vibrate(new long[]{100,10,100,1000}, 0);

                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private final BroadcastReceiver screenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_USER_PRESENT)) {
                //ServiceUtils.stopService(WarningService.class);

//                Intent intent2 = new Intent(Utils.getContext(), WarningService.class);
//                Utils.getContext().stopService(intent2);

                pocketProtectState = PocketProtectState.UNLOCK;
                if (!isUsbIn) {
                    isUsbGuard = false;
                }
            }
        }
    };


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


    private final Runnable pocketGuardRunnable = new Runnable() {
        @Override
        public void run() {
            if (pocketProtectState == PocketProtectState.IN_POCKET) {
                //是否开启口袋模式
                Vibrator mvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                mvibrator.vibrate(2000);
                pocketProtectState = PocketProtectState.START_GUARD;
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
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        soundPlayer = new SoundPlayer(this);
        final IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(BatteryChangedReceiver, mIntentFilter);
        sensorManager.registerListener(functionSensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenBroadcastReceiver, filter);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundPlayer.release();
        sensorManager.unregisterListener(functionSensorListener);
        unregisterReceiver(screenBroadcastReceiver);
        unregisterReceiver(BatteryChangedReceiver);
        stopForeground(true);
    }

}
