package cn.dozyx.template.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.ActionProvider
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cn.dozyx.core.utli.SampleUtil
import cn.dozyx.template.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_toolbar.*
import timber.log.Timber

class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.ToolbarActivity)
        setContentView(R.layout.activity_toolbar)
        setSupportActionBar(toolbar)
//        setupActionBarCustomView()
//        testActionMode()
        btn_invali.setOnClickListener {
            invalidateOptionsMenu()
        }
    }

    private fun testActionMode() {
        rv_list.layoutManager = LinearLayoutManager(this)
        val quickAdapter = object :
            BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_text, SampleUtil.strings) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(android.R.id.text1, item)
            }
        }
        val actionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                Timber.d("ToolbarActivity.onCreateActionMode")
                menu?.add("action1")
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                // action mode 的 menu 有变化时回调
                Timber.d("ToolbarActivity.onPrepareActionMode")
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                Timber.d("ToolbarActivity.onActionItemClicked")
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                Timber.d("ToolbarActivity.onDestroyActionMode")
                true
            }
        }
        quickAdapter.setOnItemLongClickListener { adapter, view, position ->
            val actionMode = startSupportActionMode(actionModeCallback) // 会返回一个 ActionMode 实例
            actionMode?.customView =
                LayoutInflater.from(this).inflate(R.layout.custom_action_mode_view, null)
            actionMode?.title = "标题"
            return@setOnItemLongClickListener true
        }
        rv_list.adapter = quickAdapter
    }

    private fun setupActionBarCustomView() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            //            setHomeAsUpIndicator(android.R.drawable.arrow_up_float)

            val button = Button(this@ToolbarActivity)
            button.text = "custom"
            button.setTextColor(Color.BLUE)
            setDisplayShowCustomEnabled(true)
            customView = button
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Timber.d("ToolbarActivity.onCreateOptionsMenu")
        menuInflater.inflate(R.menu.test, menu)
        val item = menu?.findItem(R.id.action_settings)
//        item?.actionView = LayoutInflater.from(this).inflate(R.layout.menu_action_view, null)// 设置 action view 之后将没办法自动响应 onOptionsItemSelected
        val actionProviderMenuItem =
            MenuItemCompat.setActionProvider(item, CustomActionProvider(this))
        MenuItemCompat.setActionProvider(item, ShareActionProvider(this))
//        actionProviderMenuItem.setOnMenuItemClickListener {
//            Timber.d("ToolbarActivity.onCreateOptionsMenu")
//            return@setOnMenuItemClickListener true
//        }
//        actionProviderMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
//        val newMenuItem = menu?.add("new")
//        newMenuItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
//        testSubMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Timber.d("ToolbarActivity.onPrepareOptionsMenu")
        return super.onPrepareOptionsMenu(menu)
    }

    private fun testSubMenu(menu: Menu?) {
        val subMenu = menu?.addSubMenu("sub1")
        val providerMenuItem = subMenu?.add("action provider")
        MenuItemCompat.setActionProvider(providerMenuItem, CustomActionProvider(this))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.d("ToolbarActivity.onOptionsItemSelected $item")
        if (item.itemId == R.id.action_settings) {
//            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class CustomActionProvider(context: Context) : ActionProvider(context) {
    override fun onCreateActionView(): View {

        val actionView = LayoutInflater.from(context).inflate(R.layout.menu_action_view, null)
        actionView.findViewById<ImageView>(R.id.iv_search).addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {
                Timber.d("CustomActionProvider.onViewAttachedToWindow")
            }

            override fun onViewDetachedFromWindow(v: View?) {
                Timber.d("CustomActionProvider.onViewDetachedFromWindow")
            }
        })
        return actionView
    }

    override fun onPerformDefaultAction(): Boolean {
        Timber.d("CustomActionProvider.onPerformDefaultAction")
        return false
    }

    override fun onPrepareSubMenu(subMenu: SubMenu?) {
        super.onPrepareSubMenu(subMenu)
//        subMenu?.add("item1")
//        subMenu?.add("item2")
    }

    override fun hasSubMenu(): Boolean {
        return false
    }

    override fun isVisible(): Boolean {
        // 需要 overridesItemVisibility 返回 true 才会生效
        return false
    }

    override fun overridesItemVisibility(): Boolean {
        return false
    }
}

