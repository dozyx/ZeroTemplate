package cn.dozyx.demo.dagger.ui.detail.fragment;

import javax.inject.Inject;

/**
 * Create by dozyx on 2019/6/27
 **/
public class DetailFragmentPresenter {
    DetailFragmentView detailFragmentView;

    @Inject
    public DetailFragmentPresenter(DetailFragmentView detailFragmentView) {
        this.detailFragmentView = detailFragmentView;
        detailFragmentView.onDetailFragmentLoaded();
    }
}
