package cn.dozyx.template.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.webkit.WebView
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ForegroundServiceTest : BaseTestActivity() {
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Timber.d("ForegroundServiceTest.onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Timber.d("ForegroundServiceTest.onServiceDisconnected")
        }
    }

    override fun initActions() {
        addAction("start") {
            startService(Intent(this, ForegroundService::class.java))
        }

        addAction("start fore") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, ForegroundService::class.java))
            }
        }

        addAction("bind") {
            bindService(
                Intent(this, ForegroundService::class.java),
                connection,
                Context.BIND_AUTO_CREATE
            )
        }

        addAction("unbind") {
            unbindService(connection)
        }

        addAction("app bind") {
            applicationContext.bindService(
                Intent(this, ForegroundService::class.java),
                connection,
                Context.BIND_AUTO_CREATE
            )
        }

        addAction("app unbind") {
            applicationContext.unbindService(connection)
        }

        addAction("webview") {
            val userAgentString = WebView(this).settings.userAgentString
            Timber.d("User agent： $userAgentString")
        }
    }
}