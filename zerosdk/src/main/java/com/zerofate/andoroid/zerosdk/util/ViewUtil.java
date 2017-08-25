package com.zerofate.andoroid.zerosdk.util;

import android.content.Context;
import android.view.ViewConfiguration;

/**
 * 有些方法 Android 本身已做了封装，在这里重复只是举个例子来帮助记忆
 */

public final class ViewUtil {
    /**
     * 滑动发生的临界点
     */
    public static int getTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }
}
