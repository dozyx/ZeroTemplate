package cn.dozyx.template.justfortest

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.util.Printer
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ThreadUtils
import kotlinx.android.synthetic.main.activity_meaningless.*
import timber.log.Timber


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        button.setOnClickListener{
            val start = System.currentTimeMillis()
            val webView = WebView(this.applicationContext)
            webView.settings.userAgentString
            webView.destroy()
            Timber.d("MeaninglessActivity.onCreate ${System.currentTimeMillis() - start}")

        }
        tv_link.text = ""
    }

    private fun startLoadingAnim() {
        val z1 = PropertyValuesHolder.ofFloat("translationZ", SizeUtils.dp2px(1F).toFloat())
        val x1 = PropertyValuesHolder.ofFloat("translationX", SizeUtils.dp2px(10F).toFloat())
        val animator1 = ObjectAnimator.ofPropertyValuesHolder(view_first, z1, x1)
        animator1.repeatCount = ObjectAnimator.INFINITE
        animator1.repeatMode = ObjectAnimator.REVERSE

        val z2 = PropertyValuesHolder.ofFloat("translationZ", SizeUtils.dp2px(0F).toFloat())
        val x2 = PropertyValuesHolder.ofFloat("translationX", -SizeUtils.dp2px(10F).toFloat())
        val animator2 = ObjectAnimator.ofPropertyValuesHolder(view_second, z2, x2)
        animator2.repeatCount = ObjectAnimator.INFINITE
        animator2.repeatMode = ObjectAnimator.REVERSE

        val set = AnimatorSet()
        set.duration = 1000
        set.playTogether(animator1, animator2)
        set.start()
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

    companion object {
        private val TAG = "MeaninglessActivity"
    }
}
