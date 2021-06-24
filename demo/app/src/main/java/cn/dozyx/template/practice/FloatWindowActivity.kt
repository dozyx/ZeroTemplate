package cn.dozyx.template.practice

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.*
import android.widget.*
import cn.dozyx.constant.Shakespeare
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.Utils
import timber.log.Timber

class FloatWindowActivity : BaseTestActivity() {
    var params: WindowManager.LayoutParams? = null

    override fun initActions() {

        addButton("权限检查") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Timber.d("permission granted: ${PermissionUtils.isGrantedDrawOverlays()}")
            }
        }
        addButton("授权") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                /*PermissionUtils.requestDrawOverlays(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        Timber.d("FloatWindowActivity.onGranted")
                    }

                    override fun onDenied() {
                        Timber.d("FloatWindowActivity.onDenied")
                    }
                })*/
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.parse("package:" + Utils.getApp().packageName)
                startActivityForResult(intent, 101)
            }
        }
        addButton("service float") {
            val intent = Intent(this, FloatWindowsServices::class.java)
            if (FloatWindowsServices.isServiceRunning) {
                stopService(intent)
            }
            startService(intent)
        }

        addButton("activity float") {
            showFloatView()
        }

        addButton("占位") {

        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("FloatWindowActivity.onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("FloatWindowActivity.onResume")
    }

    override fun onPause() {
        Timber.d("FloatWindowActivity.onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.d("FloatWindowActivity.onStop")
        super.onStop()
    }


    private fun showFloatView() {
        val windowManager = getSystemService(WINDOW_SERVICE) as? WindowManager
        windowManager ?: return
        val rootView = LayoutInflater.from(applicationContext).inflate(R.layout.float_view, null)
        windowManager.addView(
            rootView,
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        )
        // 不会触发 activity 的生命周期

        rootView.isFocusable = true
        rootView.isFocusableInTouchMode = true
        rootView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Timber.d("intercept back")
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    class FloatWindowsServices : Service() {
        private lateinit var windowView: View
        private lateinit var windowManager: WindowManager
        private lateinit var params: WindowManager.LayoutParams
        private var button1: Button? = null
        private var button2: Button? = null
        private var listView: ListView? = null
        override fun onBind(intent: Intent): IBinder? {
            return null
        }

        override fun onCreate() {
            super.onCreate()
            windowManager = application.getSystemService(WINDOW_SERVICE) as WindowManager
            params = WindowManager.LayoutParams()
            showFloat()
            isServiceRunning = true
        }

        fun showFloat() {
            windowView = LayoutInflater.from(this).inflate(R.layout.float_window, null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                params.type = WindowManager.LayoutParams.TYPE_PHONE
            }
            params.format = PixelFormat.TRANSLUCENT
            params.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_FULLSCREEN
            params.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.MATCH_PARENT
            windowManager.addView(windowView, params)
            button1 = windowView.findViewById<View>(R.id.float_button1) as Button
            button2 = windowView.findViewById<View>(R.id.float_button2) as Button
            listView = windowView.findViewById<View>(R.id.list) as ListView
            listView!!.adapter = ArrayAdapter(
                this@FloatWindowsServices, android.R.layout.simple_list_item_1,
                Shakespeare.MORE_TITLES
            )
            button1!!.setOnClickListener {
                params.width = 800
                windowManager.updateViewLayout(windowView, params)
            }
            button2!!.setOnClickListener {
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                windowManager.updateViewLayout(windowView, params)
            }
            listView!!.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    Toast.makeText(
                        this@FloatWindowsServices, parent.adapter.getItem(position) as String,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        override fun onDestroy() {
            super.onDestroy()
            windowManager!!.removeView(windowView)
            isServiceRunning = false
        }

        companion object {
            var isServiceRunning = false
        }
    }
}