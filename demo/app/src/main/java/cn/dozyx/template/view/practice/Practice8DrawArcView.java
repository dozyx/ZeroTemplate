package cn.dozyx.template.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Practice8DrawArcView extends View {

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        canvas.drawArc(300, 400, 600, 600, -100, 100, true, paint);
        canvas.drawArc(300, 400, 600, 600, 15, 135, false, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(300, 400, 600, 600, -180, 60, false, paint);
//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
    }
}
