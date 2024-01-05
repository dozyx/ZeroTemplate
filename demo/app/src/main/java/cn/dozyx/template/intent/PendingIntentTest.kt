package cn.dozyx.template.intent

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.lifecycle.lifecycleScope
import cn.dozyx.template.aidl.MyService
import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.service.SimpleService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class PendingIntentTest : BaseTestActivity() {

    override fun initActions() {
        addAction("pending service") {
            testService()
        }

        addAction("start service") {
            directStartService()
        }

        addAction("finish") {
            finish()
        }
    }

    private fun directStartService() {
        SimpleService.startDelay(this, 65)
    }

    private fun testService() {
        val intent = Intent(applicationContext, SimpleService::class.java)
        val pendingIntent =
            PendingIntent.getService(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 80 * 1000,
            pendingIntent
        )
    }

    override fun onStop() {
        Timber.d("PendingIntentTest.onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.d("PendingIntentTest.onDestroy")
        super.onDestroy()
    }
}