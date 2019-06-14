package cn.dozyx.template.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.dozyx.template.R;

public class ViewLifeActivity extends AppCompatActivity {
    private static final String TAG = "ViewLifeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_life);
    }

    public static class ViewLifeView extends View {
        public ViewLifeView(Context context) {
            super(context);
            Log.d(TAG, "ViewLifeView: constructor1");
        }

        public ViewLifeView(Context context,
                @Nullable AttributeSet attrs) {
            super(context, attrs);
            Log.d(TAG, "ViewLifeView: constructor2");
        }

        public ViewLifeView(Context context,
                @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            Log.d(TAG, "ViewLifeView: constructor3");
        }

        @Nullable
        @Override
        protected Parcelable onSaveInstanceState() {
            Log.d(TAG, "onSaveInstanceState: ");
            return super.onSaveInstanceState();
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            Log.d(TAG, "onFinishInflate: ");
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            Log.d(TAG, "onAttachedToWindow: ");
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            Log.d(TAG, "onLayout: ");
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            Log.d(TAG, "onMeasure: ");
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.d(TAG, "onDraw: ");
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            Log.d(TAG, "onDetachedFromWindow: ");
        }

        @Override
        protected void onFocusChanged(boolean gainFocus, int direction,
                @Nullable Rect previouslyFocusedRect) {
            super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
            Log.d(TAG, "onFocusChanged: " + gainFocus);
        }

        @Override
        protected void onRestoreInstanceState(Parcelable state) {
            super.onRestoreInstanceState(state);
            Log.d(TAG, "onRestoreInstanceState: ");
        }
    }

    public static class LifeViewLinearLayout extends LinearLayout {
        public LifeViewLinearLayout(Context context) {
            super(context);
            Log.d(TAG, "LifeViewGroup: 1");
        }

        public LifeViewLinearLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            Log.d(TAG, "LifeViewGroup: 2");
        }

        public LifeViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            Log.d(TAG, "LifeViewGroup: 3");
        }

        @Nullable
        @Override
        protected Parcelable onSaveInstanceState() {
            Log.d(TAG, "onSaveInstanceState: view group");
            return super.onSaveInstanceState();
        }

        @Override
        protected void onRestoreInstanceState(Parcelable state) {
            super.onRestoreInstanceState(state);
            Log.d(TAG, "onRestoreInstanceState: view group");
        }
    }
}
