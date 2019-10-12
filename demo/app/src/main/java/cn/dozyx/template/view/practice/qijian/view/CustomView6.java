package cn.dozyx.template.view.practice.qijian.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import cn.dozyx.template.R;

/**
 * Create by dozyx on 2019/5/14
 **/
public class CustomView6 extends View {
    private Bitmap bitmap;
    private int clipWidth = 0;
    private int width;
    private int height;
    private static final int CLIP_HEIGHT = 30;
    //    private Region region;
    private Path path;

    public CustomView6(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), R.drawable.bg_0, options);
//        options.inSampleSize =
//                (int) (ScreenUtil.getDisplayMetrics(getContext()).widthPixels / (float)options.outWidth / 2);
//        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_dialog_bg, options);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
//        region = new Region();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        region.setEmpty();
        path.reset();
        int i = 0;
        while (i * CLIP_HEIGHT <= height) {
            if (i % 2 == 0) {
//                region.union(new Rect(0, i * CLIP_HEIGHT, clipWidth, (i + 1) * CLIP_HEIGHT));
                path.addRect(new RectF(0, i * CLIP_HEIGHT, clipWidth, (i + 1) * CLIP_HEIGHT), Path.Direction.CW);
            } else {
//                region.union(new Rect(new Rect(width - clipWidth, i * CLIP_HEIGHT, width, (i + 1) * CLIP_HEIGHT)));
                path.addRect(new RectF(new Rect(width - clipWidth, i * CLIP_HEIGHT, width, (i + 1) * CLIP_HEIGHT)),
                        Path.Direction.CW);

            }
            i++;
        }
//        canvas.clipPath(region.getBoundaryPath());
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);
        if (clipWidth > width) {
            return;
        }
        clipWidth += 5;
        invalidate();
    }
}
