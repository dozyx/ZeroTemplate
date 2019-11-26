package cn.dozyx.demo.dagger.java;

import dagger.Component;

/**
 * @author dozyx
 * @date 2019-11-22
 */
@Component(modules = CarModule.class)
public interface MyComponent {
    public void inject(Car car);
}
