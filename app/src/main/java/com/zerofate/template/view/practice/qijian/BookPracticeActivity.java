package com.zerofate.template.view.practice.qijian;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.zerofate.template.view.practice.qijian.view.CustomView1;
import com.zerofate.template.view.practice.qijian.view.CustomView2;
import com.zerofate.template.view.practice.qijian.view.CustomView3;
import com.zerofate.template.view.practice.qijian.view.CustomView4;
import com.zerofate.template.view.practice.qijian.view.CustomView5;
import com.zerofate.template.view.practice.qijian.view.CustomView6;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 《Android自定义控件开发入门与实战》练习
 * Create by timon on 2019/5/11
 **/
public class BookPracticeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private static final List<Class> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = new ViewPager(this);
        prepareData();
        setupViewPager(viewPager);
        setContentView(viewPager);
    }

    private void prepareData() {
        views.add(CustomView6.class);
        views.add(CustomView5.class);
        views.add(CustomView4.class);
        views.add(CustomView3.class);
        views.add(CustomView2.class);
        views.add(CustomView1.class);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                LogUtils.d(container);
                View pageView = null;
                try {
                    pageView = (View) views.get(position).getDeclaredConstructor(Context.class).newInstance(
                            BookPracticeActivity.this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
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

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
    }
}
