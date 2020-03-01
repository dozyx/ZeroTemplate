package cn.dozyx.template

import android.os.*
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
    private val handler = Handler(Handler.Callback {
        // 利用 Callback 可以 hook Handler 的消息
        Timber.d("HandlerTest.handleMessage $it")
        false
    })

    override fun initActions() {
        addAction(object : Action("点击") {
            override fun run() {
                handler.sendEmptyMessage(1)
            }
        })

        addAction(object : Action("postSyncBarrier+发送异步消息") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val queue = handler.looper.queue
                    sendAsyncMsg()
                    handler.sendEmptyMessageDelayed(3, 200)
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
                    handler.sendEmptyMessageDelayed(1, 100)
                    val queue = handler.looper.queue
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
                    handler.sendEmptyMessageDelayed(2, 100)
                }
            }
        })
    }

    private fun removeSyncBarrier() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (tokens.size <= 0) {
                Timber.d("没有待移除同步屏障")
                return
            }
            val queue = handler.looper.queue
            val token = tokens.poll()
            Timber.d("removeSyncBarrier token: $token")
            ReflectUtils.reflect(queue).method("removeSyncBarrier", token)
        }
    }

    private fun sendAsyncMsg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val message = Message.obtain(handler)
            message.isAsynchronous = true
            message.what = 2
            handler.sendMessageDelayed(message, 1000)
        }
    }
}