package cn.dozyx.template.activity

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * 通过 adb shell dumpsys activity activities 命令查看 Activity 栈信息
 */
open class LaunchModeTest : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appendResult("onCreate ${javaClass.simpleName} flag = ${intent?.flags?.let { Integer.toHexString(it) }}")
        Timber.d("LaunchModeTest.onCreate ${this.javaClass.name} $taskId")

        addAction(object : Action("start self") {
            override fun run() {
                callActivity(LaunchModeTest::class.java)
            }
        })

        addAction(object : Action("(flag new task or clear top) start self") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, LaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
            }
        })

        addAction(object : Action("restart by launch intent") {
            override fun run() {
                finish()
                val intent = packageManager.getLaunchIntentForPackage(packageName)
//                intent?.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                intent?.flags = FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION
//                intent?.flags = 0
                startActivity(intent)
            }
        })

        addAction(object : Action("restart by class") {
            override fun run() {
                finish()
                val intent = Intent(this@LaunchModeTest, LaunchModeTest::class.java)
//                val intent = packageManager.getLaunchIntentForPackage(packageName)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                startActivity(intent)
            }
        })
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
                // singleTask 在栈顶，再次启动不会重建，但是会触发 onpause 和 onresume
            }
        })
        addAction(object : Action("singleInstance") {
            override fun run() {
                callActivity(SingleInstanceLaunchModeTest::class.java)
            }
        })

        addAction(object : Action("standard finish") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, StandardLaunchModeTest::class.java)
                intent.putExtra("autoFinish", true)
                startActivity(intent)
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

        addAction(object : Action("(flag clear top) start singleTop") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTopLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                // SingleTopLaunchModeTest 上面有 Activity，会销毁其上面的 Activity
                // 没有，SingleTopLaunchModeTest 不会重建，但会重新执行 pause 和 resume。。。
                // 如果 栈中存在多个 SingleTopLaunchModeTest，那么被销毁的只是最上面的 SingleTopLaunchModeTest 上面的 Activity
            }
        })

        addAction(object : Action("(flag new task) start singleTop") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTopLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                // SingleTopLaunchModeTest 上面有 Activity，会新建一个
                // 没有，SingleTopLaunchModeTest 不会重建，但会重新执行 pause 和 resume。。。
            }
        })

        addAction(object : Action("(flag new task | clear top) start singleTop") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTopLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                // 行为同只加了 Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        })

        addAction(object : Action("(flag new task) start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                // 还是会销毁上面的 Activity
            }
        })
        addAction(object : Action("(flag new task | clear top) start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        appendResult("onNewIntent ${javaClass.simpleName} flag = ${intent?.flags?.let { Integer.toHexString(it) }}")
        Timber.d("LaunchModeTest.onNewIntent $taskId")
    }


    private fun callActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }

    override fun onDestroy() {
        Timber.d("LaunchModeTest.onDestroy ${this.javaClass.name} $taskId")
        super.onDestroy()
    }

    class StandardLaunchModeTest : LaunchModeTest() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val autoFinish = intent.getBooleanExtra("autoFinish", false)
            if (autoFinish) finish()
        }
    }

    class SingleTopLaunchModeTest : LaunchModeTest()

    class SingleTaskLaunchModeTest : LaunchModeTest()

    class SingleInstanceLaunchModeTest : LaunchModeTest()

    class SingleTaskLaunchModeTestWithTaskAffinity : LaunchModeTest()


}
