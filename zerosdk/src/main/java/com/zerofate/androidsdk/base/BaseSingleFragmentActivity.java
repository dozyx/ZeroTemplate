package com.zerofate.androidsdk.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zerofate.androidsdk.R;

/**
 * @author dozeboy
 * @date 2018/7/1
 */
public abstract class BaseSingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(params);
        frameLayout.setId(View.generateViewId());
        setContentView(frameLayout);
        getSupportFragmentManager().beginTransaction().add(frameLayout.getId(),
                getFragment(getIntent())).commit();
    }

    /**
     * 提供Fragment
     *
     * @param startIntent 从启动 activity 的 intent 中获取参数
     * @return fragment
     */
    public abstract Fragment getFragment(Intent startIntent);
}
