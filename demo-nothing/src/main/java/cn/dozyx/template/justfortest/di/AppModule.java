package cn.dozyx.template.justfortest.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author dozeboy
 * @date 2019/1/5
 */
@Module
public abstract class AppModule {
    @Singleton
    @Provides
    static Gson provideApp() {
        return new GsonBuilder().create();
    }
}
