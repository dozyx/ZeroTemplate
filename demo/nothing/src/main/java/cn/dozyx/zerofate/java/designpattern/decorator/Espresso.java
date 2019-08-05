package cn.dozyx.zerofate.java.designpattern.decorator;

/**
 * 浓缩咖啡
 *
 * @author dozeboy
 * @date 2018/11/20
 */
public class Espresso extends Beverage {
    public Espresso() {
        description = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
