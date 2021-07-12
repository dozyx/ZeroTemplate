package cn.dozyx.template

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import timber.log.Timber

/**
 * Intent.ACTION_CLOSE_SYSTEM_DIALOGS
 *     手势进入桌面、最近任务、切换应用都会收到，reason 为 fs_gesture
 *     息屏也会收到，reason 为 dream
 *     按键 home 会收到多个(小米 11 收到了 4 个)，reason 为 homekey
 *     进入最近任务也收到了多个，reason 为 recentapps
 *     返回不会收到
 */
class SystemBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_CLOSE_SYSTEM_DIALOGS -> {
                Timber.d(
                    "SystemBroadcastReceiver.onReceive action: ${intent.action} reason: ${
                        intent.getStringExtra(
                            "reason"
                        )
                    }"
                )
            }
            else -> Timber.d("SystemBroadcastReceiver.onReceive action: ${intent?.action}")
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.registerReceiver(SystemBroadcastReceiver(), IntentFilter().apply {
                addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                addAction(Intent.ACTION_SCREEN_OFF)
            })
        }
    }
}