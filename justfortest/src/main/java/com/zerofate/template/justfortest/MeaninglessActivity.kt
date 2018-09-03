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

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        findViewById<Button>(R.id.button).setOnClickListener {
            var builder = NotificationCompat.Builder(this,"0").setContentTitle("哈哈哈")
                .setContentText("ssss").setSmallIcon(R.mipmap.ic_launcher)
            var intent = PendingIntent.getActivity(this@MeaninglessActivity,0,Intent(this@MeaninglessActivity,MeaninglessActivity.javaClass),0)
            builder.setContentIntent(intent)
            var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var channel =  NotificationChannel("0","test",NotificationManager.IMPORTANCE_HIGH)
                manager.createNotificationChannel(channel)
            }
            manager.notify(111, builder.build())
        }

    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
