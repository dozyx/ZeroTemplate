package cn.dozyx.template.view.practice.qijian.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import cn.dozyx.template.R;

/**
 * Create by dozyx on 2019/5/14
 **/
public class CustomView5 extends View {
    private Bitmap bitmap;
    private Paint paint;

    public CustomView5(Context context) {
        super(context);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_0);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(bitmap, 0, 0, null);
        Path path = new Path();
        path.addCircle(400, 400, 200, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}
