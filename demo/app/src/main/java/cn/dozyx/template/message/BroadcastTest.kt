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
import timber.log.Timber

/**
 * 多次注册 receiver（包括静态和动态，注册两个静态只会生效一个），会触发多次 onReceive
 */
class BroadcastTest : BaseTestActivity() {
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            appendResult("onReceive: ${intent?.action}")
            Timber.d("BroadcastTest.onReceive: ${intent?.action} {${intent?.dataString}}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH)
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)
        }
        registerReceiver(receiver, filter)

        registerReceiver(receiver, IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REPLACED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package") // 必须指定 scheme 才能生效
        })

        registerReceiver(MyBroadcastReceiver(), IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REPLACED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package") // 必须指定 scheme 才能生效
        })

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
        addAction(object : Action("empty receiver") {
            override fun run() {
                sendBroadcast(
                    Intent(
                        this@BroadcastTest,
                        NoFilterReceiver::class.java
                    ).apply { action = "test11111" })
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