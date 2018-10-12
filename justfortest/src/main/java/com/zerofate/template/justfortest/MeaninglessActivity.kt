package com.zerofate.template.justfortest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.dozeboy.android.core.utli.log.ZLog

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        ZLog.d("onCreate: ")
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent();
            intent.setPackage("")
        }

    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}