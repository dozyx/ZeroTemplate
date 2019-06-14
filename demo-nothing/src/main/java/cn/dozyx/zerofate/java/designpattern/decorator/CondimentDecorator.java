package cn.dozyx.zerofate.java.designpattern.decorator;

/**
 * 配料抽象类
 *
 * @author dozeboy
 * @date 2018/11/20
 */
public abstract class CondimentDecorator extends Beverage {
    @Override
    public abstract String getDescription();
}
