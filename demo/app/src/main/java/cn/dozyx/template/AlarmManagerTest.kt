package cn.dozyx.template

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import cn.dozyx.template.AlarmManagerTest.Companion.EXTRA_ID
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AlarmManagerTest : BaseTestActivity() {
    private var notificationId = 100
    override fun initActions() {
        addAction(object : Action("broadcast") {
            override fun run() {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this@AlarmManagerTest, AlarmBroadcast::class.java)// PendingIntent 的广播必须是显式的
                intent.action = ALARM_BROADCAST_ACTION
                Timber.d("send broadcast $notificationId ${TimeUnit.MILLISECONDS.toMinutes(SystemClock.elapsedRealtime())}")
                intent.putExtra(EXTRA_ID, notificationId ++)
                val broadcast = PendingIntent.getBroadcast(this@AlarmManagerTest, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, 60 * 1000 * 100000L, broadcast)// 没有加上 SystemClock.elapsedRealtime()，三星 api23 会在 1s 后就执行，小米11(api30) 在 5s 后执行
//                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 60 * 1000, broadcast)
            }
        })
    }

    companion object {
        const val ALARM_BROADCAST_ACTION = "cn.dozyx.template.BROADCAST"
        const val EXTRA_ID = "id"
    }

}

class AlarmBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Dozyx", "onReceive: ")
        Timber.d("AlarmBroadcast.onReceive: ${intent?.getIntExtra(EXTRA_ID, -1)}")
    }
}