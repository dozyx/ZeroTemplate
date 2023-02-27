package cn.dozyx.template

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.base.BaseTestActivity
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_base_test.*

class ImmersionBarTest : BaseTestActivity() {

    override fun initActions() {
        ImmersionBar.with(this).init()
        ImmersionBar.setTitleBar(this, app_content)
        addAction("bg") {
            // MIUI 的深色模式会自动根据背景颜色调整状态栏的字体颜色
            app_content.setBackgroundColor(getColor(android.R.color.black))
        }
        addAction("bg2") {
            app_content.setBackgroundColor(getColor(android.R.color.white))
        }

        addAction("full screen") {
            window.addFlags(FLAG_FULLSCREEN)
        }

        addAction("normal screen") {
            window.clearFlags(FLAG_FULLSCREEN)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
//        setTransparentStatusBar()
//        setStatusTextColor(true)
//        compatHighMIUI(true)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
//        ImmersionBar.setFitsSystemWindows(this)
    }

    private fun setTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            var option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                option = option or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window.decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.BLUE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private fun setStatusTextColor(useDart: Boolean) {
        if (useDart) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.BLUE
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    private fun compatHighMIUI(darkmode: Boolean) {
        val decorView = window.decorView
        if (darkmode) {
//            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)// 这样处理在小米手机上有问题
            decorView.systemUiVisibility =
                (decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        } else {
            val flag = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            decorView.systemUiVisibility = flag
        }
    }


//    override fun getLayoutId() = R.layout.test_immersion_bar
}