package com.zerofate.template.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zerofate.template.base.BaseShowResultActivity;

import java.net.Socket;

public class SocketTestActivity extends BaseShowResultActivity {
    @Override
    protected String[] getButtonText() {
        return new String[]{"扫描端口"};
    }

    @Override
    public void onButton1() {
        for (int i= 0;i<65535;i++){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
