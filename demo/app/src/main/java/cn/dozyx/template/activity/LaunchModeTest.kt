package cn.dozyx.template.activity

import android.content.ComponentName
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import cn.dozyx.core.ex.flagToHex
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * 过滤 onActivityCreated|onActivityDestroyed|onNewIntent
 *
 * 通过 adb shell dumpsys activity activities 命令查看 Activity 栈信息
 */
open class LaunchModeTest : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appendResult("onCreate ${javaClass.simpleName} flag = ${intent.flagToHex()}")

        installLauncherTest()
        installStandardTest()
        installSingleTopTest()
        installSingleTaskTest()


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
                intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        addAction(object : Action("singleTask start standard") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, StandardLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
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

        addAction(object : Action("(flag new task) start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                // 还是会销毁上面的 Activity
            }
        })
        addAction(object : Action("(flag new task | clear top) start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        appendResult("onNewIntent ${javaClass.simpleName} flag = ${intent?.flagToHex()}")
        Timber.d("onNewIntent ${javaClass.simpleName} $taskId ${intent?.flagToHex()}")
    }

    private fun callActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }

    override fun onDestroy() {
        Timber.d("LaunchModeTest.onDestroy ${this.javaClass.name} $taskId")
        super.onDestroy()
    }

    private fun installLauncherTest(){

        addAction(object : Action("start self") {
            override fun run() {
                callActivity(LaunchModeTest::class.java)
            }
        })

        addAction(object : Action("(new_task|clear_top) self") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, LaunchModeTest::class.java)
                intent.flags =
                    FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
            }
        })

        addAction(object : Action("restart by launch intent") {
            override fun run() {
                finish()
                val intent = packageManager.getLaunchIntentForPackage(packageName)
//                intent?.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
//                intent?.flags =
//                    FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent?.flags = 0
//                intent?.addFlags(FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
//                intent?.flags =  FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        addAction(object : Action("restart by class") {
            override fun run() {
                finish()
                val intent = Intent(this@LaunchModeTest, LaunchModeTest::class.java)
//                val intent: Intent = packageManager.getLaunchIntentForPackage(packageName)!!
//                intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                intent.apply {
                    flags =  FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                    action = ACTION_MAIN
                    addCategory(CATEGORY_LAUNCHER)
                }
                startActivity(intent)
            }
        })

        addAction(object : Action("restart by clear task") {
            override fun run() {
                finish()
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                intent?.apply {
                    flags =  FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
//                    action = ACTION_MAIN
//                    addCategory(CATEGORY_LAUNCHER)
                }
                startActivity(intent)
//                startActivity(Intent.makeMainActivity(ComponentName(this@LaunchModeTest, LaunchModeTest::class.java)))
                // 如果同时使用 finish()，那么「冷启动，执行finish 和 clear task，启动一个其他 activity，home，返回 app」，这时会新建一个 activity。如果使用 CATEGORY_LAUNCHER 会正常。
                // 不加 FLAG_ACTIVITY_NEW_TASK 的话，不会重建 activity，只会回调 onNewIntent()
            }
        })

        addAction(object : Action("makeRestartActivityTask") {
            override fun run() {
                finish()
                val intent = Intent.makeRestartActivityTask(
                    ComponentName(
                        this@LaunchModeTest,
                        LaunchModeTest::class.java
                    )
                )
                startActivity(intent)
            }
        })
    }

    private fun installStandardTest() {

        addAction(object : Action("standard") {
            override fun run() {
                callActivity(StandardLaunchModeTest::class.java)
            }
        })

        addAction(object : Action("(new task) start standard") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, StandardLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                // 与直接启动一个 standard 无区别
            }
        })

        addAction(object : Action("(new_task|clear_top) start standard") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, StandardLaunchModeTest::class.java)
//                intent.flags = FLAG_ACTIVITY_CLEAR_TOP
                intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                // 销毁其自身及其上面的 activity，并创建一个新的。不加 FLAG_ACTIVITY_NEW_TASK 也一样。
            }
        })

        addAction(object : Action("(single_top | clear_top) start standard") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, StandardLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                // 销毁它上面的 activity，自身不会被销毁，而是回调 onNewIntent
            }
        })
    }

    private fun installSingleTopTest() {
        addAction(object : Action("singleTop") {
            override fun run() {
                callActivity(SingleTopLaunchModeTest::class.java)
            }
        })

        addAction(object : Action("(new_task|clear_top) singleTop") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTopLaunchModeTest::class.java)
//                intent.flags = FLAG_ACTIVITY_CLEAR_TOP
//                intent.flags = FLAG_ACTIVITY_NEW_TASK
                intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                // SingleTopLaunchModeTest 上面有 Activity，会新建一个
                // 没有，SingleTopLaunchModeTest 不会重建，但会重新执行 pause 和 resume。。。
                // 不加 FLAG_ACTIVITY_NEW_TASK 行为没差别，不加 FLAG_ACTIVITY_CLEAR_TOP 的话如果不在 top 会新建
            }
        })
        addAction(object : Action("(new_task|clear_task) singleTop") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTopLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })
        addAction(object : Action("finish restart singleTop") {
            override fun run() {
                finish()
                val intent = Intent(this@LaunchModeTest, SingleTopLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

    }

    private fun installSingleTaskTest() {

        addAction(object : Action("singleTask") {
            override fun run() {
                callActivity(SingleTaskLaunchModeTest::class.java)
                // singleTask 在栈顶，再次启动不会重建，但是会触发 onpause 和 onresume
            }
        })

        addAction(object : Action("(new_task | clear_top) start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        addAction(object : Action("(front) start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_BROUGHT_TO_FRONT
                startActivity(intent)
                // 没感觉有区别
            }
        })

        addAction(object : Action("(single_top) start singleTask") {
            override fun run() {
                val intent = Intent(this@LaunchModeTest, SingleTaskLaunchModeTest::class.java)
                intent.flags = FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                // 没感觉有区别
            }
        })
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
