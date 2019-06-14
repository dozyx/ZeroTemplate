package cn.dozyx.zerofate.java.designpattern.decorator;

/**
 * @author dozeboy
 * @date 2018/11/20
 */
public class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "DarkRoast";
    }

    @Override
    public double cost() {
        return 2.99;
    }
}
