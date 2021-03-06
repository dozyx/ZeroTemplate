package cn.dozyx.template;

import android.os.Bundle;
import android.os.CountDownTimer;

import cn.dozyx.template.base.BaseShowResultActivity;

public class CountDownActivity extends BaseShowResultActivity {
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                appendResult("onTick: " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                appendResult("onFinish: ");
            }
        };
    }

    @Override
    protected String[] getButtonText() {
        return new String[]{
                "开始", "停止"
        };
    }

    @Override
    public void onButton1() {
        timer.start();
    }

    @Override
    public void onButton2() {
        timer.cancel();
    }
}
