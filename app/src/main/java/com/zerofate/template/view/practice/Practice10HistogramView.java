package com.zerofate.template.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Practice10HistogramView extends View {

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Path axisPath = new Path();

        paint.setStyle(Paint.Style.STROKE);
        axisPath.moveTo(100, 100);
        axisPath.lineTo(100, 600);
        axisPath.lineTo(1000, 600);
        canvas.drawPath(axisPath, paint);

        paint.setTextSize(48);
        Rect titleRect = getTextBounds("直方图", paint);
        canvas.drawText("直方图", width / 2 - titleRect.width() / 2, height - 100,
                paint);// 文字居中，距底部 100 像素

        paint.setTextSize(25);
        List<Version> data = getData();
        int count = data.size();
        int space = 20;
        int itemWidth = 900 / count - space;
        for (int i = 0; i < count; i++) {
            Version version = data.get(i);
            Rect textRect = new Rect();
            textRect = getTextBounds(version.name, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(version.name,
                    (itemWidth + space) * (i + 1) + 100 - itemWidth / 2
                            - textRect.width() / 2, 600 + textRect.height(), paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);
            canvas.drawRect(100 + (itemWidth + space) * i + space, 600 - version.number,
                    100 + (itemWidth + space) * (i + 1), 600, paint);
        }
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
    }

    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    private List<Version> getData() {
        List<Version> data = new ArrayList<>();
        Random random = new Random();
        String[] versions = new String[]{"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"
        };
        for (String version : versions) {
            data.add(new Version(version, random.nextInt(500)));
        }
        return data;
    }

    private static class Version {
        String name;
        int number;

        public Version(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }
}
