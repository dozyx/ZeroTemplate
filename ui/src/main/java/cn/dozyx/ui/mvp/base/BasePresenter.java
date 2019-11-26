package cn.dozyx.ui.mvp.base;

/**
 * 根据不同的技术，view 的注入和分离方式会有多种方式，比如构造器传入，setter 设置等。
 * 无论使用何种技术，都是注意生命周期的处理，避免在 view 退出时，仍然操作了 view。
 *
 * Create by T on 2019/11/26
 **/
public interface BasePresenter {
    void subscribe();

    void unsubscribe();
}
