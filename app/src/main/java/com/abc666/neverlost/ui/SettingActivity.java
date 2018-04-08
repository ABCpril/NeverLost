package com.abc666.neverlost.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc666.neverlost.R;
import com.abc666.neverlost.util.SharedUtils;
import com.blankj.ALog;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    public String[] toneItems;
    public String[] warningItems;
    public String sphone_number = "";
    public String security_message = "";
    @BindView(R.id.sb_protect_usb)
    SwitchButton sbProtectUsb;
    @BindView(R.id.sb_open_vibrator)
    SwitchButton sbOpenVibrator;
    @BindView(R.id.sb_warning_vibrator)
    SwitchButton sbWarningVibrator;

    @BindView(R.id.tv_open_tone)
    TextView tvOpenTone;
    @BindView(R.id.ll_open_tone)
    LinearLayout llOpenTone;
    @BindView(R.id.tv_warning_sound)
    TextView tvWarningSound;
    @BindView(R.id.ll_warning_tone)
    LinearLayout llWarningTone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        InitView();
        Button btn_sphone = (Button) findViewById(R.id.btn_select_sphone);
        btn_sphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SecurityActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    //Initialize button and text content
    private void InitView() {
        toneItems = getResources().getStringArray(R.array.sound_tones);
        warningItems = getResources().getStringArray(R.array.sound_warning);
        tvOpenTone.setText(SharedUtils.getString(this,"OPEN_TUNE_NAME","KOKO"));
        tvWarningSound.setText(SharedUtils.getString(this,"WARNING_SOUND_NAME","didi"));
        boolean isProtectUSB = SharedUtils.getBoolean(this, "USBprotect", true);
        sbProtectUsb.setChecked(isProtectUSB);
        boolean isOpenVibrator = SharedUtils.getBoolean(this, "OpenVibrator", true);
        sbOpenVibrator.setChecked(isOpenVibrator);
        boolean isWarningVibrator = SharedUtils.getBoolean(this, "WarningVibratorr", true);
        sbWarningVibrator.setChecked(isWarningVibrator);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    sphone_number = data.getStringExtra("sphone_number");
                    security_message = data.getStringExtra("security_message");
                    SharedUtils.putString(SettingActivity.this,"sphone_number",sphone_number);
                    SharedUtils.putString(SettingActivity.this,"security_message",security_message);
                }vvvc
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //store choice
        SharedUtils.putBoolean(this, "USBprotect", sbProtectUsb.isChecked());
        SharedUtils.putBoolean(this, "OpenVibrator", sbOpenVibrator.isChecked());
        SharedUtils.putBoolean(this, "WarningVibrator", sbWarningVibrator.isChecked());
    }

    //choose music
    @OnClick({R.id.ll_open_tone, R.id.ll_warning_tone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_open_tone:
                showSoundDialog(toneItems,tvOpenTone);
                break;
            case R.id.ll_warning_tone:
                showWarningDialog(warningItems,tvWarningSound);
                break;
        }
    }

    //choose open sound
    private void showSoundDialog(final String[] items, final TextView textView){

        final int[] chooseDex = new int[1];
        AlertDialog dialog=new AlertDialog.Builder(this).setTitle("^_^")
                .setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chooseDex[0] =which;
                                ALog.d(which);
                                //soundPlayer.playToneRaw(which);
                            }
                        })
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        textView.setText(items[chooseDex[0]]);
                        SharedPreferences.Editor editor=getSharedPreferences("config",MODE_PRIVATE).edit();
                        editor.putInt("OPEN_TUNE_RAW",chooseDex[0]).commit();
                        editor.putString("OPEN_TUNE_NAME",items[chooseDex[0]]).commit();
                        ALog.d(items[chooseDex[0]]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //soundPlayer.stop();
                    }
                })
                .create();
        dialog.show();
    }

    //choose warning sound
    private void showWarningDialog(final String[] items, final TextView textView){

        final int[] chooseWDex = new int[1];
        AlertDialog dialog=new AlertDialog.Builder(this).setTitle("^_^")
                .setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chooseWDex[0] =which;
                                ALog.d(which);
                            }
                        })
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        textView.setText(items[chooseWDex[0]]);
                        SharedPreferences.Editor editor=getSharedPreferences("config",MODE_PRIVATE).edit();
                        editor.putInt("WARNING_SOUND_RAW",chooseWDex[0]).commit();
                        editor.putString("WARNING_SOUND_NAME",items[chooseWDex[0]]).commit();
                        ALog.d(items[chooseWDex[0]]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //soundPlayer.stop();
                    }
                })
                .create();
        dialog.show();
    }
}