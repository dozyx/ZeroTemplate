package com.zerofate.template.justfortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerofate.androidsdk.util.ToastX;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {

    @BindView(R.id.btn_hello)
    CustomButton btnHello;
    @BindView(R.id.edit_test)
    CustomEditText editTest;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.image_clip)
    ImageView imageClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastX.showShort(this,"HelloActivity -> onCreate" + " & task == " + getTaskId());
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {
        Intent intent = new Intent(this, HelloActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToastX.showShort(this, "onNewIntent -> ");
    }

    @Override
    protected void onDestroy() {
        ToastX.showShort(this,"HelloActivity -> onDestroy" + " & task == " + getTaskId());
        super.onDestroy();
    }
}
