package cn.dozyx.demo.dagger.di;

import android.app.Application;

import javax.inject.Singleton;

import cn.dozyx.demo.dagger.DaggerDemoApp;
import cn.dozyx.demo.dagger.test.SingletonTest;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Create by dozyx on 2019/6/27
 **/
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class
})
public interface AppComponent extends AndroidInjector<DaggerApplication> {
    void inject(DaggerDemoApp app);

    @Override
    void inject(DaggerApplication instance);

    void inject(SingletonTest singletonTest);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
