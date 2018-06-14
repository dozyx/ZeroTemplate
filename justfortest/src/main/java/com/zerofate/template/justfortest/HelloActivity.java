package com.zerofate.template.justfortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zerofate.template.justfortest.databinding.ActivityHelloBinding;
import com.zerofate.template.justfortest.lifecycleArch.UserModel;

public class HelloActivity extends AppCompatActivity {

    private static final String TAG = "HelloActivity";
    private String testString = "11111";
    UserModel userModel;
    private TextView textView;
    MyViewModel myViewModel;
    ActivityHelloBinding binding;
    String text = "0";
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
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


}
