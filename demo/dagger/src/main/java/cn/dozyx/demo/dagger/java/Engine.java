package cn.dozyx.demo.dagger.java;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author dozyx
 * @date 2019-11-22
 */
public class Engine {
    public Map<String, String> modes;

    @Inject
    public Engine(Map<String, String> modes) {
        this.modes = modes;
    }

    public void start() {
        System.out.println("启动");
    }
}
