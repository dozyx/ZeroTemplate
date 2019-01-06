package com.dozeboy.sample1;

import com.dozeboy.sample1.di.ActivityScope;
import com.dozeboy.sample1.di.AppComponent;

import dagger.Component;

/**
 * @author dozeboy
 * @date 2019/1/7
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
