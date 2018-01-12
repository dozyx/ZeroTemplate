package com.zerofate.template.justfortest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerofate.androidsdk.util.ToastX;
import com.zerofate.template.justfortest.databinding.ActivityHelloBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {


    @BindView(R.id.btn_hello)
    Button btnHello;
    @BindView(R.id.edit_test)
    EditText editTest;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.image_clip)
    ImageView imageClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelloBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_hello);
        ButterKnife.bind(this);
        ClipDrawable drawable = (ClipDrawable) imageClip.getDrawable();
        drawable.setLevel(1000);
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {
        Intent intent = new Intent(this,HelloActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToastX.showShort(this,"onNewIntent -> ");
    }
}
