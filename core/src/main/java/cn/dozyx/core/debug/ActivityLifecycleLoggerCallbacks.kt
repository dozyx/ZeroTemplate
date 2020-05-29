package cn.dozyx.core.debug

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log

@SuppressLint("LogNotTimber")
class ActivityLifecycleLoggerCallbacks : ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity?) {
        Log.d(TAG, "${activity?.javaClass?.simpleName}.onActivityPaused $activity")
    }

    override fun onActivityResumed(activity: Activity?) {
        Log.d(TAG, "${activity?.javaClass?.simpleName}.onActivityResumed $activity")
    }

    override fun onActivityStarted(activity: Activity?) {
        Log.d(TAG, "${activity?.javaClass?.simpleName}.onActivityStarted $activity")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Log.d(TAG, "${activity?.javaClass?.simpleName}.onActivityDestroyed $activity")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Log.d(TAG, "${activity?.javaClass?.simpleName}.onActivitySaveInstanceState $activity")
    }

    override fun onActivityStopped(activity: Activity?) {
        Log.d(TAG, "${activity?.javaClass?.simpleName}.onActivityStopped $activity")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Log.d(TAG, "${activity?.javaClass?.simpleName}.onActivityCreated $activity")
    }

    companion object {
        private const val TAG = "ActivityLifecycle";
    }
}