package cn.dozyx.demo.dagger.ui.detail.fragment;

import dagger.Module;
import dagger.Provides;

/**
 * Create by timon on 2019/6/27
 **/
@Module
public class DetailFragmentModule {
    @Provides
    DetailFragmentView provideDetailFragmentView(DetailFragment detailFragment) {
        return detailFragment;
    }
}
