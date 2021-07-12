package cn.dozyx

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationChannelCompat

object ZeroNotificationManager {

    fun registerChannel(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channel = NotificationChannel("channel_default", "默认", NotificationChannelCompat.DEFAULT_CHANNEL_ID)
//        notificationManager.createNotificationChannel(channel)
    }
}