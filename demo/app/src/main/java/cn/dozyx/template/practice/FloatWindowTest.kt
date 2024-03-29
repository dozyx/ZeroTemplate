package cn.dozyx.template.practice

import android.app.ActivityManager
import android.app.Presentation
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Process
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import cn.dozyx.constant.Shakespeare
import cn.dozyx.core.ex.dp
import cn.dozyx.template.MainActivity
import cn.dozyx.template.R
import cn.dozyx.template.accessibility.AccessibilityUtils
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.test_text.view.*
import kotlinx.android.synthetic.main.test_transition_anim.*
import timber.log.Timber
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import kotlin.random.Random

class FloatWindowTest : BaseTestActivity() {
    var params: WindowManager.LayoutParams? = null
    private lateinit var floatView: View
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Timber.d("permission granted: ${PermissionUtils.isGrantedDrawOverlays()} $it")
            Timber.d("permission granted: ${canDrawOverlaysFix(this)}")
            Timber.d("permission granted: ${hasPermission(this, "OP_SYSTEM_ALERT_WINDOW")}")
            Timber.d("permission granted: ${getWindoOverLayAddedOrNot2()}")
            Handler().postDelayed({

                Timber.d("permission granted: ${PermissionUtils.isGrantedDrawOverlays()}")
            }, 400)
        }
    }

    override fun initActions() {

        addButton("权限检查") {
            checkOverlaysPermission()
        }

        addButton("授权") {
            requestOverlaysPermission()
        }
        addButton("service float") {
            startFloatService()
        }

        addButton("activity float") {
            openSettings()
            showFloatView()
            Timber.d("FloatWindowActivity.initActions floatView root ${floatView.rootView}")
            Timber.d("FloatWindowActivity.initActions decorView root ${window.decorView.rootView}")
        }

        addButton("remove float") {
            val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            windowManager.removeView(floatView)
        }

        addButton("settings") {
            openSettings()
        }

        addButton("accessibility settings") {
            AccessibilityUtils.startAccessibilitySettings(this);
        }

        addButton("clear top") {
            clearTopActivity()
        }

        addButton("presentation") {
            showFloatByPresentation()
        }
    }

    private fun showFloatByPresentation() {
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val layoutParams = getBaseWindowLayoutParams()
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        layoutParams.type = 2037
        layoutParams.flags = layoutParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        layoutParams.dimAmount = 0.6F

        val presentation = Presentation(this, windowManager.defaultDisplay, R.style.no_frame_dialog)
        presentation.setContentView(createFloatView())
        if (presentation.window != null) {
            // 默认的 attributes 的 flags 为 0x800002，
            // 即 WindowManager.LayoutParams.FLAG_SPLIT_TOUCH | WindowManager.LayoutParams.FLAG_DIM_BEHIND
            // dimAmount 为 0.6
            presentation.window!!.attributes = layoutParams
        }
        presentation.setCanceledOnTouchOutside(true)
        presentation.show()
    }

    private fun createFloatView(): View {
        val floatView = object : FrameLayout(this) {
            override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                Timber.d("dispatchKeyEvent ${KeyEvent.keyCodeToString(event.keyCode)}")
                if (event.keyCode == KeyEvent.KEYCODE_HOME) {
                    Timber.d("dispatchKeyEvent intercept home")
                    return true
                }
                // flags 不设置为 WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE 的话可以拦截到 back 事件，但 home 事件没办法拦截
                if (event.keyCode == KeyEvent.KEYCODE_BACK) {
//                    Timber.d("dispatchKeyEvent intercept back")
//                    return true
                }
                return super.dispatchKeyEvent(event)
            }

            override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//                Timber.d("dispatchTouchEvent ${MotionEvent.actionToString(ev.action)}")
                if (ev.action == MotionEvent.ACTION_OUTSIDE) {
                    return true
                }
                return super.dispatchTouchEvent(ev)
            }
        }
        LayoutInflater.from(this).inflate(R.layout.float_view, floatView)
        // 不会触发 activity 的生命周期

        Timber.d("FloatWindowActivity.showFloatView ${floatView.parent}")

        floatView.isFocusable = true
        floatView.isFocusableInTouchMode = true
        floatView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                Timber.d("intercept back")
//                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        floatView.findViewById<View>(R.id.btn_float).setOnClickListener {
            Timber.d("FloatWindowActivity.showFloatView click start")
//            clearTopActivity()
//            sendBroadcast()
//            sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
            val spannableString =
                SpannableString(Shakespeare.TITLES[Random.nextInt(Shakespeare.TITLES.size - 1)] + Random.nextInt())
            spannableString.setSpan(
                AbsoluteSizeSpan(24.dp.toInt()),
                0,
                spannableString.length / 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            floatView.findViewById<TextView>(R.id.tv_info).text = spannableString
//            finish()
            Timber.d("FloatWindowActivity.showFloatView click end")
        }
        return floatView
    }

    private fun getBaseWindowLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
            flags = flags or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            format = PixelFormat.TRANSLUCENT
            screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            packageName = packageName
        }
    }

    private fun openSettings() {
        val intent1 = IntentUtils.getLaunchAppDetailsSettingsIntent(packageName)
        intent1.flags = intent1.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent1)
//        startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent("com.UCMobile"))
    }

    private fun startFloatService() {
        val intent = Intent(this, FloatWindowsServices::class.java)
        if (FloatWindowsServices.isServiceRunning) {
            stopService(intent)
        }
        startService(intent)
    }

    private fun requestOverlaysPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*PermissionUtils.requestDrawOverlays(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        Timber.d("FloatWindowActivity.onGranted")
                    }

                    override fun onDenied() {
                        Timber.d("FloatWindowActivity.onDenied")
                    }
                })*/
            /*val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.parse("package:" + Utils.getApp().packageName)
                startActivityForResult(intent, 101)*/
            resultLauncher.launch(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).also {
                it.data = Uri.parse("package:" + Utils.getApp().packageName)
            })
        }
    }

    private fun checkOverlaysPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Timber.d("permission granted: ${PermissionUtils.isGrantedDrawOverlays()}")
        }
    }

    private fun clearTopActivity() {
        // 关闭所有打开之后没有正确关闭的设置页面
        // 小米 8、Android9、MIUI11 上没办法通过这种方式关闭上层的 Activity
//        moveTaskFront() // 无效
        val removeAllSettingsIntent = Intent(this, this::class.java)
        removeAllSettingsIntent.action = "IntentConstants.Action.CLEAR_TOP"
        removeAllSettingsIntent.flags =
            Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(removeAllSettingsIntent)
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun moveTaskFront() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = activityManager.getRunningTasks(20)
        var currentTaskId: Int? = null
        runningTasks.forEach {
            if (it.baseActivity?.packageName == packageName) {
                currentTaskId = it.id
                return@forEach
            }
        }
        currentTaskId?.let {
            activityManager.moveTaskToFront(it, ActivityManager.MOVE_TASK_WITH_HOME)
        }
    }

    @Throws(UnknownError::class)
    fun hasPermission(appContext: Context, appOpsServiceId: String?): Boolean {
        val appInfo = appContext.applicationInfo
        val pkg = appContext.packageName
        val uid = appInfo.uid
        val appOpsClass: Class<*>
        val appOps = appContext.getSystemService(APP_OPS_SERVICE)
        return try {
            appOpsClass = Class.forName("android.app.AppOpsManager")
            val checkOpNoThrowMethod = appOpsClass.getMethod(
                "checkOpNoThrow",
                Integer.TYPE,
                Integer.TYPE,
                String::class.java
            )
            val opValue = appOpsClass.getDeclaredField(appOpsServiceId)
            val value = opValue.getInt(Int::class.java)
            val result = checkOpNoThrowMethod.invoke(appOps, value, uid, pkg)
            result.toString().toInt() == 0 // AppOpsManager.MODE_ALLOWED
        } catch (e: ClassNotFoundException) {
            throw UnknownError("class not found")
        } catch (e: NoSuchMethodException) {
            throw UnknownError("no such method")
        } catch (e: NoSuchFieldException) {
            throw UnknownError("no such field")
        } catch (e: InvocationTargetException) {
            throw UnknownError("invocation target")
        } catch (e: IllegalAccessException) {
            throw UnknownError("illegal access")
        }
    }


    /**
     * https://stackoverflow.com/questions/46173460/why-in-android-8-method-settings-candrawoverlays-returns-false-when-user-has
     */
    private fun canDrawOverlaysFix(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) true else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
            Settings.canDrawOverlays(context)
        } else {
            if (Settings.canDrawOverlays(context)) return true
            try {
                val mgr = context.getSystemService(WINDOW_SERVICE) as WindowManager
                    ?: return false
                //getSystemService might return null
                val viewToAdd = View(context)
                val params: WindowManager.LayoutParams = WindowManager.LayoutParams(
                    0,
                    0,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT
                )
                viewToAdd.layoutParams = params
                mgr.addView(viewToAdd, params)
                mgr.removeView(viewToAdd)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            false
        }
    }

    fun getWindoOverLayAddedOrNot2(): Boolean {
        val sClassName = "android.provider.Settings"
        try {
            val classToInvestigate = Class.forName(sClassName)
            val method: Method = classToInvestigate.getDeclaredMethod(
                "isCallingPackageAllowedToDrawOverlays",
                Context::class.java,
                Int::class.javaPrimitiveType,
                String::class.java,
                Boolean::class.javaPrimitiveType
            )
            val value: Any =
                method.invoke(null, this, Process.myUid(), packageName, false)
            // Dynamically do stuff with this class
            // List constructors, fields, methods, etc.
        } catch (e: ClassNotFoundException) {
            // Class not found!
        } catch (e: java.lang.Exception) {
            // Unknown exception
            e.printStackTrace()
        }
        return false
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
        floatView = createFloatView()
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    or WindowManager.LayoutParams.FLAG_FULLSCREEN
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
             or WindowManager.LayoutParams.FLAG_DIM_BEHIND,
            PixelFormat.TRANSLUCENT
        )
        layoutParams.dimAmount = 0.8f // 不设置的话就是全黑的
        windowManager.addView(
            floatView,
            layoutParams
        )
    }

    fun sendBroadcast() {
        val intent = Intent()
        intent.action = "ACTION_SERVICE_ENABLE_CAPTURE"
        intent.putExtra("enabled", false)
        sendBroadcast(intent)
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
            windowView = LayoutInflater.from(applicationContext).inflate(R.layout.float_window, null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            } else {
                params.type = WindowManager.LayoutParams.TYPE_PHONE
            }
//            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            params.format = PixelFormat.TRANSLUCENT
            params.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_FULLSCREEN
            params.gravity = Gravity.RIGHT or Gravity.BOTTOM
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            windowManager.addView(windowView, params)
            button1 = windowView.findViewById<View>(R.id.float_button1) as Button
            button2 = windowView.findViewById<View>(R.id.float_button2) as Button
            listView = windowView.findViewById<View>(R.id.list) as ListView
//            listView!!.adapter = ArrayAdapter(
//                this@FloatWindowsServices, android.R.layout.simple_list_item_1,
//                Shakespeare.MORE_TITLES
//            )
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