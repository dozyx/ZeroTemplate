package cn.dozyx.template.system

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Handler
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.jakewharton.processphoenix.ProcessPhoenix

class SystemTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("restart") {
            override fun run() {
                restartApp()
            }
        })

        addAction(object : Action("System.exit") {
            override fun run() {
                System.exit(0)// 这个方法并不会导致整个 app 退出，而是当前 resumed Activity 销毁，栈里的其他 Activity 被重建。。。
            }
        })

        addAction(object : Action("restart by clear task") {
            override fun run() {
                val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                launchIntent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(launchIntent)
            }
        })

        addAction(object : Action("Process Phoenix") {
            override fun run() {
//                ProcessPhoenix.triggerRebirth(this@SystemTest)
                // 偶尔还是会在一加上出现 ANR，但 ANR 不像直接 System.exit 那样必现
                ProcessPhoenix.triggerRebirth(this@SystemTest, packageManager.getLaunchIntentForPackage(packageName))
            }
        })
    }

    /**
     * 主要是在一加上使用这个方法出现了 ANR，所以做了下测试。结果是只要调用了 System.exit 都会 ANR
     */
    private fun restartApp() {
        // 在一加手机上使用这种方式会出现，restart 过一会 ANR 的问题。。。
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
//        launchIntent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        launchIntent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mPendingIntent = PendingIntent.getActivity(
                this@SystemTest, 123456, launchIntent, PendingIntent.FLAG_ONE_SHOT)
        val mgr = getSystemService(ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mgr.setExact(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        } else {
            mgr[AlarmManager.RTC, System.currentTimeMillis() + 100] = mPendingIntent
        }
        /*val intent = Intent(this, MainActivity::class.java) // 可以解决 ANR 问题
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)*/
//        finishAffinity() // 不会有 Activity 重启
//        finish()
        System.exit(0)
//        exitProcess(0)
//        startActivity(launchIntent)
//            android.os.Process.killProcess(android.os.Process.myPid())// 没办法在杀掉之后通过 AlarmManager 启动新的 Activity，直接 startActivity 的话可以，但还是会出现 ANR
    }

    private fun restartApp2() {
        Handler().postDelayed({
            val launchIntent: Intent? = packageManager.getLaunchIntentForPackage(packageName)
            finishAffinity()
            startActivity(launchIntent)
        }, 1500)
    }
}