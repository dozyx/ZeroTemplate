package com.zerofate.template.justfortest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.dozeboy.android.core.utli.log.ZLog
import com.zerofate.androidsdk.util.ToastX
import javax.inject.Inject

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        ZLog.d("onCreate: ")
        findViewById<Button>(R.id.button).setOnClickListener {
            ToastX.showShort(this,"开机速度" + "首先在完全关机状态下同时开机，在iPad Mini 2上iOS 12的启动速度要比iOS 11快2秒时间，在主屏幕上移动更流畅更灵敏。" +
                    "GeekBench 4" +
                    "启动跑分软件GeekBench 4，iOS 11.4的单核成绩为1295分，多核成绩为2179分，iOS 12的单核成绩为1293分，多核成绩为2203分，两者之间的差距并不大。" +
                    "在GPU性能跑分方面，iOS 12的得分要略高一些，得分为591分，iOS 11为588分。运行iOS 12的iPhone X得分为17198分，而iOS 11.4的得分为14314，提升幅度超过20%。" +
                    "游戏" +
                    "随后AppleInsider测试了三款游戏。《愤怒的小鸟2》在iOS 12中启动时间为19秒，在iOS 11.4中启动时间为31秒。接下来的《精灵宝可梦Go》中两个系统版本均为39秒左右。在《Asphalt 8》中两个版本均为32秒左右。" +
                    "APP" +
                    "在相机上，两款系统之间的启动速度基本上相似。在原生新闻APP中，iOS 12明显更加流畅，不过奇怪的是iOS 11加载时间更快，为8秒，而iOS 12为12秒。" +
                    "接下来五款APP：iBooks and Books, the Appleinsider app, Amazon Prime Video, Google Drive和YouTube，iOS 12的启动时间快1秒左右。最后“TV”的应用中，iOS 12要比iOS 11.4快5秒时间。");
        }

    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
