package com.zerofate.androidsdk.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference 用法模板类
 *
 * @author Timon
 * @date 2018/1/8
 */

public class PreferencesManager {
    /**
     * 保存不同用户的私有属性
     */
    public static String FILE_NAME_USER_PRIVATE;
    private static final String FILE_NAME_USER_PRIVATE_SUFFIX = "_preference";

    public static void init(String privateFileName) {
        FILE_NAME_USER_PRIVATE = privateFileName + FILE_NAME_USER_PRIVATE_SUFFIX;
    }

    public static SharedPreferences getPrivateSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME_USER_PRIVATE, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getPrivateEditor(Context context) {
        return getPrivateSharedPreferences(context).edit();
    }
}
