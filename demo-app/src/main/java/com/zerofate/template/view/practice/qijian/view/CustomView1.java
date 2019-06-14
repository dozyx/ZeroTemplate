package com.zerofate.template.view.practice.qijian.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

/**
 * Create by timon on 2019/5/11
 **/
public class CustomView1 extends View {
    public CustomView1(Context context) {
        super(context);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*paint 与 canvas*/
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);
//        canvas.drawCircle(190, 200, 150, paint);
//        canvas.drawLine(100, 100, 200, 200, paint);
//        canvas.drawPoint(100, 100, paint);
        canvas.drawRect(10, 10, 100, 100, paint);
        paint.setStyle(Paint.Style.FILL);
        @SuppressLint("DrawAllocation") RectF rectF = new RectF(210f, 10f, 300f, 100f);
        canvas.drawRect(rectF, paint);

//        paint.setColor(0x7EFFFF00);
//        canvas.drawCircle(190, 200, 100, paint);


        /*path*/
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(10, 160);
        path.lineTo(10, 260);
        path.lineTo(300, 260);
        path.close();
        paint.setStrokeWidth(5);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(10, 300);
//        path.arcTo(new RectF(100, 300, 200, 400), 0, 90);
        path.arcTo(new RectF(100, 300, 200, 400), 0, 90, true);
        canvas.drawPath(path, paint);

        /*region*/
        paint.setStyle(Paint.Style.FILL);
        Region region = new Region(new Rect(50, 410, 200, 510));
        drawRegion(canvas, paint, region);

        path.reset();
        path.addOval(new RectF(50, 520, 200, 970), Path.Direction.CCW);
        Region region1 = new Region();
        Rect regionRect = new Rect(50, 520, 200, 670);
        region1.setPath(path, new Region(regionRect));
//        region1.union(regionRect);
        drawRegion(canvas, paint, region1);

        Rect rect1 = new Rect(100, 700, 400, 800);
        Rect rect2 = new Rect(200, 600, 300, 900);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);
        Region region2 = new Region(rect1);
        Region region3 = new Region(rect2);
        region2.op(region3, Region.Op.INTERSECT);
        paint.setStyle(Paint.Style.FILL);
        drawRegion(canvas, paint, region2);
    }

    private void drawRegion(Canvas canvas, Paint paint, Region region) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();
        int drawCount = 0;
        LogUtils.d("start drawRegion");
        while (regionIterator.next(rect)) {
            drawCount++;
            canvas.drawRect(rect, paint);
        }
        LogUtils.d("end drawRegion：" + drawCount);
    }
}
