package com.zerofate.template.justfortest;

import android.app.Application;
import android.util.Log;

/**
 * @author Timon
 * @date 2017/11/13
 */

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler{
    private static final String TAG = "MyApplication";
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e(TAG, "uncaughtException: ",e);
        uncaughtExceptionHandler.uncaughtException(t,e);
    }
}
