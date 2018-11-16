package com.zerofate.androidsdk.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 用于 Page 为图片的 ViewPager
 *
 * @author dozeboy
 * @date 2017/11/13
 */

public abstract class ImagePagerAdapter extends PagerAdapter {

    private Context context;

    public ImagePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        setImage(imageView, position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    /**
     * 为指定位置的 ImageView 设置图片
     */
    public abstract void setImage(ImageView imageView, int position);
}
