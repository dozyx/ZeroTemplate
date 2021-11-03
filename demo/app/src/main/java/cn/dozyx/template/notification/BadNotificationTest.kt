package cn.dozyx.template.notification

import android.os.Bundle
import android.os.Environment
import android.widget.RemoteViews
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.notification.manager.NotificationManager
import com.blankj.utilcode.util.AppUtils
import java.io.File

/**
 * 更新安装新版本，通知对应的布局 id 可能发生改变，会触发一个 RemoteServiceException Bad notification 异常
 * 这个类用来验证这个问题
 */
class BadNotificationTest : BaseTestActivity() {

    private val upgradeApkPath =
        "${Environment.getExternalStorageDirectory()}${File.pathSeparator}upgrade.apk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initActions() {
        addAction("显示通知") {
            NotificationCompat.Builder(this, NotificationManager.CHANNEL_ID_DEFAULT)
                .setSmallIcon(R.drawable.ic_notification_idlefish)
                .setCustomContentView(RemoteViews(packageName, R.layout.notification_custom_view))
                .build().let {
                    NotificationManagerCompat.from(this@BadNotificationTest).notify(0, it)
                }
        }

        addAction("安装升级") {
            AppUtils.installApp(upgradeApkPath)
        }
    }

}