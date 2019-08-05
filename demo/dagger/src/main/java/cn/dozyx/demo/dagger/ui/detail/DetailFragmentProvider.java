package cn.dozyx.demo.dagger.ui.detail;

import cn.dozyx.demo.dagger.ui.detail.fragment.DetailFragment;
import cn.dozyx.demo.dagger.ui.detail.fragment.DetailFragmentModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Create by timon on 2019/6/27
 **/
@Module
public abstract class DetailFragmentProvider {
    @ContributesAndroidInjector(modules = DetailFragmentModule.class)
    abstract DetailFragment provideDetailFragmentFactory();
}
