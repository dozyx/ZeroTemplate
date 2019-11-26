package cn.dozyx.ui.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 使用泛型 P 是为了消除模板代码，包括创建和关联生命周期
 * Create by T on 2019/11/26
 **/
public abstract class BaseMVPActivity<P extends AbstractPresenter> extends
        AppCompatActivity implements
        BaseView {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        if (presenter != null) {
            presenter.subscribe();
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.unsubscribe();
        }
        super.onDestroy();
    }

    protected abstract P createPresenter();
}
