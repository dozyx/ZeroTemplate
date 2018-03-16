package com.zerofate.template.justfortest;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
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
import com.zerofate.template.justfortest.lifecycleArch.*;
import com.zerofate.template.justfortest.lifecycleArch.User;

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
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
        userModel = ViewModelProviders.of(this).get(UserModel.class);
        userModel.setName("你好2018");
        userModel.getUser().observe(this,
                new Observer<com.zerofate.template.justfortest.lifecycleArch.User>() {
                    @Override
                    public void onChanged(@Nullable User user) {
                        text1.setText(user.getName());
                    }
                });
    }

    @OnClick(R.id.btn_hello)
    void onBtnHello() {
//        userModel.setName("你好");
        userModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                text1.setText(user.getName() +"111");
            }
        });
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                final User user = new User();
                user.setName(editTest.getText().toString());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                editTest.post(new Runnable() {
                    @Override
                    public void run() {
                        userModel.getUser().setValue(user);
                    }
                });
            }
        }).start();*/
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
