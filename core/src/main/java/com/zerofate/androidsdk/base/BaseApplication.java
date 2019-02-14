package com.zerofate.androidsdk.base;

import android.app.Application;

/**
 * @author dozeboy
 * @date 2019/1/6
 */
public class BaseApplication extends Application {
    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

}
