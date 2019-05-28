package com.zerofate.androidsdk.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author dozeboy
 * @date 2017/12/12
 */

public class CountDownButton extends AppCompatButton {
    private CountDownTimer timer;
    private CountDownListener listener;
    private boolean isPrepared;

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化
     */
    public void configCountDown(long millisInFuture, long countDownInterval) {
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (listener != null) {
                    listener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onFinish();
                }
            }
        };
    }

    private void checkPrepared() {
        if (!isPrepared) {
            throw new IllegalStateException("You must invoke configCountDown before use!");
        }
    }

    /**
     * 重新开始倒计时
     */
    public void restart() {
        cancel();
        timer.start();
    }

    /**
     * 启动倒计时
     */
    public void start() {
        checkPrepared();
        timer.start();
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        checkPrepared();
        timer.cancel();
        if (listener != null) {
            listener.onCancel();
        }
    }

    public void setCountDownListener(CountDownListener listener) {
        this.listener = listener;
    }

    public interface CountDownListener {
        void onTick(long millisUntilFinished);

        void onCancel();

        void onFinish();
    }
}
