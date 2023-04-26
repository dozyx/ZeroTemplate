package cn.dozyx.template.device

import android.graphics.Rect
import android.graphics.RectF
import android.util.DisplayMetrics
import androidx.core.view.WindowInsetsCompat
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.NetworkUtils
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import timber.log.Timber

class DeviceInfoTest : BaseTestActivity() {

    override fun initActions() {
        addAction(object : Action("IP") {
            override fun run() {
                showIP()
            }
        })

        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init()
        addAction(object : Action("Screen") {
            override fun run() {
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                Timber.d("DeviceInfoTest.run getMetrics ${displayMetrics.widthPixels} ${displayMetrics.heightPixels}")
                windowManager.defaultDisplay.getRealMetrics(displayMetrics)
                Timber.d("DeviceInfoTest.run getRealMetrics ${displayMetrics.widthPixels} ${displayMetrics.heightPixels}")
                Timber.d(
                    "DeviceInfoTest.run bar: ${ImmersionBar.getStatusBarHeight(this@DeviceInfoTest)} ${
                        ImmersionBar.getNavigationBarHeight(
                            this@DeviceInfoTest
                        )
                    }"
                )
            }
        })
    }

    private fun showIP() {
        appendResult(NetworkUtils.getIPAddress(true))
        appendResult(NetworkUtils.getIPAddress(false))
        appendResult(cn.dozyx.template.device.NetworkUtils.getLocalIpAddress(this))
        appendResult("#########")
    }

}