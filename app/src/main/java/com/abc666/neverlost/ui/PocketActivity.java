package com.abc666.neverlost.ui;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc666.neverlost.R;
import com.abc666.neverlost.module.SoundPlayer;
import com.abc666.neverlost.service.WarningService;
import com.abc666.neverlost.util.SharedUtils;
import com.blankj.ALog;

public class PocketActivity extends Activity implements SensorEventListener {

    SensorManager sm;
    Sensor s;
    private MediaPlayer mp;

    //State of phone
    private enum PocketProtectState {
        UNLOCK,
        IN_POCKET,
        START_GUARD,
        WARNING_READY
    }
    private PocketProtectState pocketProtectState = PocketProtectState.UNLOCK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket);

        //register sensor
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        s=sm.getDefaultSensor(s.TYPE_PROXIMITY);
    }



    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        sm.unregisterListener(this,s);
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sm.registerListener(this, s, sm.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放音乐实例
        //soundPlayer.release();
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //near state
        float f=event.values[0];
        if(f==0){
            pocketProtectState = PocketProtectState.IN_POCKET;

            Vibrator mvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            mvibrator.vibrate(2000);


            pocketProtectState = PocketProtectState.START_GUARD;

        } else { //far state
            if (pocketProtectState == PocketProtectState.START_GUARD) {
                //play diffrent sound
                int rawIdPos = SharedUtils.getInt(this,"WARNING_SOUND_RAW", 0);
                ALog.d(rawIdPos);

                if (rawIdPos==0){
                    mp= MediaPlayer.create(PocketActivity.this, R.raw.warning0_didi);//重新设置要播放的音频
                    mp.setLooping(true);
                    mp.start();
                } else {
                    if (rawIdPos==1){
                        mp= MediaPlayer.create(PocketActivity.this, R.raw.warning1_police_car);//重新设置要播放的音频
                        mp.setLooping(true);
                        mp.start();
                    } else {
                        mp= MediaPlayer.create(PocketActivity.this, R.raw.warning2_bibi);//重新设置要播放的音频
                        mp.setLooping(true);
                        mp.start();
                    }
                }


                boolean isWarningVibrator = SharedUtils.getBoolean(this, "WarningVibrator", true);
                if (isWarningVibrator){
                    Vibrator myvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    myvibrator.vibrate(new long[]{100,10,100,1000}, 0);
                }
            }
        }
    }

}
