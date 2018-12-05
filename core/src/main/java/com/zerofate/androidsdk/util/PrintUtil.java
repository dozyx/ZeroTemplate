package com.zerofate.androidsdk.util;

import android.app.Activity;

public class PrintUtil {

    public static String printResultCode(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            return "RESULT OK";
        } else if (resultCode == Activity.RESULT_CANCELED) {
            return "RESULT_CANCELED";
        }
        return String.valueOf(resultCode);
    }
}
