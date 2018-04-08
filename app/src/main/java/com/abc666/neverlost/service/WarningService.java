package com.abc666.neverlost.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;

import com.abc666.neverlost.config.SPConfig;
import com.abc666.neverlost.module.SoundPlayer;




public class WarningService extends Service {

    private AudioManager audioManager;
    private int maxVolume;
    private Handler handler = new Handler();
    private SoundPlayer soundPlayer;
    /**
     * Alarm on
     */
    private final Runnable warningSoundRunnable = new Runnable() {
        @Override
        public void run() {
            // Play alarm sound
            soundPlayer.playWarning();
            // If the alarm is set, it will vibrate
        }
    };


    /**
     * Lock screen broadcast monitoring
     * ACTION_SCREEN_ON open screen
     * ACTION_SCREEN_OFF lock screen
     * ACTION_USER_PRESENT unlock
     */
    private final BroadcastReceiver screenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_USER_PRESENT)) { // 解锁
                // if unlock, turn off alarm service immediately WarningService
                stopSelf();
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
        // Initialization object
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        soundPlayer = new SoundPlayer(this);

        // Registered listener lock screen unlock status broadcast
        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenBroadcastReceiver, screenFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Set the volume to maximum
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        // If you set the alarm preparation vibration
        // Delayed opening alarm
        handler.postDelayed(warningSoundRunnable,3000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundPlayer.release();
        unregisterReceiver(screenBroadcastReceiver);
    }

}
