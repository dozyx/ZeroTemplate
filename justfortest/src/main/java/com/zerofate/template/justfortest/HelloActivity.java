package com.zerofate.template.justfortest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerofate.androidsdk.util.ToastX;

import butterknife.BindView;
import butterknife.OnClick;

public class HelloActivity extends Activity {


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
