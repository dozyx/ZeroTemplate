package cn.dozyx.template.performance

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import androidx.core.os.TraceCompat
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.FileUtils
import timber.log.Timber
import java.io.File
import kotlin.concurrent.thread

/**
 * @author dozyx
 * @date 9/10/20
 */
@SuppressLint("MissingPermission")
class TraceTest : BaseTestActivity() {
    private val lock = Object()
    private val lock2 = Object()

    override fun initActions() {
        addAction(object : Action("start") {
            @SuppressLint("HardwareIds")
            override fun run() {
                TraceCompat.beginSection("start_trace")

                // 启动很多线程
                for (i in 1..20) {
                    Thread({
                        TraceCompat.beginSection("multi_thread_${i}_run")
                        doLongTimeJob("thread1_${i}")
                        TraceCompat.endSection()
                    }, "multi_thread_$i").start()
                }

                // 调用系统服务
                TraceCompat.beginSection("getDeviceId1")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
                    } catch (e: Exception) {

                    }
                }
                TraceCompat.endSection()
                TraceCompat.beginSection("getDeviceId2")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
                    } catch (e: Exception) {

                    }
                }
                TraceCompat.endSection()

                // 文件 IO
                TraceCompat.beginSection("file_io")
                doLongTimeJob("io")
                TraceCompat.endSection()

                // 启动线程2
                Thread {
                    TraceCompat.beginSection("thread_2_run")
                    synchronized(lock) {
                        Thread.sleep(200)
                    }
                    TraceCompat.endSection()
                }.start()

                // 同步锁执行
                Thread.sleep(20)
                TraceCompat.beginSection("require_lock")
                synchronized(lock) {
                    Timber.d("TraceTest.run")
                }
                TraceCompat.endSection()

                Thread({
                    TraceCompat.beginSection("thread3_run")
                    synchronized(lock2) {
                        doLongTimeJob("thread3")
                        lock2.notify()
                    }
                    TraceCompat.endSection()
                }, "notify_thread").start()

                TraceCompat.beginSection("lock2_wait")
                synchronized(lock2) {
                    lock2.wait()
                }
                TraceCompat.endSection()

                TraceCompat.endSection()
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 注意：如果在启动过程中回到了后台（比如锁屏），那么这个获取的焦点的时间会变长，不过考虑启动时间的话，并不需要在意 hasFocus 是否为 true？
        TraceCompat.beginSection("onWindowFocusChanged")
        Timber.d("TraceTest.onWindowFocusChanged $hasFocus")
        doLongTimeJob("onWindowFocusChanged")
        TraceCompat.endSection()
    }

    private fun doLongTimeJob(label: String) {
        for (i in 0..100) {
            FileUtils.createFileByDeleteOldFile(File(cacheDir, "trace$label"))
        }
    }
}