package com.dozeboy.android.template.base;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


/**
 * @author timon
 * @date 2018/11/6
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
