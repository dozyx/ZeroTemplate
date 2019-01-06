package com.zerofate.androidsdk.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Zero
 * @date 2017/11/6
 * TODO: 使用Builder实现视图可定制
 */

public class ToastX {

    public static void showShort(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
