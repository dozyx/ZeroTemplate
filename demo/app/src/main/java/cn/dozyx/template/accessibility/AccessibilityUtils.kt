package cn.dozyx.template.accessibility

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.text.TextUtils

object AccessibilityUtils {

    private val ACCESSIBILITY_SERVICE_PATH: String = MyAccessibilityService::class.java.canonicalName

    /**
     * 判断是否有辅助功能权限
     */
    fun isAccessibilitySettingsOn(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        var accessibilityEnabled = 0
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                context.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        val packageName = context.packageName
        val serviceStr = "$packageName/$ACCESSIBILITY_SERVICE_PATH"
        if (accessibilityEnabled == 1) {
            val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
            val settingValue = Settings.Secure.getString(
                context.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()
                    if (accessibilityService.equals(serviceStr, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun getAccessibilitySettingPageIntent(context: Context?): Intent? {
        // 一些品牌的手机可能不是这个Intent,需要适配
        return Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    }

    fun startAccessibilitySettings(activity: Activity): Boolean {
        val intent = Intent()
        if (!Build.MANUFACTURER.toLowerCase().contains("samsung") || Build.VERSION.SDK_INT < 28) {
            intent.action = "android.settings.ACCESSIBILITY_SETTINGS"
        } else {
            intent.component = ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$AccessibilityInstalledServiceActivity"
            )
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY)
        return try {
            activity.startActivityForResult(intent, 1)
            activity.overridePendingTransition(0, 0)
            true
        } catch (unused: ActivityNotFoundException) {
            false
        }
    }
}