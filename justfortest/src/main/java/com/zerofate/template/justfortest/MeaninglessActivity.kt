package com.zerofate.template.justfortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_meaningless.*
import timber.log.Timber


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        button.setOnClickListener {
            switch_test.isChecked = !switch_test.isChecked
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
        switch_test.isChecked = true
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
