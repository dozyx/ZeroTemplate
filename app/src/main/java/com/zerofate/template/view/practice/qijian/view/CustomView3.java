package com.zerofate.template.view.practice.qijian.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

/**
 * Create by timon on 2019/5/11
 **/
public class CustomView3 extends View {
    private Paint radarPaint;
    private Paint valuePaint;
    private Paint pointPaint;
    private float radius;
    private int centerX;
    private int centerY;
    private static final int POLYGON_COUNT = 6;
    private static final int MAX_VALUE = 6;
    private double[] datas = {2, 5, 1, 6, 4, 5};

    public CustomView3(Context context) {
        super(context);
        init();
    }

    private void init() {
        radarPaint = new Paint();
        radarPaint.setStyle(Paint.Style.STROKE);
        radarPaint.setColor(Color.GREEN);

        valuePaint = new Paint();
        valuePaint.setStyle(Paint.Style.FILL);
        valuePaint.setColor(Color.BLUE);
        valuePaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.RED);
        pointPaint.setStrokeWidth(5);
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
        Path path = new Path();
        valuePaint.setAlpha(127);
        for (int i = 0; i < datas.length; i++) {
            float polygonRadius = (float) (radius / POLYGON_COUNT * datas[i]);
            float x = (float) (centerX + polygonRadius * Math.cos(Math.toRadians(i / (float) MAX_VALUE * 360)));
            float y = (float) (centerY + polygonRadius * Math.sin(Math.toRadians(i / (float) MAX_VALUE * 360)));
            canvas.drawCircle(x, y, 10, valuePaint);
            if (i == 0) {
                path.moveTo(x, y);
                continue;
            }
            path.lineTo(x, y);
        }
        path.close();
        canvas.drawPath(path, valuePaint);
    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i < POLYGON_COUNT; i++) {
            double angle = Math.toRadians(360 / (double) POLYGON_COUNT * i);
            canvas.drawLine(centerX, centerY, ((float) (centerX + radius * Math.cos(angle))),
                    ((float) (centerY + radius * Math.sin(
                            angle))), radarPaint);
        }
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float angle = 360f / POLYGON_COUNT;
        float r = radius / POLYGON_COUNT;// 环间距
        canvas.drawPoint(centerX, centerY, pointPaint);
        for (int i = 1; i <= 6; i++) {
            float curR = r * i;//当前环的半径
            path.reset();
            for (int j = 0; j < POLYGON_COUNT; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                    canvas.drawPoint(centerX + curR, centerY, pointPaint);
                } else {
                    float x = (float) (centerX + curR * Math.cos(Math.toRadians(angle * j)));
                    float y = (float) (centerY + curR * Math.sin(Math.toRadians(angle * j)));
                    path.lineTo(x, y);
                    canvas.drawPoint(x, y, pointPaint);
                    LogUtils.d(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, radarPaint);
        }
    }
}
