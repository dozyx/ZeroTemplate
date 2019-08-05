package cn.dozyx.zerofate.java.designpattern.decorator;

/**
 * 摩卡
 *
 * @author dozeboy
 * @date 2018/11/20
 */
public class Mocha extends CondimentDecorator {

    Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return .20 + beverage.cost();
    }
}
