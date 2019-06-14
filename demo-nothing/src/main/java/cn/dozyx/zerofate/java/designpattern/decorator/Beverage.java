package cn.dozyx.zerofate.java.designpattern.decorator;

/**
 * 饮料抽象类
 *
 * @author dozeboy
 * @date 2018/11/20
 */
public abstract class Beverage {
    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();

}
