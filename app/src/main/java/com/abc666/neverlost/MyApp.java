package com.abc666.neverlost;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.blankj.ALog;

import com.abc666.neverlost.ui.MainActivity;

import java.util.concurrent.Callable;



public class MyApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        // Global context assignment
        context = getApplicationContext();
        // Initialize ALog
        initALog();
    }


    /**
     * Initialize ALog
     */
    private void initALog() {
        ALog.Config config = ALog.init(this)
                .setLogSwitch(BuildConfig.DEBUG)
                .setConsoleSwitch(BuildConfig.DEBUG)
                .setGlobalTag(null)
                .setLogHeadSwitch(true)
                .setLog2FileSwitch(false)
                .setDir("")
                .setFilePrefix("")
                .setBorderSwitch(true)
                .setConsoleFilter(ALog.V)
                .setFileFilter(ALog.V)
                .setStackDeep(1);
        ALog.d(config.toString());
    }


    /**
     * @return Global context
     */
    public static Context getContext() {
        return context;
    }
}
