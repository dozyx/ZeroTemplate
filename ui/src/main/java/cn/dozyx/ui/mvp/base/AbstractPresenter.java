package cn.dozyx.ui.mvp.base;

/**
 * Create by T on 2019/11/26
 **/
public abstract class AbstractPresenter<V extends BaseView> implements BasePresenter {
    protected V view;

    public AbstractPresenter(V view) {
        this.view = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
