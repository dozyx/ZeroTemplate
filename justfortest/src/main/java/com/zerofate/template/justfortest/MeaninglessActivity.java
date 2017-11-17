package com.zerofate.template.justfortest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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
    private ViewTreeObserver viewTreeObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewTreeObserver = adPager.getViewTreeObserver();
//        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                Log.d(TAG, "onPreDraw: ");
//                return false;
//            }
//        });
        viewTreeObserver.addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                Log.d(TAG, "onDraw: ");
            }
        });

        viewTreeObserver.addOnGlobalFocusChangeListener(
                new ViewTreeObserver.OnGlobalFocusChangeListener() {
                    @Override
                    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                        Log.d(TAG, "onGlobalFocusChanged: ");
                    }
                });
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "onGlobalLayout: ");
            }
        });

        viewTreeObserver.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Log.d(TAG, "onScrollChanged: ");
            }
        });

        viewTreeObserver.addOnTouchModeChangeListener(
                new ViewTreeObserver.OnTouchModeChangeListener() {
                    @Override
                    public void onTouchModeChanged(boolean isInTouchMode) {
                        Log.d(TAG, "onTouchModeChanged: ");
                    }
                });
        viewTreeObserver.addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            @Override
            public void onWindowAttached() {
                Log.d(TAG, "onWindowAttached: ");
            }

            @Override
            public void onWindowDetached() {
                Log.d(TAG, "onWindowDetached: ");
            }
        });
        viewTreeObserver.addOnWindowFocusChangeListener(
                new ViewTreeObserver.OnWindowFocusChangeListener() {
                    @Override
                    public void onWindowFocusChanged(boolean hasFocus) {
                        Log.d(TAG, "onWindowFocusChanged: ");
                    }
                });
    }

    @Override
    public PagerAdapter getAdapter() {
        return new ImagePagerAdapter(this) {
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
