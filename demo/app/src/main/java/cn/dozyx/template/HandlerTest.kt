package cn.dozyx.template

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import cn.dozyx.template.activity.LifeCycleTest
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ReflectUtils
import timber.log.Timber
import java.util.*

/**
 * @author dozyx
 * @date 2020-01-05
 */
class HandlerTest : BaseTestActivity() {
    private val tokens = LinkedList<Int>()
    private val myHandler = @SuppressLint("HandlerLeak")
    object : Handler(Handler.Callback {
        // 利用 Callback 可以 hook Handler 的消息
        Timber.d("HandlerTest.handleMessage $it")
        false
    }) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_TOKEN -> {
//                    Timber.d("HandlerTest.handleMessage 收到 MSG_TOKEN")
                    // 死循环
                    sendToTarget()
                }
            }
        }
    }
    private val mainHandler = Handler()
    private val loopRunnable:Runnable = Runnable {
        // do something
        scheduleLoop()
    }

    private fun scheduleLoop() {
        mainHandler.removeCallbacks(loopRunnable)
        mainHandler.postDelayed(loopRunnable, 1)
    }


    private fun sendToTarget() {
        val message = myHandler.obtainMessage(MSG_TOKEN)
        message.sendToTarget()
    }

    override fun initActions() {
        addAction(object : Action("点击") {
            override fun run() {
                myHandler.sendEmptyMessage(1)
            }
        })

        addAction(object : Action("target") {
            override fun run() {
                sendToTarget()
            }
        })

        addAction(object : Action("loop") {
            override fun run() {
                Timber.d("start loop")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mainHandler.looper.queue.addIdleHandler {
                        Timber.d("run IdleHandler1")
                        return@addIdleHandler false
                    }
                }
                scheduleLoop()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mainHandler.looper.queue.addIdleHandler {
                        Timber.d("run IdleHandler2")
                        return@addIdleHandler false
                    }
                }
            }
        })

        addAction(object : Action("启动") {
            override fun run() {
                startActivity(Intent(this@HandlerTest, LifeCycleTest::class.java))
            }
        })

        addAction(object : Action("postSyncBarrier+发送异步消息") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val queue = myHandler.looper.queue
                    sendAsyncMsg()
                    myHandler.sendEmptyMessageDelayed(3, 200)
                    val utils = ReflectUtils.reflect(queue).method("postSyncBarrier")
                    val token: Int = utils.get()
                    Timber.d("postSyncBarrier token: $token")
                    tokens.add(token)
                    // 设置同步屏障之后只能处理异步消息
                    // 如果不移除同步屏障，再次点击按钮没有触发点击事件，但是界面并不是阻塞状态，感觉是部分事件被阻挡。猜测原因是有些触摸 event 并不是作为异步消息处理的
//                    Thread.sleep(2000)
//                    removeSyncBarrier()
                }
            }
        })

        /*addAction(object : Action("removeSyncBarrier") {
            override fun run() {
                removeSyncBarrier()
            }
        })*/
/*
        addAction(object : Action("发送异步消息") {
            override fun run() {
                sendAsyncMsg()

            }
        })*/

        addAction(object : Action("IdleHandler") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    myHandler.sendEmptyMessageDelayed(1, 100)
                    val queue = myHandler.looper.queue
                    for (i in 0 until 5) {
                        // 添加 5 个 IdleHandler
                        queue.addIdleHandler {
                            Timber.d("idle handler is handle: $i")
                            // 当消息队列里没有要执行的消息或者还没有到消息执行时间的时候，执行 IdleHandler
                            // 返回值表示是否保留这个 IdleHandler，即在执行完之后是否移除
                            Thread.sleep(100)
                            false
                        }
                    }
                    // IdleHandler 会影响后续消息执行
                    myHandler.sendEmptyMessageDelayed(2, 100)
                }
            }
        })

    }

    override fun onPause() {
        Timber.d("HandlerTest.onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.d("HandlerTest.onStop")
        super.onStop()
    }

    private fun removeSyncBarrier() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (tokens.size <= 0) {
                Timber.d("没有待移除同步屏障")
                return
            }
            val queue = myHandler.looper.queue
            val token = tokens.poll()
            Timber.d("removeSyncBarrier token: $token")
            ReflectUtils.reflect(queue).method("removeSyncBarrier", token)
        }
    }

    private fun sendAsyncMsg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val message = Message.obtain(myHandler)
            message.isAsynchronous = true
            message.what = 2
            myHandler.sendMessageDelayed(message, 1000)
        }
    }

    companion object {
        const val MSG_TOKEN = 1
    }
}