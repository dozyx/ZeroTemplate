package cn.dozyx.template.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * https://developer.android.com/guide/topics/ui/notifiers/notifications
 * 样式 https://material.io/design/platform-guidance/android-notifications.html#templates
 */
class NotificationTest : BaseTestActivity() {
  private var notificationId = 0
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    createChannel()
    val filter = IntentFilter()
    filter.addAction(ACTION_NOTIFICATION_CLICK)
//    filter.addCategory(Intent.CATEGORY_DEFAULT)
    registerReceiver(object : BroadcastReceiver() {
      override fun onReceive(context: Context?, intent: Intent?) {
        ToastUtils.showShort(intent?.action)
      }
    }, filter)
  }

  override fun initActions() {
    addAction(object : Action("normal") {
      override fun run() {
        // targetSdk 版本为 26+ 的话，如果没有创建 channel 和指定通知使用的 channel，通知会发送失败
//        val builder = NotificationCompat.Builder(this@NotificationTest)
        // 左侧始终有一个应用图标，验证发现是 MIUI 才有，模拟器没有
        val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                .setSmallIcon(R.drawable.ic_cat_dog) // 这个是必须设置的，不设置通知显示不出来。MIUI 上通知没看到这个 icon，模拟器有
                .setContentTitle("Title11111")
                .setContentText("Content111111")
                .setContentIntent(createPendingIntent())
                .setStyle(NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true) // 用户点击后自动消失
        val notification = builder.build()
//        notification.flags = notification.flags or NotificationCompat.FLAG_NO_CLEAR // 用户清理通知时不会被取消
//        notification.flags = notification.flags or NotificationCompat.FLAG_ONGOING_EVENT
        NotificationManagerCompat.from(this@NotificationTest).notify(notificationId++, notification)
        // 如何在 app 退出之后依然保留 app？
        // 1. 使用 service
      }
    })

    addAction(object : Action("heads-up") {
      override fun run() {
        // 设备：小米 8 miui12
        // 如果没有允许悬浮通知，不会展示成 heads-up
        // context 使用 applicationContext 也正常
        // 没有 setSmallIcon 在 MIUI 上没有弹出通知
        val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_IMPORTANCE)
//                .setSmallIcon(R.drawable.ic_arrow)
                .setSmallIcon(android.R.color.transparent)
                .setLargeIcon(ImageUtils.drawable2Bitmap(packageManager.getApplicationIcon("com.android.vending")))
                .setContentTitle("33333")
                .setContentIntent(createPendingIntent())
                .setAutoCancel(true)
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .setVibrate(longArrayOf(0))
        NotificationManagerCompat.from(this@NotificationTest.applicationContext).notify(notificationId++, builder.build())
      }
    })

    addAction(object : Action("监听") {
      override fun run() {
        val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                .setSmallIcon(R.drawable.ic_cat_dog)
                .setContentIntent(PendingIntent.getBroadcast(this@NotificationTest, 0, Intent(ACTION_NOTIFICATION_CLICK), FLAG_UPDATE_CURRENT))
                .setContentTitle("监听点击")
                .setAutoCancel(true)
        NotificationManagerCompat.from(this@NotificationTest).notify(notificationId++, builder.build())
      }
    })
  }

  private fun createPendingIntent(): PendingIntent {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://www.baidu.com")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return PendingIntent.getActivity(this@NotificationTest, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  private fun createChannel() {
    // 只支持 API 26+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationManager = getSystemService(NotificationManager::class.java)
      notificationManager?.createNotificationChannel(
              NotificationChannel(CHANNEL_ID_NORMAL, "名称112", NotificationManager.IMPORTANCE_DEFAULT)
                      .apply { description = "描述111" })

      // 使用 NotificationManager.IMPORTANCE_HIGH，会显示 head up 通知，但前提是设置里允许了悬浮通知
      // NotificationManager.IMPORTANCE_HIGH 在 miui 设置里显示的重要程度是紧急，
      // IMPORTANCE_DEFAULT 对应的是高，IMPORTANCE_LOW 对应的是中，IMPORTANCE_LOW对应低
      // 已经创建的 channel，无法在代码里将它的 importance 改成更高，但可以改成更低
      notificationManager?.createNotificationChannel(
              NotificationChannel(CHANNEL_ID_IMPORTANCE, "名称2222", NotificationManager.IMPORTANCE_HIGH)
                      .apply { description = "描述22222" }
      )

      notificationManager?.createNotificationChannel(
              NotificationChannel(CHANNEL_ID_VIBRATE, "名称3333", NotificationManager.IMPORTANCE_DEFAULT)
                      .apply {
                        description = "描述33333"
                        enableVibration(true)
                      })
    }
  }

  class CustomStyle : NotificationCompat.Style() {

  }

  companion object {
    private const val CHANNEL_ID_NORMAL = "1"
    private const val CHANNEL_ID_IMPORTANCE = "2"
    private const val CHANNEL_ID_VIBRATE = "3"
    private const val ACTION_NOTIFICATION_CLICK = "cn.dozyx.action.notification"
  }
}