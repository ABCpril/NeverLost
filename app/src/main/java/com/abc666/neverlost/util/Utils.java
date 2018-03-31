package com.abc666.neverlost.util;

import android.content.Context;

import com.abc666.neverlost.MyApp;



public final class Utils {

    private Utils() {
        throw new IllegalAccessError("Utility class");
    }

    public static Context getContext(){
        return MyApp.getContext();
    }
}
