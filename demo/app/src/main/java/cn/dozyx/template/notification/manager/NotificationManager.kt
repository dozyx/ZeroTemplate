package cn.dozyx.template.notification.manager

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

object NotificationManager {
    const val CHANNEL_ID_DEFAULT = "default"

    fun createChannels(context: Context) {
        NotificationManagerCompat.from(context).createNotificationChannel(
            NotificationChannelCompat.Builder(
                CHANNEL_ID_DEFAULT, NotificationManagerCompat.IMPORTANCE_DEFAULT
            ).setName("默认")
                .build()
        )
    }
}