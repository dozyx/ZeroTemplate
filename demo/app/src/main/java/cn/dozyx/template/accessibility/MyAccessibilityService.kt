package cn.dozyx.template.accessibility

import android.accessibilityservice.AccessibilityGestureEvent
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.text.TextUtils
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import cn.dozyx.core.utli.TimeTracker
import timber.log.Timber

class MyAccessibilityService : AccessibilityService() {
    private val receiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            Timber.d("MyAccessibilityService.onReceive ${intent.action}")
            Thread.sleep(10 * 1000)
        }
    }
    override fun onCreate() {
        super.onCreate()
        Timber.d("MyAccessibilityService.onCreate")
        serviceInfo
        registerReceiver(receiver, IntentFilter("cn.dozyx.template.accessibility"))
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("MyAccessibilityService.onStartCommand ${intent.toUri(flags)}")
        Thread.sleep(5000)
        if (intent.getBooleanExtra("enable", true)) {
            serviceInfo = AccessibilityServiceInfo().apply {
                eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
                this.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            }
        } else {
            // setServiceInfo 是会持久化的，重启无障碍服务，使用的是上一次设置的 service info
            serviceInfo = AccessibilityServiceInfo()// null 的话不会有效果
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Timber.d("MyAccessibilityService.onServiceConnected")
        serviceInfo = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            this.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
            AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                    AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE
        }
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        Timber.d("MyAccessibilityService.onKeyEvent $event")
        return super.onKeyEvent(event)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event ?: return
        val startTime = System.currentTimeMillis()
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
            val nodeInfo = event.source ?: return
            if (!TextUtils.equals(nodeInfo.packageName, "com.miui.securitycenter")) {
//                return
            }
//            var list = nodeInfo.findAccessibilityNodeInfosByViewId("com.android.settings:id/left_button")
            val forceStopTracker = TimeTracker.newTracker()
//            var list = nodeInfo.findAccessibilityNodeInfosByText("结束运行")
            var list = nodeInfo.findAccessibilityNodeInfosByText("强制停止")
            forceStopTracker.end("force stop")
            //We can find button using button name or button id
            for (node in list) {
                if (node.isEnabled) {
                    val actionTracker = TimeTracker.newTracker()
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    actionTracker.end("action")
                } else {
                    node.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
            val idTracker = TimeTracker.newTracker()
            list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/button1")
            idTracker.end("find id")
            // 模拟弹窗确认按钮的点击，是在停止运行模拟点击之后的 onAccessibilityEvent 中出发
            for (node in list) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
//            performGlobalAction(GLOBAL_ACTION_BACK)
//            logEvent(event)
        }

        Timber.d("MyAccessibilityService.onAccessibilityEvent cost time: ${System.currentTimeMillis() - startTime}")
        logEvent(event)
//        Thread.sleep(10 * 1000)
    }

    private fun logEvent(event: AccessibilityEvent) {
        Timber.d(
            "MyAccessibilityService.onAccessibilityEvent ${event.packageName} ${
                AccessibilityEvent.eventTypeToString(
                    event.eventType
                )
            } ${event.className} ${event.source} windowId: ${event.source?.windowId}"
        )
    }


    override fun onInterrupt() {
        Timber.d("MyAccessibilityService.onInterrupt")
    }

    override fun onGesture(gestureEvent: AccessibilityGestureEvent): Boolean {
        Timber.d("MyAccessibilityService.onGesture $gestureEvent")
        return super.onGesture(gestureEvent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        Timber.d("MyAccessibilityService.onDestroy")
    }
}