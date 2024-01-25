package cn.dozyx.template.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class StaticBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d(
            "StaticBroadcastReceiver.onReceive: ${intent.hashCode()} {${intent?.action} {${intent?.dataString}} replacing: {${
                intent?.getBooleanExtra(
                    Intent.EXTRA_REPLACING,
                    false
                )
            }}"
        )
    }
}