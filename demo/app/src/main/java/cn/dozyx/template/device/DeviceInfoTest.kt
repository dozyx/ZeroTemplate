package cn.dozyx.template.device

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.NetworkUtils
import com.gyf.immersionbar.ImmersionBar
import timber.log.Timber
import java.util.Locale
import java.util.TimeZone

class DeviceInfoTest : BaseTestActivity() {

    override fun initActions() {
        addAction(object : Action("IP") {
            override fun run() {
                showIP()
            }
        })

        addAction(object : Action("settings") {
            override fun run() {
                showSettings()
            }
        })

//        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init()
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

    private fun showSettings() {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        appendResult(
            """
            Locale.getDefault(): ${Locale.getDefault()}
            networkCountryIso: ${telephonyManager.networkCountryIso}
            simOperator: ${telephonyManager.simOperator}
            simCountryIso: ${telephonyManager.simCountryIso}
            System locale: ${Resources.getSystem().configuration.let { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) it.locales[0] else it.locale }}
            "TimeZone.getDefault().id: ${TimeZone.getDefault().id}"
        """.trimIndent()
        )
    }

    private fun showIP() {
        appendResult(NetworkUtils.getIPAddress(true))
        appendResult(NetworkUtils.getIPAddress(false))
        appendResult(cn.dozyx.template.device.NetworkUtils.getLocalIpAddress(this))
        appendResult("#########")
    }

}