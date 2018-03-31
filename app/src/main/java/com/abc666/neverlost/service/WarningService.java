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
import com.abc666.neverlost.util.SPUtils;



public class WarningService extends Service {

    private AudioManager audioManager;
    private int maxVolume;
    private Handler handler = new Handler();
    private SoundPlayer soundPlayer;
    /**
     * 警报开启
     */
    private final Runnable warningSoundRunnable = new Runnable() {
        @Override
        public void run() {
            // 播放报警声
            soundPlayer.playWarning();
            // 如果设置了报警震动就进行震动
        }
    };


    /**
     * 锁屏广播监控
     * ACTION_SCREEN_ON 开屏
     * ACTION_SCREEN_OFF 锁屏
     * ACTION_USER_PRESENT 解锁
     */
    private final BroadcastReceiver screenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_USER_PRESENT)) { // 解锁
                // 只要解锁，立刻关闭报警服务WarningService
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
        // 初始化对象
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        soundPlayer = new SoundPlayer(this);

        // 注册监听锁屏解锁状态广播
        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenBroadcastReceiver, screenFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 设置音量到最大
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        // 如果设置了报警预备震动就进行震动
        // 延时开启报警声
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
