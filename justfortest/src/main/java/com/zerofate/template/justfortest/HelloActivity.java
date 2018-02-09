package com.zerofate.template.justfortest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yeahka.factorytools.ILeposService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {

    private static final String TAG = "HelloActivity";
    @BindView(R.id.btn_hello)
    Button btnHello;
    @BindView(R.id.edit_test)
    CustomEditText editTest;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text_auto_size)
    TextView textAutoSize;
    @BindView(R.id.image_clip)
    ImageView imageClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hello)
    void onBtnHello() {
        Log.d(TAG, "onBtnHello: " + "\nBuild.MODEL == " + Build.MODEL + "\nBuild.PRODUCT == "
                + Build.PRODUCT + "\nBuild.HARDWARE == " + Build.HARDWARE
                + "\nBuild.FINGERPRINT == "
                + Build.FINGERPRINT + "\nBuild.DEVICE == "
                + Build.DEVICE + "\nBuild.BOOTLOADER == " + Build.BOOTLOADER + "\nBuild.BRAND == "
                + Build.BRAND + "\nBuild.MANUFACTURER == " + Build.MANUFACTURER
                + "\nBuild.BOARD == "
                + Build.BOARD + "\nBuild.DISPLAY == "
                + Build.DISPLAY + "\nBuild.HOST == " + Build.HOST + "\nBuild.ID == " + Build.ID
                + "\nBuild.TAGS == " + Build.TAGS + "\nBuild.TYPE == " + Build.TYPE
                + "\nBuild.USER == " + Build.USER + "\nBuild.SUPPORTED_ABIS == "
                + Build.SUPPORTED_ABIS + "\nBuild.TIME == " + Build.TIME
                + "\nBuild.SUPPORTED_32_BIT_ABIS == "
                + Build.SUPPORTED_32_BIT_ABIS + "\nBuild.SUPPORTED_64_BIT_ABIS == "
                + Build.SUPPORTED_64_BIT_ABIS);
        Intent intent = new Intent("com.yeahka.action.leposservice");
        intent.setPackage("com.yeahka.factorytools");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ILeposService leposService = ILeposService.Stub.asInterface(service);
                if (leposService != null) {
                    try {
                        Toast.makeText(HelloActivity.this, leposService.readSn() + " & " + leposService.readCsn(), Toast
                                .LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}
