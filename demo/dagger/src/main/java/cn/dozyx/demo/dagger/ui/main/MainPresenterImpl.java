package cn.dozyx.demo.dagger.ui.main;

import cn.dozyx.demo.dagger.data.ApiService;

/**
 * Create by timon on 2019/6/27
 **/
public class MainPresenterImpl implements MainPresenter {
    MainView mainView;
    ApiService apiService;

    public MainPresenterImpl(MainView mainView, ApiService apiService) {
        this.mainView = mainView;
        this.apiService = apiService;
    }

    @Override
    public void loadMain() {
        apiService.loadData();
        mainView.onMainLoaded();
    }
}
