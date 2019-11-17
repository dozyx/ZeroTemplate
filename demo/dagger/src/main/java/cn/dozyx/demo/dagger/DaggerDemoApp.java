package cn.dozyx.demo.dagger;

import cn.dozyx.demo.dagger.di.AppComponent;
import cn.dozyx.demo.dagger.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 * https://github.com/iammert/dagger-android-injection
 *
 * 依赖注入的核心原则：A class shouldn’t know anything about how it is injected.
 *
 * {@link DaggerApplication} 实现了  {@link HasAndroidInjector} 接口
 * {@link HasAndroidInjector} 用于提供 {@link AndroidInjector}
 * {@link AndroidInjector} 为 android 的核心类型的具体子类型如 Activity、Fragment 实现成员的注入
 * {@link AndroidInjector} 接受一个泛型，表示要注入成员变量的实例的类型
 *
 * {@link DispatchingAndroidInjector} 是 {@link AndroidInjector} 的一个实现，用来为 Activity、Fragment 等由 framework
 * 构造实例的类型注入成员。
 *
 **/
public class DaggerDemoApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
