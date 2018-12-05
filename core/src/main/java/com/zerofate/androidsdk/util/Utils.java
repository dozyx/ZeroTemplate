package com.zerofate.androidsdk.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;

import com.zerofate.androidsdk.BuildConfig;

import java.util.List;

/**
 * Created by zero on 2017/8/24.
 */

public class Utils {

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }


    public static boolean isMainProcess(Context context) {
        return context.getPackageName().equals(getProcessName(context));
    }

    public static String getProcessName(Context context) {
        int myPid = Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == myPid) {
                return info.processName;
            }
        }
        return null;
    }
}
