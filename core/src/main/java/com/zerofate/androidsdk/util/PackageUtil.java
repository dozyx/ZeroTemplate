package com.zerofate.androidsdk.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by zero on 2017/8/7.
 */

public class PackageUtil {
    public static List<ResolveInfo> getActivities(Context context, Intent intent) {
        PackageManager pManager = context.getPackageManager();
        return pManager.queryIntentActivities(intent, 0);
    }
}
