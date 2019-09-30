package cn.dozyx.template.justfortest

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.core.context.CustomContextWrapper
import cn.dozyx.template.DelayTextWatcher
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

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_meaningless)
//        edit.requestFocus()
        // keyboardEnable true 不会自动弹出，false 会自动弹出
        // 不设置也沉浸式会自动弹出
        ImmersionBar.with(this).init()

        text.setOnClickListener {
            var picUrl = "http://pic.lepass.cn/hashpic/73813234dddc6167bfaa415f1bc472f9.jpg"
            val glideUrl = GlideUrl(picUrl, LazyHeaders.Builder()
//            .addHeader("User-Agent", "Android_10092_1_9_28_MI 8")
            .addHeader("User-Agent", "Android")
            .build())
            Glide.with(this).load(glideUrl).into(image)
            /*val httpClient = OkHttpClient()
            httpClient.newCall(Request.Builder().url(picUrl).build()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Timber.d("MeaninglessActivity.onFailure")
                }

                override fun onResponse(call: Call, response: Response) {
                    Timber.d("MeaninglessActivity.onResponse")
                    var bitmap = BitmapFactory.decodeStream(response.body()?.byteStream())
                    runOnUiThread {
                        image.setImageBitmap(bitmap)
                    }
                }
            })*/
        }
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
