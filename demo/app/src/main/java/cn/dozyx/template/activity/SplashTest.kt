package cn.dozyx.template.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber

class SplashTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarColor(android.R.color.transparent)
            .statusBarDarkFont(false)
            .navigationBarDarkIcon(false)
            .navigationBarEnable(true)
            .navigationBarWithKitkatEnable(false)
//            .fullScreen(true)
            .init()
        update()
//        StatusBarUtils.fitsToolBarAndNavigationBar(this, null, 0)
//        StatusBarUtils.statusBarDarkMode(this, true)
//        StatusBarUtils.fitsNavigationBar(this, true)
        btn.setOnClickListener {
            update()
        }
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            window.decorView.setOnApplyWindowInsetsListener { v, insets ->
                Timber.d("onApplyWindowInsets $insets")
//                 系统 UI 导致的 inset，比如系统状态栏和底部状态栏
//        insets.inset(100, 300, 0, 0) // 设置这个没什么用。。。
                insets
            }
            // 简单的返回 insets，却可以实现类似于 FLAG_TRANSLUCENT_NAVIGATION 的效果？
        }*/
    }

    private fun update() {
//        window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // 部分手机不设置 FLAG_TRANSLUCENT_NAVIGATION 会导致 View#background 与 windowBackground 中的 layer-list 的居中不一致。
        // 但设置 FLAG_TRANSLUCENT_NAVIGATION 会导致导航栏显示半透明灰色，不是那么地「沉浸」
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.apply {
    //                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE
        }
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun getLayoutId() = R.layout.activity_splash
}