package com.abc666.neverlost.ui;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc666.neverlost.R;

public class PocketActivity extends Activity implements SensorEventListener {

    SensorManager sm;
    Sensor s;

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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float f=event.values[0];
        if(f==0){
            pocketProtectState = PocketProtectState.IN_POCKET;
            Vibrator mvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            mvibrator.vibrate(2000);
            pocketProtectState = PocketProtectState.START_GUARD;

        }
        else{
            if (pocketProtectState == PocketProtectState.START_GUARD) {

                Vibrator myvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                myvibrator.vibrate(new long[]{100,10,100,1000}, 0);

            }


        }
    }

}
