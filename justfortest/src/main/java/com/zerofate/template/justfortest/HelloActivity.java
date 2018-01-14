package com.zerofate.template.justfortest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.zerofate.androidsdk.util.ToastX;
import com.zerofate.androidsdk.util.Utils;
import com.zerofate.androidsdk.util.ViewUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends Activity {


    @BindView(R.id.btn_hello)
    Button btnHello;
    @BindView(R.id.edit_test)
    EditText editTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.transparentStatusBar(this);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {
        ToastX.showShort(this, "onClick");
    }
}
