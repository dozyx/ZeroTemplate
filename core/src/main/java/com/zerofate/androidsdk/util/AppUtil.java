package com.zerofate.androidsdk.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * @author dozeboy
 * @date 2018/3/18
 */
public class AppUtil {
    private static final String TAG = "AppUtil";

    /**
     * 判断当前进程是否为主进程，本应用里的主进程名为包名。
     * 作此判断的目的是为了避免其他进程再次初始化一些内容（每个进程都会创建自己的 Application）
     */
    private boolean isMainProcess(Context context) {

        String processName = "";
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> allProcesses =
                activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : allProcesses) {
            if (process.pid == pid) {
                processName = process.processName;
                break;
            }
        }
        return context.getPackageName().equals(processName);
    }

    private static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getVersionName: " + e.getMessage());
        }
        return versionName;
    }

    private static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getVersionName: " + e.getMessage());
        }
        return versionCode;
    }
}
