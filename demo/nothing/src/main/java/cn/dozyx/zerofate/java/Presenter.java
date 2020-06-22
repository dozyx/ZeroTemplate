package cn.dozyx.zerofate.java;

import cn.dozyx.core.debug.MockUtils;

/**
 * Create by dozyx on 2019/11/1
 **/
public class Presenter {
    private IView view;

    public void onRefresh() {
        if (view != null) {
            view.showDatas(MockUtils.generateList(String.class));
        }
    }
}
