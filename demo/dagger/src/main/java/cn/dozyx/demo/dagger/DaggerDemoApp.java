package cn.dozyx.demo.dagger;

import cn.dozyx.demo.dagger.di.AppComponent;
import cn.dozyx.demo.dagger.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * https://github.com/iammert/dagger-android-injection
 * Create by timon on 2019/6/27
 **/
public class DaggerDemoApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
