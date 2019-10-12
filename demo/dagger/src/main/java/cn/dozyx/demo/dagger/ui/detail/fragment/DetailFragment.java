package cn.dozyx.demo.dagger.ui.detail.fragment;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Create by dozyx on 2019/6/27
 **/
public class DetailFragment extends DaggerFragment implements DetailFragmentView {
    private static final String TAG = "DetailFragment";
    @Inject
    DetailFragmentPresenter detailFragmentPresenter;

    public static DetailFragment newInstance() {

        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetailFragmentLoaded() {
        Log.d(TAG, "onDetailFragmentLoaded: ");
    }
}
