package com.zerofate.template.view.practice.qijian.chapter1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Create by timon on 2019/5/11
 **/
public class CustomView3 extends View {
    private Paint radarPaint;
    private Paint valuePaint;
    private float radius;
    private int centerX;
    private int centerY;

    public CustomView3(Context context) {
        super(context);
        init();
    }

    private void init() {
        radarPaint = new Paint();
        radarPaint.setStyle(Paint.Style.STROKE);
        radarPaint.setColor(Color.GREEN);

        valuePaint = new Paint();
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2f * 0.9f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
    }

    private void drawRegion(Canvas canvas) {
    }

    private void drawLines(Canvas canvas) {

    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        int count = 6;
        float angle = 360f / count;
        float r = radius / count;// 环间距
        for (int i = 1; i <= 1; i++) {
            float curR = r * i;//当前环的半径
            path.reset();
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, radarPaint);
        }
    }
}
