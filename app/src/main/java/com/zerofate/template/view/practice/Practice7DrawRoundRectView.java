package com.zerofate.template.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice7DrawRoundRectView extends View {

    private static final int RECT_WIDTH_HEIGHT = 200;

    public Practice7DrawRoundRectView(Context context) {
        super(context);
    }

    public Practice7DrawRoundRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice7DrawRoundRectView(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int halfWidth = getMeasuredWidth() / 2;
        int halfHeight = getMeasuredHeight() / 2;

        canvas.drawRoundRect(halfWidth - RECT_WIDTH_HEIGHT, halfHeight - RECT_WIDTH_HEIGHT,
                halfWidth + RECT_WIDTH_HEIGHT, halfHeight + RECT_WIDTH_HEIGHT, 50, 50,
        new Paint(Paint.ANTI_ALIAS_FLAG));
//        练习内容：使用 canvas.drawRoundRect() 方法画圆角矩形
    }
}
