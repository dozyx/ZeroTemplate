package com.zerofate.template.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.zerofate.template.base.BaseShowResultActivity;

public class ServiceLifeCycleActivity extends BaseShowResultActivity {

    private Intent serviceIntent;
    private ServiceConnection connection;
    private ServiceConnection connection2;
// 一开始怀疑多次绑定只调用 onBind 一次是否与绑定使用的是同一个ServiceConnection有关，事实证明多个客户端绑定的确只调用一次

    private SimpleService simpleService;

    @Override
    protected String[] getButtonText() {
        return new String[]{"start", "bind", "unbind", "stop", "bind app", "unbind app"};
    }

    @Override
    public void onButton1() {
        startService(serviceIntent);

    }

    @Override
    public void onButton2() {
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);
    }

    @Override
    public void onButton3() {
        unbindService(connection);
    }

    @Override
    public void onButton4() {
        stopService(serviceIntent);
//        simpleService.stopSelf();
    }

    @Override
    public void onButton5() {
        getApplicationContext().bindService(serviceIntent, connection, BIND_AUTO_CREATE);
    }

    @Override
    public void onButton6() {
        getApplicationContext().unbindService(connection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceIntent = new Intent(this, SimpleService.class);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                appendResult("onServiceConnected ");
                simpleService = ((SimpleService.ServiceBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                appendResult("onServiceDisconnected ");
            }
        };

        connection2 = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                appendResult("onServiceConnected ");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                appendResult("onServiceDisconnected ");
            }
        };

        setText("通过 adb 打印查看 Service 生命周期函数的回调。\n如果闪退，是因为服务还经过注册就执行解绑。");
        appendResult("\ngetApplication == " + getApplication() + "\ngetApplicationContext == "
                + getApplicationContext());
        appendResult("\n多个客户端绑定服务，onBind只在第一次绑定时回调");
        appendResult(
                "\n假如服务正处于运行状态，并且已执行过绑定和解绑，这时候进行绑定，同样不会会调用 "
                        + "onBind。即服务在第一次绑定时会将IBinder对象保存，后续直接传递给ServiceConnection对象，而不需要通过onBind");
        appendResult("\n在Context被销毁时，如果没有解绑将自动unbind");
        appendResult("\n服务有绑定的客户端，那么调用 stopService 是不能销毁该服务的，Service 的 StopSelf 同样不行。");
    }
}
