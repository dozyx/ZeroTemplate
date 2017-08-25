package com.zerofate.andoroid.zerotemplate.imageloader.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.zerofate.andoroid.zerotemplate.R;
import com.zerofate.andoroid.zerotemplate.imageloader.ImageLoader;
import com.zerofate.andoroid.zerotemplate.util.Constants;

import java.util.Arrays;
import java.util.List;

public class ImageLoaderActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    ImageLoader mImageLoader;
    private GridView mImageGridView;
    private BaseAdapter mImageAdapter;

    private boolean mIsGridViewIdle = true;
    private int mImageWidth = 0;
    private boolean mCanGetBitmapFromNetwork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        mImageWidth = (getResources().getDisplayMetrics().widthPixels - 20) / 3;// 以屏幕宽度的三分之一来计算缩放图尺寸，因为 GridView 在 XML 中设定为3列。这里感觉其实不是很严谨，觉得列数也应该动态设置才好，不过一个 sample 不需要这么严格吧

        mImageGridView = (GridView) findViewById(R.id.gridView);
        mImageAdapter = new ImageAdapter(this);
        mImageGridView.setAdapter(mImageAdapter);
        mImageGridView.setOnScrollListener(this);

        mImageLoader = ImageLoader.build(this);
    }

    public class ImageAdapter extends BaseAdapter {
        private List<String> mDatas = Arrays.asList(Constants.sImageUrls);
        private LayoutInflater mInflater;
        private Drawable mDefaultDrawable;

        public ImageAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mDefaultDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public String getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            // 复用 convertView
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.image_item, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ImageView imageView = holder.imageView;
            final String tag = (String) imageView.getTag();
            final String url = getItem(position);
            if (!url.equals(tag)) {
                imageView.setImageDrawable(mDefaultDrawable);
            }
            if (mIsGridViewIdle && mCanGetBitmapFromNetwork) { // 在滑动停止且网络可用时加载图片
                imageView.setTag(url);
                mImageLoader.bindBitmap(url, imageView, mImageWidth, mImageWidth);
            }
            return convertView;

        }

        private class ViewHolder {
            public ImageView imageView;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsGridViewIdle = true;
            mImageAdapter.notifyDataSetChanged();// 滑动停止时需要进行一次数据刷新，这样才能开始加载图片
        } else {
            mIsGridViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {

    }
}
