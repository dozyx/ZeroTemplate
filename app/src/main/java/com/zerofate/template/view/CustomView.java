package com.zerofate.template.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zerofate.androidsdk.util.ScreenUtil;

/**
 * Paint 有三种模式：FILL 填充，将忽略所有 stroke 相关的设置；STROKE 划线；FILL_AND_STROKE 同时填充并且划线
 */

public class CustomView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();

    public CustomView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 心形
        path.addArc(200, 200, 400, 400, -225,
                225); // 前四个参数确定该弧形的椭圆所在的矩形，这里是一个圆形；第五个参数为起始角度，第六个参数为扫过的范围
        path.arcTo(400, 200, 600, 400, -180, 225, false);// 与上一个弧线对称的弧线
        path.lineTo(400, 542);//连接到心形的底部端点
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        Paint paint = new Paint();
        int displayWidth = ScreenUtil.getDisplayMetrics(getContext()).widthPixels;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(30);
//        paint.setStrokeCap(Paint.Cap.ROUND);// 端点形状，相当于在已绘制形状的端点加上"帽子"（会占用额外的大小）
        // FILL_AND_STROKE 会同时填充圆和线条，即整圆的半径实际为填充圆的半径加上线条的宽度的一半
        // 注意：stroke 的宽度以线条的中间为基准，
        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(displayWidth / 2, 200, 200, paint);

//        canvas.drawLine(100, 100, 500, 100, paint);
//        canvas.drawRect(0, 400, 200, 600, paint);

//        canvas.drawPoint(600, 600, paint);

        paint.setColor(Color.GREEN);
//        canvas.drawPoints(new float[]{50, 50, 150, 150, 300, 90, 500, 400}, 0, 8, paint);

//        canvas.drawOval(400, 400, 600, 700, paint);

        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.FILL); // 填充模式
//        canvas.drawArc(200, 100, 800, 500, -110, 100, true, paint); // 绘制扇形，边界坐标表示的是图形所在椭圆
//        canvas.drawArc(200, 100, 800, 500, 20, 140, false, paint); // 绘制弧形
//        paint.setStyle(Paint.Style.STROKE); // 画线模式
//        canvas.drawArc(200, 100, 800, 500, 180, 60, false, paint); // 绘制不封口的弧形

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        paint.setAntiAlias(true);
        canvas.drawCircle(800, 800, 200, paint);

        paint.setStrokeWidth(50);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(50, 50, paint);
    }
}
