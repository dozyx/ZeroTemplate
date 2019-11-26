package cn.dozyx.demo.dagger.java;

/**
 * @author dozyx
 * @date 2019-11-22
 */
public class Client {

    public static void main(String[] args) {
        Car car = new Car();
        DaggerMyComponent.create().inject(car);
        car.engine.start();
        System.out.println(car.engine.modes);
    }
}
