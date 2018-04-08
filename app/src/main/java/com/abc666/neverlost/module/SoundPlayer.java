package com.abc666.neverlost.module;

import android.content.Context;
import android.media.MediaPlayer;

import com.abc666.neverlost.R;
import com.abc666.neverlost.base.BaseSoundPlayer;
import com.abc666.neverlost.util.SharedUtils;
import com.blankj.ALog;


public class SoundPlayer extends BaseSoundPlayer {
    private MediaPlayer mediaPlayer;
    // Raw resource array(prompt)
    private static int[] tones = new int[]{
            R.raw.tone0_car_lock,
            R.raw.tone1_robot_cop,
            R.raw.tone2_iron_man};
    // Raw resource array(alarm)
    private static int[] warnings = new int[]{
            R.raw.warning0_didi,
            R.raw.warning1_police_car,
            R.raw.warning2_bibi};
    private BaseSoundPlayer baseSoundPlayer;
    private Context context;

    /**
     * Create an instance method
     *
     * @param context context
     */
    public SoundPlayer(Context context) {
        this.baseSoundPlayer = new BaseSoundPlayer();
        this.context = context;
    }

    /**
     * Play open tone
     */
    public void playOpenTone() {
        // 获取保存的raw序号
        int rawIdPos = SharedUtils.getInt(context,"OPEN_TUNE_RAW", 0);
        ALog.d(rawIdPos);
        playToneRaw(rawIdPos);

    }

    /**
     * Play alarm tone
     */
    public void playWarning() {
        // 获取保存的raw序号
        int rawIdPos = SharedUtils.getInt(context,"OPEN_WARNING_RAW", 0);
        ALog.d(rawIdPos);
        playWarningRaw(rawIdPos);
    }

    /**
     * Play prompt tone
     *
     */
    public void playToneRaw(int rawIdPos) {
        baseSoundPlayer.playRaw(context, tones[rawIdPos], false);
    }

    /**
     * Play raw alarm tone
     *
     */
    public void playWarningRaw(int rawIdPos) {
        baseSoundPlayer.playRaw(context, warnings[rawIdPos], true);
    }

    /**
     * Pause playback
     */
    public void pause() {
        baseSoundPlayer.pause();
    }

    /**
     * Stop play
     */
    public void stop() {
        baseSoundPlayer.stop();
    }

    /**
     * Release resources
     */
    public void release() {
        baseSoundPlayer.release();
    }
}
