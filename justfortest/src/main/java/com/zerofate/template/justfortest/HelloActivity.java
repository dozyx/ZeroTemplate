package com.zerofate.template.justfortest;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yeahka.factorytools.ILeposService;
import com.zerofate.template.justfortest.exception.dialogfragment
        .IllegalStateExceptionTestDialogFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
    public static MyProgressDialog sMyProgressDialog;
    private String testString = "11111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hello)
    void onBtnHello() {
        textAutoSize.setText(formatIncome(getString(R.string.money_yuan, testString)));
        testString += testString;
//        startService(new Intent(this, MyService.class));
//        DialogUtil.showDialog(this);

    }


    private SpannableString formatIncome(String income) {
        SpannableString ss = new SpannableString(income);
        ss.setSpan(new AbsoluteSizeSpan(14, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    public static class MyProgressDialog extends ProgressDialog {
        public MyProgressDialog(Context context) {
            super(context);
        }
    }


}
