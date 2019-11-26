package cn.dozyx.ui.mvp;

import android.os.Handler;

import cn.dozyx.ui.mvp.base.AbstractPresenter;

/**
 * Create by T on 2019/11/26
 **/
public class DemoPresenter extends AbstractPresenter<DemoView> {
    DemoPresenter(DemoView view) {
        super(view);
    }

    public void loadData() {
        new Handler().postDelayed(() -> view.showSomething(), 2000);
    }
}
