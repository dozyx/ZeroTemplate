package com.zerofate.androidsdk.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
