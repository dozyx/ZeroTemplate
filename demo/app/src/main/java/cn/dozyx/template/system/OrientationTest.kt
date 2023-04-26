package cn.dozyx.template.system

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.LanguageUtils
import timber.log.Timber
import java.util.Locale

class OrientationTest : BaseTestActivity() {
    private var isFullScreen = false

    override fun initActions() {
        addAction(object : Action("竖屏") {
            override fun run() {
                val intent = Intent(this@OrientationTest, LandscapeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
        })

        addAction(object : Action("locale") {
            override fun run() {
                LanguageUtils.applyLanguage(Locale.FRENCH)
            }
        })

        addAction(object : Action("Dialog") {
            override fun run() {
                CustomDialog(this@OrientationTest).show()
            }
        })

        addAction(object : Action("full screen") {
            override fun run() {
                isFullScreen = isFullScreen.not()
                requestedOrientation = when {
                    isFullScreen -> ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
                    else -> ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
                }
                applyVideoSystemUi(this@OrientationTest, isFullScreen)
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    fun applyVideoSystemUi(activity: Activity, isFullScreen: Boolean) {
//        ImmersionBar.with(this).fullScreen(isFullScreen).hideBar(BarHide.FLAG_HIDE_BAR).init()
//        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility
//            .or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//            .or(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
//            .or(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        WindowInsetsControllerCompat(activity.window, activity.window.decorView).let {
            if (isFullScreen) {
                it.hide(WindowInsetsCompat.Type.systemBars())
                it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            } else {
                it.show(WindowInsetsCompat.Type.systemBars())
            }
        }
        Timber.d("OrientationTest.applyVideoSystemUi ${Integer.toBinaryString(window.decorView.systemUiVisibility)}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("OrientationTest.onConfigurationChanged $newConfig")
    }

    private class CustomDialog(context: Context) :
        Dialog(context, R.style.Theme_AppCompat_Light_Dialog) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_orientation_test)
            Timber.d("CustomDialog.onCreate")
        }
    }

}