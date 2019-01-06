package com.dozeboy.sample1.di;

import android.app.Application;

import com.dozeboy.sample1.MainActivity;
import com.squareup.haha.perflib.Main;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author dozeboy
 * @date 2019/1/5
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Application application();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
