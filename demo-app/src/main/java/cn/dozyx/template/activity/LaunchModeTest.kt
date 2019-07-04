package cn.dozyx.template.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.dozyx.template.base.Action

import cn.dozyx.template.base.BaseShowResultActivity
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * 通过 adb shell dumpsys activity activities 命令查看 Activity 栈信息
 */
open class LaunchModeTest : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("LaunchModeTest.onCreate ${this.javaClass.name} $taskId")
        addAction(object : Action("standard") {
            override fun run() {
                callActivity(StandardLaunchModeTest::class.java)
            }
        })
        addAction(object : Action("singleTop") {
            override fun run() {
                callActivity(SingleTopLaunchModeTest::class.java)
            }
        })
        addAction(object : Action("singleTask") {
            override fun run() {
                callActivity(SingleTaskLaunchModeTest::class.java)
            }
        })
        addAction(object : Action("singleInstance") {
            override fun run() {
                callActivity(SingleInstanceLaunchModeTest::class.java)
            }
        })
        addAction(object : Action("singleTask with taskAffinity") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, StandardLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        addAction(object : Action("singleTask start standard") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, StandardLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        addAction(object : Action("singleTask start singleTop") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTopLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        addAction(object : Action("singleTask start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("LaunchModeTest.onNewIntent $taskId")
    }


    private fun callActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }

    override fun onDestroy() {
        Timber.d("LaunchModeTest.onDestroy ${this.javaClass.name} $taskId")
        super.onDestroy()
    }

    class StandardLaunchModeTest : LaunchModeTest()

    class SingleTopLaunchModeTest : LaunchModeTest()

    class SingleTaskLaunchModeTest : LaunchModeTest()

    class SingleInstanceLaunchModeTest : LaunchModeTest()

    class SingleTaskLaunchModeTestWithTaskAffinity : LaunchModeTest()


}
