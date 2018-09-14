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
            val user:User

        }

    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}

data class User @Inject constructor(val name: String, val age: Int)