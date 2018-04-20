package com.zerofate.template.justfortest;

import android.app.ProgressDialog;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
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
import java.util.concurrent.atomic.AtomicInteger;

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
        Log.d(TAG, new Uri.Builder().scheme("https").authority("www.example.com").appendPath(
                "articles").appendQueryParameter("title", "张三").fragment("first").toString()+"\n");
        Log.d(TAG, Uri.parse("https://www.example.com/articles").buildUpon().appendQueryParameter(
                "title", "张三").fragment("first").toString());
        Log.d(TAG,BuildConfig.ENV + "");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hello);
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

            }
        });
        Log.d(TAG,getString(R.string.style_text2));
//        binding.textLog.setText(getText(R.string.style_text));
//        binding.textLog.setText(Html.fromHtml(getString(R.string.style_text2)));
//        binding.textLog.setText(Html.fromHtml("<font color=\"#ff0000\">你好啊</font>你好啊<font "
//                + "color=\"#00ff00\">你好啊</font>"));
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
