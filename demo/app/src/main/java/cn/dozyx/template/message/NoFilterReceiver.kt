package cn.dozyx.template.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

/**
 * manifest 里没有设置 intent filter，仍然可以通过代码显式发送广播
 */
class NoFilterReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d(
            "NoFilterReceiver.onReceive: ${intent?.action} ${intent?.dataString}"
        )
    }
}