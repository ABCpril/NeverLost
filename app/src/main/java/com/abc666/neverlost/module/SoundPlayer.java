package com.abc666.neverlost.module;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.abc666.neverlost.R;
import com.abc666.neverlost.base.BaseSoundPlayer;
import com.abc666.neverlost.config.SPConfig;
import com.abc666.neverlost.ui.MainActivity;
import com.abc666.neverlost.util.SPUtils;



public class SoundPlayer extends BaseSoundPlayer {
    private MediaPlayer mediaPlayer;
    // 提示音raw资源数组
    private static int[] tones = new int[]{
            R.raw.tone0_car_lock,
            R.raw.tone1_robot_cop,
            R.raw.tone2_iron_man};
    // 报警声raw资源数组
    private static int[] warnings = new int[]{
            R.raw.warning0_didi,
            R.raw.warning1_police_car,
            R.raw.warning2_bibi};
    private BaseSoundPlayer baseSoundPlayer;
    private Context context;

    /**
     * 创建实例方法
     *
     * @param context 上下文
     */
    public SoundPlayer(Context context) {
        this.baseSoundPlayer = new BaseSoundPlayer();
        this.context = context;
    }

    /**
     * 播放开启提示音
     */
    public void playOpenTone() {

    }

    /**
     * 播放防护提示音
     */
    public void playProtectTone() {
        // 获取保存的raw序号
        int rawIdPos = SPUtils.getInstance().getInt(SPConfig.PROTECT_SOUND_TONE_RAW, SPConfig.PROTECT_SOUND_TONE_RAW_DEFAULT);
        // 判断是否要播放raw，因为默认raw为三个，所以不为3则播放的是raw
        if (rawIdPos != 3) {
            // 播放raw，提示音不循环
            playToneRaw(rawIdPos);
        } else {
            // 播放path，提示音不循环
            String path = SPUtils.getInstance().getString(SPConfig.PROTECT_SOUND_TONE_PATH);
            baseSoundPlayer.playPath(path, false);
        }
    }

    /**
     * 播放报警声
     */
    public void playWarning() {
        // 获取保存的raw序号
        int rawIdPos = SPUtils.getInstance().getInt(SPConfig.WARNING_SOUND_RAW, SPConfig.WARNING_SOUND_RAW_DEFAULT);
        // 判断是否要播放raw，因为默认raw为三个，所以不为3则播放的是raw
        if (rawIdPos != 3) {
            // 播放raw，报警声循环
            playWarningRaw(rawIdPos);
        } else {
            // 播放path，报警声循环
            String path = SPUtils.getInstance().getString(SPConfig.WARNING_SOUND_PATH);
            baseSoundPlayer.playPath(path, true);
        }
    }

    /**
     * 播放raw提示音
     *
     * @param rawIdPos raw资源序号
     */
    public void playToneRaw(int rawIdPos) {
        baseSoundPlayer.playRaw(context, tones[rawIdPos], false);
    }

    /**
     * 播放raw报警声
     *
     * @param rawIdPos raw资源序号
     */
    public void playWarningRaw(int rawIdPos) {
        baseSoundPlayer.playRaw(context, warnings[rawIdPos], true);
    }

    /**
     * 暂停播放
     */
    public void pause() {
        baseSoundPlayer.pause();
    }

    /**
     * 停止播放
     */
    public void stop() {
        baseSoundPlayer.stop();
    }

    /**
     * 释放资源
     */
    public void release() {
        baseSoundPlayer.release();
    }
}
