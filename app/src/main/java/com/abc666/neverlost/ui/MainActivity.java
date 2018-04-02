package com.abc666.neverlost.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.skyfishjy.library.RippleBackground;
import com.abc666.neverlost.R;
import com.abc666.neverlost.base.BaseAppCompatActivity;
import com.abc666.neverlost.config.SPConfig;
import com.abc666.neverlost.module.SoundPlayer;
import com.abc666.neverlost.service.AutoProtectService;
import com.abc666.neverlost.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class MainActivity extends BaseAppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnTouchListener {


    @BindView(R.id.tb_lock)
    ToggleButton tbLock;
    @BindView(R.id.fab_setting)
    FloatingActionButton fabSetting;

    @BindView(R.id.ripple_back)
    RippleBackground rippleBack;

    private boolean isProtectTips = true;
    private boolean mIsExit = false;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放音乐实例
        soundPlayer.release();
    }

    @SuppressLint("NewApi")
    private void initView() {
        // 实例化对象
        soundPlayer = new SoundPlayer(this);

        if(!tbLock.isChecked()){
            rippleBack.startRippleAnimation();
        }
        tbLock.setOnCheckedChangeListener(this);
        tbLock.setOnTouchListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (resultCode == 1) {
            }
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        // 保存开启状态
        //SPUtils.getInstance().put(SPConfig.AUTO_PROTECT, isChecked);
        // 播放防盗开启提示音
        Vibrator mvibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mvibrator.vibrate(2000);

        if (isChecked) {
            // 关闭波纹效果
            rippleBack.stopRippleAnimation();
            // 开启自动防盗服务
            Intent startIntent = new Intent(this, AutoProtectService.class);
            startService(startIntent);//启动服务
            Intent poIntent=new Intent(MainActivity.this,PocketActivity.class);
            startActivity(poIntent);

        } else {
            // 显示波纹效果
            rippleBack.startRippleAnimation();
            // 关闭自动防盗
            Intent stopIntent = new Intent(this,AutoProtectService.class);
            stopService(stopIntent);//停止服务
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.tb_lock) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                rippleBack.stopRippleAnimation();
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                rippleBack.startRippleAnimation();
            }
        }
        return false;
    }

    /**
     * 绑定点击时间处理
     *
     * @param view 点击的view
     */
    @OnClick(R.id.fab_setting)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_setting:
                // 跳转到设置页面
                Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(settingIntent, 1);
                break;
            default:
                break;
        }
    }


}
