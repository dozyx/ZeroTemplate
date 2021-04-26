package cn.dozyx.template.activity

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ScreenUtils
import timber.log.Timber

class WindowTest : BaseTestActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//    findViewById<View>(android.R.id.content).fitsSystemWindows = true

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
      window.decorView.setOnApplyWindowInsetsListener { v, insets ->
        Timber.d("onApplyWindowInsets $insets")
        // 系统 UI 导致的 inset，比如系统状态栏和底部状态栏
//        insets.inset(100, 300, 0, 0) // 设置这个没什么用。。。
        insets
      }
    }
  }

  override fun initActions() {
    addAction(object : Action("getWindowVisibleDisplayFrame") {
      override fun run() {
        val rect = Rect()
        Handler().postDelayed({
          window.decorView.rootView.getWindowVisibleDisplayFrame(rect)
          Timber.d("getWindowVisibleDisplayFrame $rect")
        }, 3000)
      }
    })

    addAction(object : Action("decorView") {
      override fun run() {
        Timber.d("decorView top ${window.decorView.top} bottom ${window.decorView.bottom}")
        Timber.d("decorView height ${window.decorView.height}")
        Timber.d("screen height ${ScreenUtils.getScreenHeight()}")
      }
    })

    addAction(object : Action("screen") {
      override fun run() {
        val locations = IntArray(2)
        // 分屏会导致 screen 坐标变化，miui 的小窗不会
        window.decorView.getLocationOnScreen(locations)
        Timber.d("screen: ${locations[0]} ${locations[1]}")
      }
    })

    addAction(object : Action("full screen") {
      override fun run() {
//        NotchScreenManager.getInstance().setDisplayInNotch(this@WindowTest)

        // 刘海屏需要另外的处理 https://www.jianshu.com/p/2b8db60ba8df
        window.setFlags(
          WindowManager.LayoutParams.FLAG_FULLSCREEN,
          WindowManager.LayoutParams.FLAG_FULLSCREEN)// 这种方式布局不会绘制到状态栏中

        /*window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)*/

        // android 11 使用 WindowInsetsController
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
          window.setDecorFitsSystemWindows(false)
          window.insetsController?.hide(WindowInsets.Type.statusBars())
          // hide 之后如何显示状态栏
          window.insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        }*/

      }
    })

    addAction(object : Action("CUTOUT") {
      override fun run() {
        val window = window
        if (window != null) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val layoutParams = window.attributes
            // 不适配异形屏的话，在设置全屏时，状态栏还是显示不了内容
            layoutParams.layoutInDisplayCutoutMode =
              WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = layoutParams
          }
          window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
      }
    })

    addAction(object : Action("remove full screen") {
      override fun run() {
        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      }
    })


  }
}