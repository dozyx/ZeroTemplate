package cn.dozyx.template.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SimpleService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        // 第一次调用 bindService 时回调
        // 只会触发一次，如果所有 client 进行了 unbind，这时候会触发 onUnbind，接着再 bindService，这时候并不会触发 onBind，但是，如果 onUnbind 返回 true，将触发 onRebind
        Timber.d("SimpleService.onBind")
        return object : ServiceBinder() {
            init {
                service = this@SimpleService
            }
        }
    }

    override fun onCreate() {
        Timber.d("SimpleService.onCreate")
        super.onCreate()
//        startActivity(Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        })
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 每一次调用 startService 时回调
        Timber.d("SimpleService.onStartCommand $startId")
        return START_STICKY
    }

    override fun onDestroy() {
        // 所有 bindService 的 client 均进行了 unbind，如果调用了 startService，则还需要调用 stopService（多次 startService 只需要一次 stopService）
        Timber.d("SimpleService.onDestroy")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent): Boolean {
        // 所有调用 bindService 的 client 进行了 unbind（包括调用 unbindService 和 context 被销毁，但是如果 context 销毁时没有调用 unbindService 会发生内存泄露）
        // 返回 true 表示希望重新绑定时回调 onRebind
        // 如果返回 false，则只会触发一次，即在回调 onUnbind 后再进行 bindService 和 unbindService 不会触发
        // 如果返回 true 时，重新 bindService 会
        Timber.d("SimpleService.onUnbind")
//        return super.onUnbind(intent)
        return true
    }

    override fun onRebind(intent: Intent) {
        Timber.d("SimpleService.onRebind")
        // onUnbind 为 true 时才会回调
        // 当所有的 client 进行了 unbind 而 service 仍在运行，这时候进行 bindService 会触发 onRebind。
        // 总而言之，当时 onUnbind 为 true，当 onUnbind 被回调之后，再进行 bindService 会触发 onRebind
        super.onRebind(intent)
    }

    companion object{

        fun start(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startService(Intent(context.applicationContext, SimpleService::class.java))
            }
        }

        fun startDelay(context: Context, delaySeconds: Int = 5) {
            GlobalScope.launch {
                delay(delaySeconds * 1000L)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startService(
                        Intent(
                            context.applicationContext,
                            SimpleService::class.java
                        )
                    )
                }
            }
        }
    }
}

open class ServiceBinder : Binder() {
    var service: SimpleService? = null
}