package cn.dozyx.template.performance

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.tracing.Trace
import cn.dozyx.template.MainActivity
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LaunchOptimizeTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("LaunchOptimizeTest.onCreate start")
        Trace.beginSection("Main onCreate")
        super.onCreate(savedInstanceState)
        val slowTextView = SlowTextView(this)
        slowTextView.text = "哈哈哈"
        setContentView(slowTextView)
        /*if (DebugConfig.DEBUG_LAUNCH) {
            for (i in 0..100) {
                Thread(Runnable {
                    Thread.sleep(10)
                }, "custom work thread#$i").start()
            }
            Thread.sleep(500)
        }*/
        Trace.endSection()
        Timber.d("LaunchOptimizeTest.onCreate end")

        Handler().postDelayed({
            AlertDialog.Builder(this).setTitle("标题").setMessage("消息").show()
        }, 5000)
        window.decorView.post {
            Timber.d("window.decorView.post")
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("LaunchOptimizeTest.onResume")
//        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // activity 上面有其他 dialog 或者 popup 也会触发这个回调
        Timber.d("LaunchOptimizeTest.onWindowFocusChanged $hasFocus")
    }

    private class SlowTextView(context: Context) : AppCompatTextView(context) {
        init {
            Timber.d("SlowTextView.init")
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            Timber.d("SlowTextView.onMeasure start")
            Thread.sleep(TimeUnit.SECONDS.toMillis(3))
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            Timber.d("SlowTextView.onMeasure end")
        }

        override fun onDraw(canvas: Canvas?) {
            Timber.d("SlowTextView.onDraw start")
            super.onDraw(canvas)
            Timber.d("SlowTextView.onDraw end")
        }
    }

}