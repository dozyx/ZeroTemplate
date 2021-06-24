package cn.dozyx.template.accessibility

import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ProcessUtils
import timber.log.Timber

class AccessibilityTest : BaseTestActivity() {

    override fun initActions() {
        addButton("开始") {

        }

        addButton("权限检查") {
            Timber.d(
                "MyAccessibilityTest.initActions enabled: ${
                    AccessibilityUtils.isAccessibilitySettingsOn(
                        this
                    )
                }"
            )
        }

        addButton("授权") {
            // 通过 adb 命令授权更方便
            // adb shell settings put secure enabled_accessibility_services cn.dozyx.demo.app/cn.dozyx.template.accessibility.MyAccessibilityService
            // 有时候授权之后，但是无障碍并没有正常运行起来，设置中显示出故障，需要重新把开关关掉再开启
            startActivity(AccessibilityUtils.getAccessibilitySettingPageIntent(this))
        }

        addButton("正在运行") {
            val allBackgroundProcesses = ProcessUtils.getAllBackgroundProcesses()
            Timber.d("MyAccessibilityTest.initActions ${allBackgroundProcesses.size}")
        }

        addButton("停止运行") {
//            showFloatView()
            startAppDetail("com.taobao.taobao")
//            startAppDetail("com.jingdong.app.mall")
            Handler().postDelayed({ startAppDetail("com.jingdong.app.mall") }, 500)
        }

        addButton("停止运行2") {
            startAppDetail("com.jingdong.app.mall")
        }

        addButton("enable service") {
            startAccessibilityService(true)
        }

        addButton("disable service") {
            startAccessibilityService(false)
        }

    }

    private fun showFloatView() {
        val windowManager = getSystemService(WINDOW_SERVICE) as? WindowManager
        windowManager ?: return

        val rootView = TextView(this)
        rootView.text = "哈哈"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rootView.background = ColorDrawable(getColor(android.R.color.darker_gray))
        }
        rootView.gravity = Gravity.CENTER
        rootView.setOnClickListener {
            windowManager.removeView(rootView)
        }
        windowManager.addView(
            rootView,
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        )
    }

    private fun startAccessibilityService(enable: Boolean) {
        // 1. 如果辅助功能没开启，调用 startService 会启动 service，生命周期正常，但是检查辅助功能是否开启，返回是 false
        // 2. 继续进入设置打开辅助功能开关，，提示「此服务出现故障」(偶现，应该跟主动调了 startService 没关系)，过一会自动被销毁(可能是被系统回收了)
        // 3. 把开关关了再开，回调 onServiceConnected，无障碍服务正常
        // 4. 无障碍服务启动之后，继续调用 startService，会触发 onStartCommand
        val intent = Intent(this, MyAccessibilityService::class.java)
        intent.action = "accessibility.service.notify"
        intent.putExtra("enable", enable)
        startService(intent)
    }

    private fun startAppDetail(pkgName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.parse("package:$pkgName")
        intent.data = uri
//        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}