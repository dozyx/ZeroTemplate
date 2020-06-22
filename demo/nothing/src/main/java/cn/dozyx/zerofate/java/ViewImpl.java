package cn.dozyx.zerofate.java;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Create by dozyx on 2019/11/1
 **/
public class ViewImpl implements IView {
    private BaseQuickAdapter adapter;
    public ViewImpl() {
    }

    @Override
    public <T> void showDatas(List<T> datas) {
        adapter.setNewData(datas);
    }
}
