package cn.dozyx.demo.dagger.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Create by dozyx on 2019/6/27
 **/
@Module
public abstract class AppModule {
    @Binds
    abstract Context provideContext(Application application);
}
