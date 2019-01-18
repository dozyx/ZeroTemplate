package com.zerofate.androidsdk.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Process;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by zero on 2017/8/24.
 */

public class Utils {

    private static boolean sIsDebug = false;
    private static Context sContext;

    public static void init(@NonNull Context context) {
        sIsDebug = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        sContext = context.getApplicationContext();
    }

    public static boolean isDebug() {
        return sIsDebug;
    }


    public static boolean isMainProcess() {
        return sContext.getPackageName().equals(getProcessName());
    }

    public static String getProcessName() {
        int myPid = Process.myPid();
        ActivityManager manager = (ActivityManager) sContext.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == myPid) {
                return info.processName;
            }
        }
        return null;
    }
}
