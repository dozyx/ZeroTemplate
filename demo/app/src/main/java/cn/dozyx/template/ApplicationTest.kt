package cn.dozyx.template

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ApplicationTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("register") {
            override fun run() {
                // 只能收到注册之后的数据
                application.registerActivityLifecycleCallbacks(object :
                    Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(
                        activity: Activity,
                        savedInstanceState: Bundle?
                    ) {
                        Timber.d("ApplicationTest.onActivityCreated")
                    }

                    override fun onActivityStarted(activity: Activity) {
                        Timber.d("ApplicationTest.onActivityStarted")
                    }

                    override fun onActivityResumed(activity: Activity) {

                    }

                    override fun onActivityPaused(activity: Activity) {

                    }

                    override fun onActivityStopped(activity: Activity) {

                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                    }

                    override fun onActivityDestroyed(activity: Activity) {

                    }
                })
            }
        })
    }
}