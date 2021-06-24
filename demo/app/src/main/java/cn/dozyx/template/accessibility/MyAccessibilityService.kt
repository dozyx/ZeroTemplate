package cn.dozyx.template.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import timber.log.Timber

class MyAccessibilityService : AccessibilityService() {
    override fun onCreate() {
        super.onCreate()
        Timber.d("MyAccessibilityService.onCreate")
        serviceInfo
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("MyAccessibilityService.onStartCommand ${intent?.toUri(flags)}")
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
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event ?: return
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
            val nodeInfo = event.source ?: return
            if (!TextUtils.equals(nodeInfo.packageName, "com.miui.securitycenter")) {
//                return
            }
//            var list = nodeInfo.findAccessibilityNodeInfosByViewId("com.android.settings:id/left_button")
            var list = nodeInfo.findAccessibilityNodeInfosByText("结束运行")
            //We can find button using button name or button id
            for (node in list) {
                if (node.isClickable) {
//                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                } else {
//                    node.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
            list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/button1")
            // 模拟弹窗确认按钮的点击，是在停止运行模拟点击之后的 onAccessibilityEvent 中出发
            for (node in list) {
//                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
//            performGlobalAction(GLOBAL_ACTION_BACK)
//            logEvent(event)
        }

        logEvent(event)
    }

    private fun logEvent(event: AccessibilityEvent) {
        Timber.d(
            "MyAccessibilityService.onAccessibilityEvent ${event.packageName} ${
                AccessibilityEvent.eventTypeToString(
                    event.eventType
                )
            } ${event.className} ${event.source}"
        )
    }


    override fun onInterrupt() {
        Timber.d("MyAccessibilityService.onInterrupt")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("MyAccessibilityService.onDestroy")
    }
}