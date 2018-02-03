package com.zerofate.template.justfortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
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
    @BindView(R.id.text_auto_size)
    TextView textAutoSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_hello)
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
    }
}
