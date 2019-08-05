package cn.dozyx.template.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class SimpleService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        // 第一次调用 bindService 时回调
        // 只会触发一次，如果所有 client 进行了 unbind，这时候会触发 onUnbind，接着再 bindService，这时候并不会触发 onBind，但是，如果 onUnbind 返回 true，将触发 onRebind
        Log.d(TAG, "onBind: ")
        return object : ServiceBinder() {
            init {
                service = this@SimpleService
            }
        }
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 每一次调用 startService 时回调
        Log.d(TAG, "onStartCommand: $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        // 所有 bindService 的 client 均进行了 unbind，如果调用了 startService，则还需要调用 stopService（多次 startService 只需要一次 stopService）
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent): Boolean {
        // 所有调用 bindService 的 client 进行了 unbind（包括调用 unbindService 和 context 被销毁，但是如果 context 销毁时没有调用 unbindService 会发生内存泄露）
        // 返回 true 表示希望重新绑定时回调 onRebind
        // 如果返回 false，则只会触发一次，即在回调 onUnbind 后再进行 bindService 和 unbindService 不会触发
        // 如果返回 true 时，重新 bindService 会
        Log.d(TAG, "onUnbind: ")
//        return super.onUnbind(intent)
        return true
    }

    override fun onRebind(intent: Intent) {
        Log.d(TAG, "onRebind: ")
        // onUnbind 为 true 时才会回调
        // 当所有的 client 进行了 unbind 而 service 仍在运行，这时候进行 bindService 会触发 onRebind。
        // 总而言之，当时 onUnbind 为 true，当 onUnbind 被回调之后，再进行 bindService 会触发 onRebind
        super.onRebind(intent)
    }


    companion object {
        private val TAG = "SimpleService"
    }
}

open class ServiceBinder : Binder() {
    var service: SimpleService? = null
}