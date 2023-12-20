package cn.dozyx.template.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ToastUtils

class BroadcastTest : BaseTestActivity() {
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            appendResult("onReceive: ${intent?.action}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH)
            addAction(Intent.ACTION_PACKAGE_REPLACED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)
        }
        registerReceiver(receiver, filter)
        applicationContext.registerReceiver(AppReceiver(), filter)
    }

    override fun initActions() {
        addAction(object : Action("发送") {
            override fun run() {
                sendBroadcast(Intent("snap.cleaner.action.JUNK_STATUS_CHANGED"))
            }
        })
        addAction(object : Action("finish") {
            override fun run() {
                finish()
            }
        })
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}

class AppReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        ToastUtils.showShort(intent?.action)
    }
}