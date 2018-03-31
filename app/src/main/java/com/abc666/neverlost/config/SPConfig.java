package com.abc666.neverlost.config;

import com.abc666.neverlost.MyApp;
import com.abc666.neverlost.R;



public class SPConfig {

    // 第一次打开应用
    public static final String START_APP_FIRST = "START_APP_FIRST";
    public static final boolean START_APP_FIRST_DEFAULT = false;
    // 自动防盗开启
    public static final String AUTO_PROTECT = "AUTO_PROTECT";
    public static final boolean AUTO_PROTECT_DEFAULT = false;
    // 口袋防盗
    public static final String AUTO_PROTECT_POCKET = "AUTO_PROTECT_POCKET";
    public static final boolean AUTO_PROTECT_POCKET_DEFAULT = true;
    // USB防盗
    public static final String AUTO_PROTECT_USB = "AUTO_PROTECT_USB";
    public static final boolean AUTO_PROTECT_USB_DEFAULT = true;

    // 开启提示音存储
    public static final String OPEN_SOUND_TONE_NAME = "OPEN_SOUND_TONE_NAME";
    public static final String OPEN_SOUND_TONE_NAME_DEFAULT = MyApp.getContext().getResources().getStringArray(R.array.sound_tones)[0];
    public static final String OPEN_SOUND_TONE_PATH = "OPEN_SOUND_TONE_PATH";
    public static final String OPEN_SOUND_TONE_RAW = "OPEN_SOUND_TONE_RAW";
    public static final int OPEN_SOUND_TONE_RAW_DEFAULT = 0;
    // 开启震动
    public static final String OPEN_VIBRATOR = "OPEN_VIBRATOR";
    public static final boolean OPEN_VIBRATOR_DEFAULT = true;
    /**
     * 防护提示
     */
    // 防护开启缓冲时间
    public static final String PROTECT_DELAY = "PROTECT_DELAY";
    public static final int PROTECT_DELAY_DEFAULT = 2;
    // 防护提示音存储
    public static final String PROTECT_SOUND_TONE_NAME = "PROTECT_SOUND_TONE_NAME";
    public static final String PROTECT_SOUND_TONE_NAME_DEFAULT = MyApp.getContext().getResources().getStringArray(R.array.sound_tones)[0];
    public static final String PROTECT_SOUND_TONE_PATH = "PROTECT_SOUND_TONE_PATH";
    public static final String PROTECT_SOUND_TONE_RAW = "PROTECT_SOUND_TONE_RAW";
    public static final int PROTECT_SOUND_TONE_RAW_DEFAULT = 0;
    // 防护震动
    public static final String PROTECT_VIBRATOR = "PROTECT_VIBRATOR";
    public static final boolean PROTECT_VIBRATOR_DEFAULT = true;
    // 报警开启缓冲时间
    public static final String WARNING_SOUND_DELAY = "PROTECT_WARNING_SOUND_DELAY";
    public static final int WARNING_SOUND_DELAY_DEFAULT = 5;
    // 报警音存储
    public static final String WARNING_SOUND_NAME = "WARNING_SOUND_TONE_NAME";
    public static final String WARNING_SOUND_NAME_DEFAULT = MyApp.getContext().getResources().getStringArray(R.array.sound_warning)[0];
    public static final String WARNING_SOUND_PATH = "WARNING_SOUND_PATH";
    public static final String WARNING_SOUND_RAW = "WARNING_SOUND_RAW";
    public static final int WARNING_SOUND_RAW_DEFAULT = 0;
    // 报警震动
    public static final String WARNING_VIBRATOR = "WARNING_VIBRATOR";
    public static final boolean WARNING_VIBRATOR_DEFAULT = true;

    private SPConfig() {
        throw new IllegalAccessError("Utility class");
    }
}
