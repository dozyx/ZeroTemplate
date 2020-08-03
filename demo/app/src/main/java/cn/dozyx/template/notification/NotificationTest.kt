package cn.dozyx.template.notification

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getBroadcast
import android.content.*
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cn.dozyx.template.BuildConfig
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.ToastUtils
import timber.log.Timber
import kotlin.random.Random

/**
 *
 * https://developer.android.com/guide/topics/ui/notifiers/notifications
 * https://developer.android.com/training/notify-user/build-notification#top_of_page
 * 样式 https://material.io/design/platform-guidance/android-notifications.html#templates
 */
class NotificationTest : BaseTestActivity() {
    private var notificationId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        deleteChannels()
        createChannels()

        val filter = IntentFilter()
        filter.addAction(ACTION_NOTIFICATION_CLICK)
        filter.addAction(ACTION_NOTIFICATION_DELETE)
//    filter.addCategory(Intent.CATEGORY_DEFAULT)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                ToastUtils.showShort(intent?.action)
                Timber.d("NotificationTest.onReceive ${intent?.action} ${intent?.extras?.getString(EXTRA_NOTIFICATION_ID)}")
            }
        }, filter)
    }

    private fun deleteChannels() {
        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notificationChannels.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                managerCompat.deleteNotificationChannel(it.id)
            }
        }
    }

    override fun initActions() {
        testNormal()

        addAction(object : Action("progress") {
            override fun run() {
                val id = Random.nextInt(3)
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                        .setSmallIcon(R.drawable.anime)
                        .setContentTitle("下载进度测试下载进度测试下载进度测试下载进度测试下载进度测试下载进度测试下载进度测试下载进度测试")
                        .setContentText("哈哈哈")// 如果 title 太长会被遮挡
                        .setContentInfo("咦咦咦")
                        .setSubText("111")// 显示在 appname 和 when 之间（不同版本会不一样，Android 4.4 是显示在 content text 下面），文档说不要跟 setProgress 一起使用，但暂未发现问题（Android 4.4 上导致 progress 无法显示）
                        .setTicker("222")// 不知道干嘛的
                        .setGroup(GROUP_KEY_TEST)
                        .setProgress(100, 50, false)
                        .setOnlyAlertOnce(true)// 只在第一次显示时发出声音
//                        .addAction(android.R.color.transparent, "停止", PendingIntent.getActivity(this@NotificationTest, 0, IntentUtils.getDialIntent("000"), 0))
                // action 的 icon 在 Android N 以上不会显示，在模拟器 api23 上看，显示的是一个灰块。。。https://stackoverflow.com/questions/44698440/android-26-o-notification-doesnt-display-action-icon
                if (Random.nextBoolean()) {
                    builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.bg_0))
                }
                notify(builder, id)
            }
        })

        testHeadUp()

        addAction(object : Action("intent") {
            override fun run() {
                val deleteIntent = Intent(ACTION_NOTIFICATION_DELETE)
                deleteIntent.putExtra(EXTRA_NOTIFICATION_ID, "测试")
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                        .setSmallIcon(R.drawable.ic_cat_dog)
                        .setContentIntent(PendingIntent.getBroadcast(this@NotificationTest, 0, Intent(ACTION_NOTIFICATION_CLICK), FLAG_UPDATE_CURRENT))
                        .setContentTitle("监听点击")
                        .setDeleteIntent(getBroadcast(this@NotificationTest, 0, deleteIntent, FLAG_UPDATE_CURRENT))
                        .setAutoCancel(true)
                notify(builder)
            }
        })

        addAction(object : Action("enable") {
            override fun run() {
                val notificationManagerCompat = NotificationManagerCompat.from(this@NotificationTest)

                val areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled()
                Timber.d("NotificationTest.run areNotificationsEnabled() $areNotificationsEnabled")

                val notificationChannel = notificationManagerCompat.getNotificationChannel(CHANNEL_ID_IMPORTANCE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // importance 为 IMPORTANCE_NONE 表示该 channel 通知被关闭
                    Timber.d("NotificationTest.run channel importance ${notificationChannel?.importance}")
                }

            }
        })

        testGroup()

        addAction(object : Action("取消上一个通知") {
            override fun run() {
                NotificationManagerCompat.from(this@NotificationTest).cancel(notificationId)
            }
        })

        addAction(object : Action("delete channel") {
            override fun run() {
                NotificationManagerCompat.from(this@NotificationTest).deleteNotificationChannel(CHANNEL_ID_NORMAL)
            }
        })
        addAction(object : Action("channel 排序") {
            override fun run() {
                // channel 在系统设置里的排序是跟 id 相关的
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationManagerCompat.from(this@NotificationTest).createNotificationChannel(
                            NotificationChannel("3", "顺序3", NotificationManager.IMPORTANCE_DEFAULT))
                }
            }
        })

        addAction(object : Action("channel 不存在") {
            override fun run() {
                // targetSdk 26 以下，不存在的 channelId 发送通知会无效，正确的 channelId 或者不设置 channelId 可以发送成功
                // targetSdk 26 以上，不设置或者不存在的 channelId 都会发送失败
                Timber.d("NotificationTest.run channel exist " +
                        "${NotificationManagerCompat.from(this@NotificationTest).getNotificationChannel("CHANNEL_ID_NORMAL")}")
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                        .setSmallIcon(R.drawable.ic_cat_dog) // 这个是必须设置的，不设置通知显示不出来。MIUI 上通知没看到这个 icon，模拟器有
                        .setContentTitle("Title11111")
                        .setContentText("Content111111")
                        .setContentIntent(createPendingIntent())
                        .setStyle(NotificationCompat.BigTextStyle())
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true) // 用户点击后自动消失
                val notification = builder.build()
                NotificationManagerCompat.from(this@NotificationTest).notify(notificationId++, notification)
            }
        })

        addAction(object : Action("下载") {
            override fun run() {
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                        .setSmallIcon(R.drawable.ic_cat_dog) // 这个是必须设置的，不设置通知显示不出来。MIUI 上通知没看到这个 icon，模拟器有
                        .setContentTitle("Title11111")
                        .setContentText("Content111111")
                        .setContentIntent(createPendingIntent())
                        .setStyle(NotificationCompat.BigTextStyle())
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true) // 用户点击后自动消失
                val notification = builder.build()
                NotificationManagerCompat.from(this@NotificationTest).notify(notificationId++, notification)
            }
        })

        addAction(object : Action("sort1") {
            override fun run() {
                // 不设置
                notify(newNormalBuilder("sort1").setSortKey("sort1"), 10000)
            }
        })

        addAction(object : Action("sort2") {
            override fun run() {
                notify(newNormalBuilder("sort2").setSortKey("sort2"), 10001)
            }
        })

        addAction(object : Action("通知设置") {
            override fun run() {
                navigateToNotificationSettings(this@NotificationTest)
            }
        })
    }

    private fun testGroup() {
        addAction(object : Action("group channel1") {
            override fun run() {
                // https://developer.android.com/guide/topics/ui/notifiers/notifications#bundle
                // 上面的文档里提到，如果同一个 app 发送了 4 个或更多的没有指定 group 的通知，系统会自动将它们 group 到一起。miui 上没有效果，模拟器有效果。
                // 不设置 channel 也会自动 group
                // target sdk 改为 24 以下也会自动 group
                // 自动 group 之后，将通知关闭到小于 4 个，会自动取消 group
                // https://developer.android.com/training/notify-user/group
                // API 24 之后可以 group
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                        .setSmallIcon(R.drawable.ic_baseline_chevron_left_24)
                        .setLargeIcon(ImageUtils.drawable2Bitmap(packageManager.getApplicationIcon("com.android.settings")))
                        .setContentTitle("normal channel $notificationId")
                        .setContentIntent(createPendingIntent())
                        .setAutoCancel(true)
                        //                        .setSortKey("$notificationId")
                        .setGroup(GROUP_KEY_TEST)// android 4.4 设置 group 之后，通知失效
                notify(builder)
            }
        })

        addAction(object : Action("group channel2") {
            override fun run() {
//                NotificationManagerCompat.from(this@NotificationTest).cancel(10001)
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_IMPORTANCE)
                        .setSmallIcon(R.drawable.ic_baseline_chevron_right_24)
                        .setLargeIcon(ImageUtils.drawable2Bitmap(packageManager.getApplicationIcon("com.android.settings")))
                        .setContentTitle("importance channel $notificationId")
                        .setContentIntent(createPendingIntent())
                        .setAutoCancel(true)
                        //                        .setWhen(System.currentTimeMillis() - 2 * 60 * 1000)
                        //                        .setSortKey("$notificationId")
                        .setGroup(GROUP_KEY_TEST)// android 4.4 设置 group 之后，通知失效
                notify(builder, 1001)
                builder.setChannelId(CHANNEL_ID_NORMAL)
//                notify(builder, 1001)
            }
        })

        addAction(object : Action("group summary") {
            override fun run() {
                // 要发送一个 group summary 的通知，才能把同一 group 的通知汇总到一起
                // 如果对应的 channel 被关闭了，那么 group 也会失败
                // 重复发送 group 会导致之前的通知根据 importance 重排序
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_GROUP_SUMMARY)
                        .setSmallIcon(android.R.color.transparent)
                        .setContentTitle("group1 channel2")
                        .setGroupSummary(true)
                        .setShowWhen(false)
                        .setContentIntent(PendingIntent.getActivity(this@NotificationTest, 0, IntentUtils.getDialIntent("123"), 0))
                        .setGroup(GROUP_KEY_TEST)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setOnlyAlertOnce(true)// 也相当于一个通知，需要注意不要每次都发出声音
                notify(builder, 999)// 保持同一个 id 来确保只发送一个，并且后续更新的是同一个通知
                // 如果之前没有同一个 group key 的通知存在，那么 summary 的通知不会显示
                // 先发送了 summary 通知，再发送同一 group key 的通知，通知会直接 group
            }
        })

        addAction(object : Action("group summary2") {
            override fun run() {
                // 换一个 channel 发送同一个 group summary 通知好像没什么影响
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                        .setSmallIcon(android.R.color.transparent)
                        .setContentTitle("group1 channel2")
                        .setGroupSummary(true)
                        .setContentIntent(PendingIntent.getActivity(this@NotificationTest, 0, IntentUtils.getDialIntent("123"), 0))
                        .setGroup(GROUP_KEY_TEST)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                notify(builder, 100)
            }
        })

        addAction(object : Action("cancel summary") {
            override fun run() {
                NotificationManagerCompat.from(this@NotificationTest).cancel(100)// 会导致所有的通知都被关闭
            }
        })
    }

    private fun testNormal() {
        addAction(object : Action("normal") {
            override fun run() {
                // targetSdk 版本为 26+ 的话，如果没有创建 channel 和指定通知使用的 channel，通知会发送失败
                //        val builder = NotificationCompat.Builder(this@NotificationTest)
                // 左侧始终有一个应用图标，验证发现是 MIUI 才有，模拟器没有
                val contentView = RemoteViews(packageName, R.layout.custom_content_view)
                val content = SpannableString("标题")
                content.setSpan(ForegroundColorSpan(Color.RED), 1, 2, 0)

                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_NORMAL)
                        .setSmallIcon(R.drawable.ic_cat_dog) // 这个是必须设置的，不设置通知显示不出来。MIUI 上通知没看到这个 icon，模拟器有。显示在左上角和系统状态栏小图标
                        .setColor(Color.RED)
                        .setColorized(true)//
                        .setContentTitle("normal channel $notificationId")
                        .setContentText(content)
                        //                        .setCustomContentView(contentView)// 使用自定义的布局
                        //                        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.bg_0)) //显示在右侧
                        .setContentIntent(createPendingIntent())
                        .setStyle(NotificationCompat.BigTextStyle())
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true) // 用户点击后自动消失
                        .setVisibility(NotificationCompat.VISIBILITY_SECRET)// 可以跟 channel 的不一样，测试时需要注意 miui 是只有在锁屏时发出的通知才显示到锁屏
                        .setWhen(System.currentTimeMillis() - 3600 * 1000)// 修改通知显示的时间，不设置显示的是「现在」
                        .setShowWhen(true)
                        .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                        .setSortKey("$notificationId")// 按字典顺序对通知进行排序，比如 "0"、"1"，前者会在上面。但是 importance 会知道这个设置无效，importance 的会一直在前面。。。
                        .setGroup(GROUP_KEY_TEST)
                // 关于排序(模拟器 API29)
                // 影响通知排序的有三个因素：when、importance、sortKey
                // importance 更高的将排在前面，即使低 importance 的通知发送更晚，即使 when 更新也没用；
                // app1 发送了通知，如果在通知栏里插入了一个另一个 app2 的通知（如果 app2 通知的 importance 都比 app1 的低，那么这个通知时不会插入到 app1 通知的前面的），
                // 那么后面 app1 再发送的通知会被 app2 的通知分隔，这个最后发送的通知不会受 importance 的影响直接放置在最上面位置
                // 有时候好像又不是这样。。。而是会将同一 app 的通知全部归到前面。。。
                // 同一个 app 发送不同 importance 的通知：
                // 有一个奇怪的地方是一开始是有按 importance 排序的，但是，如果低 importance 的通知多发送了几次又会提到前面
                // 虽然奇怪，但是对于目前项目的一个场景来说，这样反而是合理的：多个下载任务，下载中和下载完成是不同的 importance，
                // 下载完成的会放置在前面，新建的下载需要放在下载的前面，那么因为下载中一直在 update，将能使其放置在前面即使它的优先级更低
                // 不过还是有问题，低 importance 在高 importance 前面之后，如果不断的下拉通知栏查看，高 importance 的又会跑到前面。。。
                // 但是只要低 importance 的再 update 一次，就又可以排到前面了
                // 感觉这排序规则有点蛋疼。。。

                val remoteViews = RemoteViews(packageName, R.layout.notification_custom)
                //                builder.setContent(remoteViews)// 没生效

                //                builder.setCustomContentView(remoteViews)
                //                builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())

                builder.addAction(R.drawable.anime, "pause", null)
                builder.addAction(R.drawable.anime, "resume", null)

                //        notification.flags = notification.flags or NotificationCompat.FLAG_NO_CLEAR // 用户清理通知时不会被取消
                //        notification.flags = notification.flags or NotificationCompat.FLAG_ONGOING_EVENT
                Handler().postDelayed({ notify(builder) }, 2000)
                //                Handler().postDelayed({NotificationManagerCompat.from(this@NotificationTest).notify(notificationId++, notification)},3000)
                // 如何在 app 退出之后依然保留 app？
                // 1. 使用 service
            }
        })
    }

    fun navigateToNotificationSettings(context: Context) {
        // for Android 4 and below
        val KEY_PACKAGE = "package"
        // for Android 5-7
        val KEY_APP_PACKAGE = "app_package"
        val KEY_APP_UID = "app_uid"
        // for Android O
        val KEY_EXTRA_APP_PACKAGE = "android.provider.extra.APP_PACKAGE"
        val intent = Intent()
        // try to navigate to the notification settings screen first.
        // It should work on Android 5.0 and above, but might not on below.
        try {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(KEY_APP_PACKAGE, BuildConfig.APPLICATION_ID)
            intent.putExtra(KEY_APP_UID, context.applicationContext.applicationInfo.uid)
            intent.putExtra(KEY_EXTRA_APP_PACKAGE, BuildConfig.APPLICATION_ID)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // if above can't navigate to the destination, then navigate to the application detail screen,
            // which will cost one more step into the notification settings.
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts(KEY_PACKAGE, BuildConfig.APPLICATION_ID, null)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    private fun newNormalBuilder(title: String): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID_NORMAL);
        builder.setSmallIcon(android.R.color.transparent)
                .setContentTitle(title)
                .setGroup("normal")
        return builder
    }

    private fun testHeadUp() {
        addAction(object : Action("heads-up") {
            override fun run() {
                // 设备：小米 8 miui12
                // 如果没有允许悬浮通知，不会展示成 heads-up
                // context 使用 applicationContext 也正常
                // 没有 setSmallIcon 在 MIUI 上没有弹出通知
                // 如果对同一个通知发送，会重新显示 headup，设置 setOnlyAlertOnce 为 true 可以避免。所以 head up 可以理解为提醒的一种方式，与提示音类似
                val builder = NotificationCompat.Builder(this@NotificationTest, CHANNEL_ID_IMPORTANCE)
                        //                .setSmallIcon(R.drawable.ic_arrow)
                        .setSmallIcon(android.R.color.transparent)
                        .setLargeIcon(ImageUtils.drawable2Bitmap(packageManager.getApplicationIcon("com.android.vending")))
                        .setContentTitle("33333")
                        .setContentIntent(createPendingIntent())
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_TEST)
                        .setVibrate(LongArray(0))
                        .setSound(null)
                        .setOnlyAlertOnce(true)
//                        .setOnlyAlertOnce(true)
//                        .setCategory(NotificationCompat.CATEGORY_EVENT)
//                        .setProgress(100,0, false)// progress 无法 heads up
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                //                .setVibrate(longArrayOf(0))
                Handler().postDelayed({ notify(builder) }, 2000)
            }
        })
    }

    private fun notify(builder: NotificationCompat.Builder, id: Int = ++notificationId) {
        NotificationManagerCompat.from(this).notify(id, builder.build())
    }

    private fun createPendingIntent(): PendingIntent {
        return createPendingIntent(FLAG_UPDATE_CURRENT)
    }

    private fun createPendingIntent(flag: Int): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://www.baidu.com")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return PendingIntent.getActivity(this@NotificationTest, 0,
                intent, flag)
    }

    private fun createChannels() {
        // targetSdk 为 26 以下时，系统设置里会自动添加一个「未分类」的 channel，不知道是不是 miui 才有的特性
        // 只支持 API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID_NORMAL, "普通", NotificationManager.IMPORTANCE_DEFAULT)
                            .apply {
                                description = "描述111"
//                                group = "group1"// 修改 channel 的 group 也无法改变类似于 importance 的值
                                setSound(null, null)// 关闭提示音
                                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC // 测试结果（miui）： channel 创建后无法修改；public 卸载后重装还是 public；secret 卸载后重装可以改成 public
                            })

            // 使用 NotificationManager.IMPORTANCE_HIGH，会显示 head up 通知，但前提是设置里允许了悬浮通知
            // NotificationManager.IMPORTANCE_HIGH 在 miui 设置里显示的重要程度是紧急，
            // IMPORTANCE_DEFAULT 对应的是高，IMPORTANCE_LOW 对应的是中，IMPORTANCE_MIN对应低
            // 已经创建的 channel，无法在代码里将它的 importance 改成更高，但可以改成更低
            notificationManager?.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID_IMPORTANCE, "名称2222", NotificationManager.IMPORTANCE_HIGH)
                            .apply {
                                description = "描述22222"
                            }
            )

            notificationManager?.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID_VIBRATE, "名称3333", NotificationManager.IMPORTANCE_DEFAULT)
                            .apply {
                                description = "描述33333"
                                enableVibration(true)
                            })

            notificationManager?.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID_LOCK_SCREEN, "锁屏显示", NotificationManager.IMPORTANCE_DEFAULT)
                            .apply {
                                description = "可以显示在锁屏"
                                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                            })
            notificationManager?.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID_GROUP_SUMMARY, "group summary", NotificationManager.IMPORTANCE_DEFAULT)
                            .apply {
                                description = "group summary"
                                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                            })
            notificationManager?.createNotificationChannel(
                    // NotificationManager.IMPORTANCE_LOW 的通知在 pixel Android 10 上会显示到一个 Silent notification 分区中
                    NotificationChannel(CHANNEL_ID_LOW, "低优先级", NotificationManager.IMPORTANCE_LOW)
                            .apply {
                            })
            notificationManager?.createNotificationChannel(
                    // NotificationManager.IMPORTANCE_MIN 的通知在 pixel Android 10 上会显示到一个 Silent notification 分区中
                    // 并且内容被折叠起来，顶部的状态栏也不会有图标提示
                    NotificationChannel(CHANNEL_ID_MIN, "低优先级", NotificationManager.IMPORTANCE_MIN)
                            .apply {
                            })
            // group
            notificationManager?.createNotificationChannelGroup(NotificationChannelGroup("group1", "Group1"))
            val notificationChannel1 = NotificationChannel(CHANNEL_ID_GROUP1,
                    "Group1Channel1", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel1.group = "group1"
            notificationManager?.createNotificationChannel(notificationChannel1)
            val notificationChannel2 = NotificationChannel(CHANNEL_ID_GROUP2,
                    "Group1Channel2", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel2.group = "group1"
            notificationManager?.createNotificationChannel(notificationChannel2)
        }
    }

    class CustomStyle : NotificationCompat.Style() {

    }

    companion object {
        private const val CHANNEL_ID_NORMAL = "1"
        private const val CHANNEL_ID_IMPORTANCE = "2"
        private const val CHANNEL_ID_VIBRATE = "4"
        private const val CHANNEL_ID_LOCK_SCREEN = "5"
        private const val CHANNEL_ID_GROUP_SUMMARY = "6"
        private const val CHANNEL_ID_LOW = "7"
        private const val CHANNEL_ID_MIN = "9"

        private const val CHANNEL_ID_GROUP1 = "group1_channel1"
        private const val CHANNEL_ID_GROUP2 = "group1_channel2"
        private const val ACTION_NOTIFICATION_CLICK = "cn.dozyx.action.notification"

        private const val GROUP_KEY_TEST = "cn.dozyx.GROUP_KEY_TEST"

        private const val ACTION_NOTIFICATION_DELETE = "action_delete"
        private const val EXTRA_NOTIFICATION_ID = "notification_id"

        private const val NOTIFICATION_ID_PROGRESS = 10000;
    }
}