package com.zerofate.template;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.zerofate.androidsdk.util.Utils;

/**
 * Created by Administrator on 2017/10/23.
 */

public class ZTApplication extends Application {
    private static final String TAG = "ZTApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        LogUtils.getConfig().setGlobalTag("ZeroTemplate");
        Log.d(TAG,
                "onCreate: app module BuildConfig.DEBUG == " + BuildConfig.DEBUG + " & sdk module BuildConfig.DEBUG == "
                        + Utils.isDebug());
        Stetho.initializeWithDefaults(this);
    }
}
