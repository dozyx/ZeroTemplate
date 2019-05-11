package com.zerofate.template.view.practice.qijian;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zerofate.template.view.practice.qijian.chapter1.CustomView1;
import com.zerofate.template.view.practice.qijian.chapter1.CustomView2;
import com.zerofate.template.view.practice.qijian.chapter1.CustomView3;

import java.util.ArrayList;
import java.util.List;

/**
 * 《Android自定义控件开发入门与实战》练习
 * Create by timon on 2019/5/11
 **/
public class BookPracticeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private static final List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = new ViewPager(this);
        prepareData();
        setupViewPager(viewPager);
        setContentView(viewPager);
    }

    private void prepareData() {
        views.add(new CustomView3(this));
        views.add(new CustomView2(this));
        views.add(new CustomView1(this));
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View pageView = views.get(position);
                container.addView(pageView);
                return pageView;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
    }
}
