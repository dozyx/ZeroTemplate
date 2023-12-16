package cn.dozyx.template.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.template.notification.NotificationTest

class BroadcastDeliverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendBroadcast(Intent(NotificationTest.ACTION_NOTIFICATION_CLICK))
        finish()
    }
}