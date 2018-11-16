package com.zerofate.androidsdk.util;

import android.content.Context;
import android.widget.Toast;

import com.zerofate.androidsdk.BuildConfig;

/**
 * @author Zero
 * @date 2017/11/6
 * TODO: 使用Builder实现视图可定制
 */

public class ToastX {
    private static final boolean isDebug = BuildConfig.DEBUG;

    public static void showShort(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showDebugShort(Context context, CharSequence msg) {
        if (!isDebug) {
            showShort(context, msg);
        }
    }

    public static void showDebugLong(Context context, CharSequence msg) {
        if (!isDebug) {
            showLong(context, msg);
        }
    }
}
