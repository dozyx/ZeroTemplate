package com.example.app_float;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zerofate.androidsdk.util.ViewUtil;
import com.zerofate.template.app_float.R;

public class FloatService extends Service implements View.OnTouchListener {
    private static final String TAG = "FloatService";
    private View floatView;
    private ImageView floatImage;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    private static int sTouchSlop;

    private float startRawX = 0, startRawY = 0;
    private float oldX = 0, oldY = 0;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_PHONE);
        initFloatView();
        showWindow();
    }

    private void initFloatView() {
        floatView = LayoutInflater.from(this).inflate(R.layout.float_view, null);
        floatImage = floatView.findViewById(R.id.float_image);
        floatImage.setOnTouchListener(this);
    }

    private void showWindow() {
        windowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowParams.alpha = 0.5f;
        windowParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        windowManager.addView(floatView, windowParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final float x = event.getRawX();
        final float y = event.getRawY();

        if (v == floatImage) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startRawX = event.getRawX();
                    startRawY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(x - startRawX) < sTouchSlop || Math.abs(y - startRawY) < sTouchSlop) {
//                        break;
                    }
                    // TODO: 2017/11/7 此处计算有问题，需要重新写
                    windowParams.x -= (int) (x - startRawX);
                    windowParams.y += (int) (y - startRawY);
                    windowManager.updateViewLayout(floatView, windowParams);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }
}
