package cn.dozyx.demo.dagger.ui.detail;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import cn.dozyx.demo.dagger.R;
import cn.dozyx.demo.dagger.ui.detail.fragment.DetailFragment;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Create by timon on 2019/6/27
 **/
public class DetailActivity extends DaggerAppCompatActivity implements DetailView {
    private static final String TAG = "DetailActivity";
    @Inject
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailPresenter.loadDetail();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,
                    DetailFragment.newInstance()).commitAllowingStateLoss();
        }
    }

    @Override
    public void onDetailLoaded() {
        Log.d(TAG, "onDetailLoaded: ");
    }
}
