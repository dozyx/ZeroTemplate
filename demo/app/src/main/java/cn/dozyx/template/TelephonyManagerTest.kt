package cn.dozyx.template

import android.telephony.TelephonyManager
import cn.dozyx.core.utli.TimeTracker
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class TelephonyManagerTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("networkCountryIso") {
            override fun run() {
                val tracker = TimeTracker.newTracker()
                val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                telephonyManager.networkCountryIso// 每次调用大概 3~4 ms
                telephonyManager.networkCountryIso
                tracker.end()
            }
        })
    }
}