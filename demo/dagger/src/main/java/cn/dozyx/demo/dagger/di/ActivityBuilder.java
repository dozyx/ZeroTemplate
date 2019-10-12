package cn.dozyx.demo.dagger.di;

import cn.dozyx.demo.dagger.ui.detail.DetailActivity;
import cn.dozyx.demo.dagger.ui.detail.DetailActivityModule;
import cn.dozyx.demo.dagger.ui.detail.DetailFragmentProvider;
import cn.dozyx.demo.dagger.ui.main.MainActivity;
import cn.dozyx.demo.dagger.ui.main.MainActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Create by dozyx on 2019/6/27
 **/
@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {DetailActivityModule.class, DetailFragmentProvider.class})
    abstract DetailActivity bindDetailActivity();
}
