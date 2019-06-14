package cn.dozyx.template.justfortest.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ActivityModule {
    @Provides
    static Gson provideApp() {
        return new GsonBuilder().create();
    }
}
