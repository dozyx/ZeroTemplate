package cn.dozyx.core.debug

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import cn.dozyx.core.ex.flagToHex

@SuppressLint("LogNotTimber")
class ActivityLifecycleLoggerCallbacks : ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName}.onActivityPaused ${Integer.toHexString(activity.hashCode())}")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName}.onActivityResumed ${Integer.toHexString(activity.hashCode())}")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName}.onActivityStarted ${Integer.toHexString(activity.hashCode())}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName}.onActivityDestroyed ${Integer.toHexString(activity.hashCode())}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d(TAG, "${activity.javaClass.simpleName}.onActivitySaveInstanceState ${Integer.toHexString(activity.hashCode())}")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName}.onActivityStopped ${Integer.toHexString(activity.hashCode())}")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(TAG, "${activity.javaClass.simpleName}.onActivityCreated ${Integer.toHexString(activity.hashCode())} ${activity.intent?.flagToHex()}")
    }
    

    companion object {
        private const val TAG = "ActivityLifecycle";
    }
}