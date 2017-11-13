package com.zerofate.androidsdk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zerofate.androidsdk.R;

/**
 * @author dozeboy
 * @date 2017/11/13
 */

public abstract class AdPagerActivity extends AppCompatActivity {

    private ViewPager adPager;
    private Button jumpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ad_pager);
        adPager = (ViewPager) findViewById(R.id.ad_pager);
        jumpBtn = (Button) findViewById(R.id.jump_over);
        adPager.setAdapter(getAdapter());

        // TODO: 添加指示器
        adPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJump();
            }
        });
    }

    /**
     * 跳过，默认行为为直接 finish 此Activity
     */
    public void onJump() {
        finish();
    }

    /**
     * @return
     */
    public abstract PagerAdapter getAdapter();

}
