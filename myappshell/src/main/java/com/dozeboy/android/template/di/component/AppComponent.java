package com.dozeboy.android.template.di.component;

import com.dozeboy.android.template.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author dozeboy
 * @date 2018/11/7
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

}
