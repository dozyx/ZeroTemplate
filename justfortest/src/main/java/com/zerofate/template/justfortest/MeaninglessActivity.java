package com.zerofate.template.justfortest;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.zerofate.androidsdk.activity.AdPagerActivity;
import com.zerofate.androidsdk.adapter.ImagePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
public class MeaninglessActivity extends AdPagerActivity {

    private static final String TAG = "MeaninglessActivity";
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    public PagerAdapter getAdapter() {
        return new ImagePagerAdapter(this){
            @Override
            public void setImage(ImageView imageView, int position) {
                imageView.setImageResource(R.drawable.bg_0);
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }
}
