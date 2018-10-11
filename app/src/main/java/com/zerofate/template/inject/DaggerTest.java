package com.zerofate.template.inject;

import dagger.Component;

/**
 * @author dozeboy
 * @date 2018/9/20
 */
public class DaggerTest {
}


@Component(modules = AppModule.class)
interface AppComponent {
    App app();

    @Component.Builder
    interface Builder {
        @BindsInstance Builder userName(@UserName String userName);
        AppComponent build();
    }
}