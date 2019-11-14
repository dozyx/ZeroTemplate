package cn.dozyx.template.justfortest

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Printer
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.core.context.CustomContextWrapper
import cn.dozyx.template.DelayTextWatcher
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_meaningless.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    var time = 0
    lateinit var alertDialog: AlertDialog
    lateinit var thread: Thread
    var threadLooper: Looper? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_meaningless)
//        edit.requestFocus()
        // keyboardEnable true 不会自动弹出，false 会自动弹出
        // 不设置也沉浸式会自动弹出
        ImmersionBar.with(this).init()
        thread = Thread {
            Looper.prepare()
            Timber.d("MeaninglessActivity.onCreate looper start")
            Looper.loop()
            threadLooper = Looper.myLooper()
        }

        text.setOnClickListener {
            //            threadLooper?.quit()
        }
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            image.outlineProvider = ViewOutlineProvider.BOUNDS
//            image.outlineProvider = ViewOutlineProvider.PADDED_BOUNDS
//            image.outlineProvider = object :ViewOutlineProvider(){
//                override fun getOutline(view: View, outline: Outline) {
//                    Timber.d("MeaninglessActivity.getOutline ${view.width} ${view.height}")
//                    outline.setOval(0,0,view.width,view.height)
//                }
//            }
            image.clipToOutline = true
        }


        testIdleHandler()
        edit.isEnabled = false
    }

    private fun testIdleHandler() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            Timber.d("MeaninglessActivity.testIdleHandler 执行 message 1")
        }, 500) // 延时比较短时还是会先执行 message
        if (SDK_INT >= Build.VERSION_CODES.M) {
            Looper.myLooper()!!.queue.addIdleHandler {
                Timber.d("MeaninglessActivity.testIdleHandler 执行 IdleHandler  ${ThreadUtils.isMainThread()}")
                false
            }
        }
        handler.postDelayed(Runnable {
            Timber.d("MeaninglessActivity.testIdleHandler 执行 message 2")
        }, 50)
    }

    private fun testLooperPrinter() {
        Looper.myLooper()!!.setMessageLogging(object : Printer {
            override fun println(x: String?) {
                Timber.d("MeaninglessActivity.println $x")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }

    /*override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CustomContextWrapper.wrap(newBase, 1.0f))
    }*/

    /*override fun getResources(): Resources {
        val resources = super.getResources()
        val oldConfig = resources.configuration
        Timber.d("MeaninglessActivity.getResources ${oldConfig.fontScale}")
        oldConfig.fontScale = 2f
//        resources.displayMetrics.scaledDensity = resources.displayMetrics.density
        val newConfig = Configuration(oldConfig)
        if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            createConfigurationContext(newConfig)
            // 这个没有效果
        }
        Timber.d("MeaninglessActivity.getResources ${resources.displayMetrics.scaledDensity } ${resources.displayMetrics.density}")
        resources.updateConfiguration(newConfig,resources.displayMetrics)
        return resources
    }*/

}
