package cn.dozyx.template.firebase

import android.app.ActivityManager
import android.os.Build
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber
import java.lang.RuntimeException

class FirebaseTest : BaseTestActivity() {
    override fun initActions() {
        addButton("crash") {
            throw RuntimeException("Test Crash")
        }

        addButton("check") {
            // 检查设备状态
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Timber.d("isBackgroundRestricted: ${(getSystemService(ACTIVITY_SERVICE) as ActivityManager).isBackgroundRestricted}")
            }
        }
    }
}