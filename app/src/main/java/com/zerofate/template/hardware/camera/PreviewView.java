package com.zerofate.template.hardware.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

public class PreviewView extends ViewGroup {

    private SurfaceView surfaceView;

    public PreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceView = new SurfaceView(context);
        addView(surfaceView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                View.resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec));

    }
}
