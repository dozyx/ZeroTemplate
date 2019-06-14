package cn.dozyx.template.view.practice.qijian.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Create by timon on 2019/5/14
 **/
public class CustomView4 extends View {
    private Paint textPaint;

    public CustomView4(Context context) {
        super(context);
        init();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setColor(Color.GREEN);
        textPaint.setStrokeWidth(5);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(SizeUtils.sp2px(48));
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.LEFT);
//        textPaint.setUnderlineText(true);
//        textPaint.setStrikeThruText(true);
//        textPaint.setTextSkewX(-1);
//        textPaint.setTextScaleX(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final float startX = 480;
        canvas.drawLine(startX, 0, startX, 500, textPaint);
        canvas.drawText("海贼王", startX, 100, textPaint);

        Path path = new Path();
        path.addCircle(500f, 500f, 300, Path.Direction.CW);
        canvas.drawPath(path, textPaint);
        canvas.drawTextOnPath("不知道的事", path, 0, 0, textPaint);

    }
}
