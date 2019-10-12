package cn.dozyx.demo.dagger.ui.detail;

import javax.inject.Inject;

import cn.dozyx.demo.dagger.data.ApiService;

/**
 * Create by dozyx on 2019/6/27
 **/
public class DetailPresenterImpl implements DetailPresenter {
    DetailView detailView;
    ApiService apiService;

    @Inject
    public DetailPresenterImpl(DetailView detailView, ApiService apiService) {
        this.detailView = detailView;
        this.apiService = apiService;
    }

    @Override
    public void loadDetail() {
        apiService.loadData();
        detailView.onDetailLoaded();
    }
}
