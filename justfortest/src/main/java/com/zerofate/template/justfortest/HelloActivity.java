package com.zerofate.template.justfortest;

import android.app.ProgressDialog;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zerofate.androidsdk.util.Utils;
import com.zerofate.androidsdk.util.ViewUtil;
import com.zerofate.template.justfortest.databinding.ActivityHelloBinding;
import com.zerofate.template.justfortest.lifecycleArch.User;
import com.zerofate.template.justfortest.lifecycleArch.UserModel;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {

    private static final String TAG = "HelloActivity";
    private String testString = "11111";
    UserModel userModel;
    private TextView textView;
    MyViewModel myViewModel;
    ActivityHelloBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_hello);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.getMediator().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textLog.setText(binding.textLog.getText() +"\n" + s);
            }
        });
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setInt(new Random(99).nextInt());
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setString(testString);
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewModelProviders.of(HelloActivity.this).get(MyViewModel.class).getMediator().observe(

                        HelloActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String s) {
                                binding.textLog.setText(binding.textLog.getText() +"\n" +"哈哈哈哈" + s);
                            }
                        });
            }
        });
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
