package cn.dozyx.template.activity

import android.content.Intent
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

class ThirdAppTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("start") {
            override fun run() {
                val intent = Intent("cn.dozyx.thirdapp")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                startActivity(intent)
                // 不加 FLAG_ACTIVITY_NEW_TASK：activity 将在当前的 task 中启动
                // 加 FLAG_ACTIVITY_NEW_TASK：activity 将在另一个 task 中启动，如果启动的 activity 的 taskAffinity 与启动它的 activity 的 taskAffinity 相同，那么也会是在同一个 task 中
                // 如果不加 FLAG_ACTIVITY_CLEAR_TOP，并且启动的 activity 的 launchMode 为 standard 或者 singleTop，那么可能导致 activity 新建
            }
        })

        addAction(object : Action("mock") {
            override fun run() {
                val intent = Intent("cn.dozyx.mockthird")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        })
        addAction(object : Action("explicit vs implicit") {
            override fun run() {
                val intent = Intent(this@ThirdAppTest, MockThirdAppTest::class.java)
//                val intent = Intent()
                intent.action = "cn.dozyx.mockthird";
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                // action 可以被接收到
            }
        })

        addAction(object : Action("clear task") {
            override fun run() {
                val intent = Intent(this@ThirdAppTest, ThirdAppTest::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                // FLAG_ACTIVITY_CLEAR_TASK 只会清理它所在的 task
            }
        })

    }
}