package com.zerofate.template.justfortest

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding3.view.clicks
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_meaningless.*
import timber.log.Timber
import java.util.*


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        button.clicks().compose(RxPermissions(this).ensure(Manifest.permission.ACCESS_FINE_LOCATION)).subscribe {
            switch_test.isChecked = !switch_test.isChecked
        }
        Timber.plant(Timber.DebugTree())
        Timber.d("debugTree")
        Logger.addLogAdapter(object : AndroidLogAdapter() {
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
