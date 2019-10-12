package cn.dozyx.template.view.practice.qijian.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Create by dozyx on 2019/5/11
 **/
public class CustomView2 extends View {
    public CustomView2(Context context) {
        super(context);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

//        canvas.translate(100, 100);
        Rect r = new Rect(0, 0, 400, 200);
        canvas.drawRect(r, paint);

        Paint paint1 = new Paint();
        paint1.setColor(Color.RED);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(5);
        canvas.translate(100, 100);
        canvas.drawRect(r, paint1);

        canvas.save();
        canvas.drawColor(Color.RED);
        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        canvas.restore();
        canvas.drawColor(Color.BLUE);


    }
}
