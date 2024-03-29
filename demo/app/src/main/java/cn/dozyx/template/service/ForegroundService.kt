package cn.dozyx.template.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import cn.dozyx.template.R
import cn.dozyx.template.notification.manager.NotificationManager
import timber.log.Timber

class ForegroundService : Service() {

    private lateinit var session: MediaSessionCompat
    override fun onCreate() {
        super.onCreate()
        Timber.d("ForegroundServiceTest.onCreate")
        initSession()
        // 小米 11P (Android13) startForegroundService 启动后 30s 没有调用 startForeground 会发生 Crash：RemoteServiceException$ForegroundServiceDidNotStartInTimeException: Context.startForegroundService() did not then call Service.startForeground()
        // 如果 target sdk 没有升级到 26，则不会 Crash（但线上似乎个人手机还是出现了 Crash？）
//        stopForeground(true)
//        startForeground(1000, createEmptyNotification())
//        stopSelf() // 如果在 startForegroundService 后直接调用 stopSelf 而没有调用 startForeground，会立即触发 Crash：RemoteServiceException$ForegroundServiceDidNotStartInTimeException
//        startForeground(1000, createEmptyNotification())
        startForeground(1000, createNotification())
//        startForeground(1000, null) // Crash IllegalArgumentException: null notification
    }

    private fun initSession() {
        session = MediaSessionCompat(this, "foreground service")
        session.isActive = true
    }

    private fun createNotification(): Notification {
        val mediaStyle = MediaStyle().also {
            it.setMediaSession(session.sessionToken)
        }
        return NotificationCompat.Builder(this, NotificationManager.CHANNEL_ID_DEFAULT)
            .setSmallIcon(R.drawable.idlefish_ic_launcher)
            .setContentTitle("标题")
            .setContentText("内容")
            .setStyle(mediaStyle)
            .build()
    }

    private fun createEmptyNotification(): Notification {
        // 不指定通知内容，会显示一个 「"XXX" 正在运行」的通知
        return NotificationCompat.Builder(this, NotificationManager.CHANNEL_ID_DEFAULT)
            .build()
    }

    override fun onDestroy() {
        Timber.d("ForegroundServiceTest.onDestroy")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("ForegroundServiceTest.onStartCommand")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("ForegroundServiceTest.onBind")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Timber.d("ForegroundServiceTest.onUnbind")
        return super.onUnbind(intent)
    }
}