package cn.dozyx.template.interaction

import cn.dozyx.template.base.BaseShowResultActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusTest : SuperEventBusTest() {
    override fun getButtonText(): Array<String> {
        return arrayOf("register", "unregister", "post", "post from thread")
    }

    @Subscribe
     override fun onMessageEvent(event: MessageEvent) {
        val info = "default:" + event.message + "\n thread == " + Thread.currentThread()
        runOnUiThread { appendResult(info) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEventOnMainThread(event: MessageEvent) {
        val info = "main thread mode:" + event.message + "\n thread == " + Thread.currentThread()
        runOnUiThread { appendResult(info) }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessageEventOnBackgroundThread(event: MessageEvent) {
        val info = "background thread mode:" + event.message + "\n thread == " + Thread.currentThread()
        runOnUiThread { appendResult(info) }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onMessageEventOnAsyncThread(event: MessageEvent) {
        val info = "async thread mode:" + event.message + "\n thread == " + Thread.currentThread()
        runOnUiThread { appendResult(info) }
    }

    override fun onButton1() {
        EventBus.getDefault().register(this)
    }

    override fun onButton2() {
        EventBus.getDefault().unregister(this)
    }

    override fun onButton3() {
        EventBus.getDefault().post(MessageEvent("hello"))
    }

    override fun onButton4() {
        Thread(Runnable { EventBus.getDefault().post(MessageEvent("hello from thread")) }).start()
    }

    class MessageEvent(val message: String)

}
abstract class SuperEventBusTest : BaseShowResultActivity(){

    @Subscribe
    open fun onMessageEvent2(event: EventBusTest.MessageEvent) {
        val info = "super onMessageEvent2:" + event.message + "\n thread == " + Thread.currentThread()
        runOnUiThread { appendResult(info) }
    }
    @Subscribe
    open fun onMessageEvent(event: EventBusTest.MessageEvent) {
        val info = "super default:" + event.message + "\n thread == " + Thread.currentThread()
        runOnUiThread { appendResult(info) }
    }


}