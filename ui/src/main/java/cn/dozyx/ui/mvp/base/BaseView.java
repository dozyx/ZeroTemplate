package cn.dozyx.ui.mvp.base;

/**
 * 思考：BaseView 是否需要有 setPresenter 方法
 * 如果添加 setPresenter() 方法的话，BaseView 需要增加一个泛型
 * 不适用泛型的好处是这个 BaseView 可以更抽象，而不一定要与 presenter 搭配使用
 * Create by T on 2019/11/26
 **/
public interface BaseView {

}
