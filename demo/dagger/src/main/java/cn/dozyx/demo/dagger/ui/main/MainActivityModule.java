package cn.dozyx.demo.dagger.ui.main;

import cn.dozyx.demo.dagger.data.ApiService;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Create by dozyx on 2019/6/27
 **/
@Module
public abstract class MainActivityModule {
    @Provides
    static MainPresenter provideMainPresenter(MainView mainView, ApiService apiService) {
        return new MainPresenterImpl(mainView, apiService);
    }

    @Binds
    abstract MainView provideMainView(MainActivity mainActivity);
}
