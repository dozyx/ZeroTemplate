package cn.dozyx.template.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class LifecycleCallbacksTest : BaseTestActivity() {

    override fun onResume() {
        super.onResume()
        Timber.d("LifecycleCallbacksTest.onResume")
    }

    override fun initActions() {
        addAction(object : Action("register") {
            override fun run() {
                application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        Timber.d("LifecycleCallbacksTest.onActivityCreated")
                    }

                    override fun onActivityStarted(activity: Activity) {
                        Timber.d("LifecycleCallbacksTest.onActivityStarted")
                    }

                    override fun onActivityResumed(activity: Activity) {
                        Timber.d("LifecycleCallbacksTest.onActivityResumed")
                    }

                    override fun onActivityPaused(activity: Activity) {
                        Timber.d("LifecycleCallbacksTest.onActivityPaused")
                    }

                    override fun onActivityStopped(activity: Activity) {
                        Timber.d("LifecycleCallbacksTest.onActivityStopped")
                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                        Timber.d("LifecycleCallbacksTest.onActivitySaveInstanceState")
                    }

                    override fun onActivityDestroyed(activity: Activity) {
                        Timber.d("LifecycleCallbacksTest.onActivityDestroyed")
                    }
                })
            }
        })
    }
}