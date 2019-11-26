package cn.dozyx.demo.dagger.java;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.Multibinds;
import dagger.multibindings.StringKey;

/**
 * @author dozyx
 * @date 2019-11-22
 */
@Module
public abstract class CarModule {
    @Multibinds
    abstract Map<String, String> engineModes();

    @Provides
    @IntoMap
    @StringKey("key")
    public static String mode1() {
        return "值";
    }
}
