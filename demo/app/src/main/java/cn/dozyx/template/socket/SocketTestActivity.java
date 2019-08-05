package cn.dozyx.template.socket;

import android.os.Bundle;

import cn.dozyx.template.base.BaseShowResultActivity;

public class SocketTestActivity extends BaseShowResultActivity {
    @Override
    protected String[] getButtonText() {
        return new String[]{"扫描端口"};
    }

    @Override
    public void onButton1() {
        for (int i = 0; i < 65535; i++) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
