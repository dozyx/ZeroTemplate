package cn.dozyx.template.menu

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

/**
 * https://developer.android.com/guide/topics/ui/menus.html#CAB
 * 1. 实现 [ActionMode.Callback] 接口
 * 2. [startSupportActionMode] 显示 bar（比如在用户长按时）
 */
class ActionModeTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("show") {
            override fun run() {
                val actionMode = startSupportActionMode(callback)
                // 自定义布局，会显示在左侧。
                actionMode?.customView = LayoutInflater.from(this@ActionModeTest).inflate(R.layout.custom_action_mode_view, null)
            }
        })

        addAction(object : Action("hide") {
            override fun run() {

            }
        })
    }

    private val callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
            // 返回值表示是否创建
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
        }
    }
}