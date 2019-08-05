package cn.dozyx.core.debug

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import timber.log.Timber

class LogActivityLifecycleCallbacks : ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
        Timber.d("${activity?.javaClass?.simpleName}.onActivityPaused")
    }

    override fun onActivityResumed(activity: Activity?) {
        Timber.d("${activity?.javaClass?.simpleName}.onActivityResumed")
    }

    override fun onActivityStarted(activity: Activity?) {
        Timber.d("${activity?.javaClass?.simpleName}.onActivityStarted")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Timber.d("${activity?.javaClass?.simpleName}.onActivityDestroyed")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Timber.d("${activity?.javaClass?.simpleName}.onActivitySaveInstanceState")
    }

    override fun onActivityStopped(activity: Activity?) {
        Timber.d("${activity?.javaClass?.simpleName}.onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Timber.d("${activity?.javaClass?.simpleName}.onActivityCreated")
    }
}