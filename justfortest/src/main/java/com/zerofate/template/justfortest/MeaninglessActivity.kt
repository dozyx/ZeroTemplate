package com.zerofate.template.justfortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.zerofate.androidsdk.util.ToastX
import timber.log.Timber


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        findViewById<Button>(R.id.button).setOnClickListener {
            ToastX.showShort(this,"")
        }
        Timber.plant(Timber.DebugTree())
        Timber.d("debugTree")

        Logger.addLogAdapter(object :AndroidLogAdapter(){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return false
            }
        })
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.d("logger")
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
