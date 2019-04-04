package com.zerofate.template.justfortest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcel
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BulletSpan
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gyf.barlibrary.ImmersionBar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding3.view.clicks
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zerofate.java.SimpleDialog
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
        ImmersionBar.with(this).statusBarColor(android.R.color.transparent).init()
        button.clicks().compose(RxPermissions(this).ensure(Manifest.permission.ACCESS_FINE_LOCATION)).subscribe {
            SimpleDialog(this).show()
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
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener{
            val rect = Rect()
            window.decorView.getGlobalVisibleRect(rect)
            val contentView = findViewById<FrameLayout>(android.R.id.content)
        }
    }

    override fun onDestroy() {
        ImmersionBar.with(this).destroy()
        super.onDestroy()
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
