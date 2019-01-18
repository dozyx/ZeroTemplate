package com.zerofate.androidsdk.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 权限请求被拒绝后很可能下一次就不会再显示询问框，不同厂商的权限设置界面也会不一样
 * Android M 运行时权限说明：安装时，普通权限直接授予，危险权限需要在运行时授予；运行时需要某个
 * 危险权限，需要判断该权限是否已获得，如果没有则发起请求，这时，系统将弹框询问用户是否允许，允许后，
 * 应用将获得该权限及其权限组内其他权限的权限，下一次不再需要请求，如果拒绝，那么应用在下一次请求时，
 * 系统将没有任何提示（不会有提示框），这时候可以通过 ActivityCompat.shouldShowRequestPermissionRationale
 * 判断是否需要向用户进行说明或者引导用户进行设置（也可以在onRequestPermissionsResult中进行）
 *
 * @author dozeboy
 * @date 2017/11/22
 */

public class PermissionHelper {

    public static boolean checkContactReadPermission(Context context) {
        return checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
    }

    public static boolean checkContactWritePermission(Context context) {
        return checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS);
    }

    /**
     * 是否需要显示权限说明
     * 第一次请求被拒绝后，调用将返回true；多次请求，并且用户选择了不再提醒并拒绝，将返回false；
     * 设备的策略禁止当前应用获取该权限，将返回false
     */
    private static boolean shouldShowRequestPermissionRationale(Activity activity,
            String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static void requestPermissions(Activity activity, String[] strings, int requestCode) {
        ActivityCompat.requestPermissions(activity, strings, requestCode);
    }

    /**
     * 检查是否已被授予该权限
     */
    private static boolean checkSelfPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

}
