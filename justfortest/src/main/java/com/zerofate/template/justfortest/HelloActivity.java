package com.zerofate.template.justfortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.zerofate.androidsdk.util.ToastX;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_hello)
    public void onHello() {
        ToastX.showLong(this,"onHello");
    }
}
