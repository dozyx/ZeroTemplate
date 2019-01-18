package com.zerofate.template.interaction;

import com.zerofate.template.base.BaseShowResultActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusTest extends BaseShowResultActivity {
    @Override
    protected String[] getButtonText() {
        return new String[]{"register", "unregister", "post", "post from thread"};
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        final String info = "default:" + event.message + "\n thread == " + Thread.currentThread();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                appendResult(info);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventOnMainThread(MessageEvent event) {
        final String info = "main thread mode:" + event.message + "\n thread == " + Thread.currentThread();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                appendResult(info);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventOnBackgroundThread(MessageEvent event) {
        final String info = "background thread mode:" + event.message + "\n thread == " + Thread.currentThread();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                appendResult(info);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventOnAsyncThread(MessageEvent event) {
        final String info = "async thread mode:" + event.message + "\n thread == " + Thread.currentThread();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                appendResult(info);
            }
        });
    }

    @Override
    public void onButton1() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onButton2() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onButton3() {
        EventBus.getDefault().post(new MessageEvent("hello"));
    }

    @Override
    public void onButton4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new MessageEvent("hello from thread"));
            }
        }).start();
    }

    private static class MessageEvent {
        public final String message;

        public MessageEvent(String message) {
            this.message = message;
        }
    }
}
