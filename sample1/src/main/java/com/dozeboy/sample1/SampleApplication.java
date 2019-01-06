package com.dozeboy.sample1;


import com.dozeboy.sample1.di.AppComponent;
import com.dozeboy.sample1.di.DaggerAppComponent;
import com.zerofate.androidsdk.base.BaseApplication;

/**
 * @author dozeboy
 * @date 2019/1/6
 */
public class SampleApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder().application(sInstance).build();
    }

}
