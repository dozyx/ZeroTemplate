package com.zerofate.andoroid.zerosdk.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by zero on 2017/8/4.
 */

public class ScreenUtil {
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static float getDisplayDensity(Context context) {
        return getDisplayMetrics(context).density;
    }


}
