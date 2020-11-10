package cn.dozyx.template.view.recyclerview

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.constant.Shakespeare
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.utli.util.ColorUtil
import cn.dozyx.template.R
import cn.dozyx.template.view.recyclerview.adapter.QuickAdapter
import cn.dozyx.template.view.recyclerview.layoutmanager.FixedRowGridLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_recycler_view_test.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewTestActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_recycler_view_test

    override fun onContextItemSelected(item: MenuItem): Boolean {
        ToastUtils.showShort(item.title)
        return false
    }

    private lateinit var adapter :QuickAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rv_common.layoutManager = getLayoutManager(LAYOUT_LINEAR)
        initItemDecoration()
        Handler().postDelayed({
            adapter = ContentAdapter()
            adapter.setData(newDatas())
            rv_common.adapter = adapter
        }, 1000)
        initItemTouch()
        initEvent()
        rv_common.viewTreeObserver.addOnGlobalLayoutListener {
            // 没有设置 adapter 也会触发
            // 首次进来触发了两次
            // 滑动是不会触发的，应该是因为没有重新 layout
            // 触发相关源码 ViewRootImpl#performTraversals()
            Timber.d("RecyclerViewTestActivity.onCreate onGlobalLayout ${rv_common.childCount}")
        }
    }

    private fun initItemTouch() {
//            ItemTouchHelper(SwipeController(context!!)).attachToRecyclerView(rv_common)
    }

    private fun initEvent() {
        btn_change_data.setOnClickListener {
//                adapter.setData(getData())
//                (rv_common.adapter as RecyclerView.Adapter<*>).notifyDataSetChanged()
        }
        btn_notify.setOnClickListener {
            //                adapter.notifyItemChanged(2)
            adapter.notifyItemChanged(2, "1111")
            adapter.notifyItemChanged(2, "2222")
            adapter.notifyItemChanged(3, "2222")
        }
    }

    private fun initItemDecoration() {
        /*rv_common.addItemDecoration(
            CustomItemDecoration(
                context!!,
                CustomItemDecoration.VERTICAL
            )
        )*/
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.context_menu, menu)
    }


    companion object {
        private const val TAG = "RecyclerViewTestActivit"
        private const val LAYOUT_LINEAR = 0
        private const val LAYOUT_FIXED_ROW_GRID = 1
    }

    inner class NestedListAdapter : QuickAdapter<String>() {

        override fun getLayoutId(viewType: Int): Int {
            return R.layout.partial_recycler_view
        }

        override fun convert(holder: VH, data: String, position: Int) {
            val recyclerView = holder.getView<RecyclerView>(R.id.recycler_view)
            recyclerView.layoutManager = getLayoutManager(LAYOUT_FIXED_ROW_GRID)
            var contentAdapter = ContentAdapter()
            contentAdapter.setData(newDatas())
            recyclerView.adapter = contentAdapter
        }

        override fun getItemCount(): Int {
            return 3
        }

    }

    class ContentAdapter : QuickAdapter<String>() {
        override fun getLayoutId(viewType: Int): Int {
            return android.R.layout.simple_list_item_1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val viewHolder = super.onCreateViewHolder(parent, viewType)
            viewHolder.itemView.apply {
                layoutParams.width = ScreenUtils.getScreenWidth() - 100
            }
            return viewHolder
        }

        override fun convert(holder: VH, data: String, position: Int) {
            holder.itemView.setBackgroundColor(ColorUtil.random())
            holder.setText(android.R.id.text1, data)
//            holder.itemView.setOnCreateContextMenuListener()
//            Thread.sleep(2000)
        }
    }

    private fun getLayoutManager(type: Int) = when (type) {
        LAYOUT_FIXED_ROW_GRID -> FixedRowGridLayoutManager(this)
        else -> LinearLayoutManager(this)
    }

    private fun newDatas(): ArrayList<String> {
        val newDatas = ArrayList<String>(5)
        for (i in 0..100) {
            newDatas.add(Shakespeare.TITLES[Random().nextInt(Shakespeare.TITLES.size)])
        }
        return newDatas
    }

}



