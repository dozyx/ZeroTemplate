package cn.dozyx.demo.dagger.ui.detail;

import cn.dozyx.demo.dagger.data.ApiService;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Create by timon on 2019/6/27
 **/
@Module
public abstract class DetailActivityModule {

    @Provides
    static DetailPresenter provideDetailPresenter(DetailView detailView, ApiService apiService) {
        return new DetailPresenterImpl(detailView, apiService);
    }

    @Binds
    abstract DetailView provideDetailView(DetailActivity detailActivity);
}
