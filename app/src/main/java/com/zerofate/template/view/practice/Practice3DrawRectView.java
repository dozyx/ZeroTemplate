package com.zerofate.template.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice3DrawRectView extends View {

    private static final int RECT_WIDTH_HEIGHT = 200;

    public Practice3DrawRectView(Context context) {
        super(context);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int halfWidth = getMeasuredWidth() / 2;
        int halfHeight = getMeasuredHeight() / 2;

        canvas.drawRect(halfWidth - RECT_WIDTH_HEIGHT, halfHeight - RECT_WIDTH_HEIGHT,
                halfWidth + RECT_WIDTH_HEIGHT, halfHeight + RECT_WIDTH_HEIGHT,
                new Paint(Paint.ANTI_ALIAS_FLAG));
//        练习内容：使用 canvas.drawRect() 方法画矩形
    }
}
