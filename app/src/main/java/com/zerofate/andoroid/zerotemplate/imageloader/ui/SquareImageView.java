package com.zerofate.andoroid.zerotemplate.imageloader.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 以宽作为边的正方形 ImageView
 */

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
