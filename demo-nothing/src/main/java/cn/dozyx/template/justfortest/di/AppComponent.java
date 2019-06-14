package cn.dozyx.template.justfortest.di;

import android.app.Application;

import com.google.gson.Gson;
import cn.dozyx.template.justfortest.GodActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author dozeboy
 * @date 2019/1/5
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    Application application();

    Gson gson();

    void inject(GodActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
