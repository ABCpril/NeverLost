package com.abc666.neverlost.base;

import android.content.Context;
import android.media.MediaPlayer;

import com.blankj.ALog;

public class BaseSoundPlayer extends MediaPlayer {

    private MediaPlayer mediaPlayer;

    /**
     * Create an instance method
     */
    public BaseSoundPlayer() {
        this.mediaPlayer = new MediaPlayer();
    }

    /**
     * Play sound (file path)
     * @param path file path
     * @param isLooping Whether to loop
     */
    public void playPath(String path, boolean isLooping) {
        try {
            //mediaPlayer.reset();
            // 设置文件路径
            mediaPlayer.setDataSource(path);
            // 异步准备装载文件
            mediaPlayer.prepareAsync();
            // 设置循环
            mediaPlayer.setLooping(isLooping);
            // 添加准备监听
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载准备完毕就播放
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 添加错误监听
        mediaPlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int i, int i1) {
                // 错误状态进行重置
                mediaPlayer.reset();
                return true;
            }
        });
    }


    /**
     * Play sound (raw resource)
     * @param context context
     * @param rawId Play raw resource id
     * @param isLooping Loop
     */
    public void playRaw(Context context, int rawId, boolean isLooping) {
        try {
            // 重置
            mediaPlayer.reset();
            // 设置播放raw资源id
            mediaPlayer = MediaPlayer.create(context, rawId);
            // 设置循环
            mediaPlayer.setLooping(isLooping);
            // 添加准备监听
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载准备完毕就播放
                    // 装载完毕回调
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 添加错误监听
        mediaPlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int i, int i1) {
                // 错误状态进行重置
                mediaPlayer.reset();
                return true;
            }
        });
    }

    /**
     * Pause playback
     */
    public void pause() {
        try {
            if (mediaPlayer != null && isPlaying()) {
                mediaPlayer.pause();
            }
        } catch (IllegalStateException e) {
            ALog.e(e);
        }
    }

    /**
     * Stop play
     */
    public void stop() {
        try {
            if (mediaPlayer != null && isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.seekTo(0);
            }
        } catch (IllegalStateException e) {
            ALog.e(e);
        }
    }

    /**
     * Release resources
     */
    public void release() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        } catch (IllegalStateException e) {
            ALog.e(e);
        }
    }

    /**
     * @return Is it playing
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}
