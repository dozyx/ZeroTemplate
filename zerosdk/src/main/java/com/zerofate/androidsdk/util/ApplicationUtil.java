package com.zerofate.androidsdk.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @author dozeboy
 * @date 2018/3/18
 */
public class ApplicationUtil {

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
}
