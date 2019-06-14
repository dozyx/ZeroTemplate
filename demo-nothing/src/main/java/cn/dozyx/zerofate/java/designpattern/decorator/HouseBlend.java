package cn.dozyx.zerofate.java.designpattern.decorator;

/**
 * 首选咖啡
 *
 * @author dozeboy
 * @date 2018/11/20
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        description = "House Blend Coffee";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
