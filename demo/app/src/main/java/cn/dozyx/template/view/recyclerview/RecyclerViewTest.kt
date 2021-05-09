package cn.dozyx.template.view.recyclerview

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
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

class RecyclerViewTest : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_recycler_view_test

    override fun onContextItemSelected(item: MenuItem): Boolean {
        ToastUtils.showShort(item.title)
        return false
    }

    private lateinit var adapter: QuickAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rv_common.layoutManager = getLayoutManager(LAYOUT_LINEAR)
        testListener()
        initItemDecoration()
//        Handler().postDelayed({
        adapter = ContentAdapter()
        testDataObserver()
        adapter.setData(newDatas())
        rv_common.adapter = adapter
//        }, 1000)
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

    private fun testListener() {
        rv_common.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // smoothScrollToPosition 如果没有发生实际的滑动的话，不会触发回调
                Timber.d("RecyclerViewTest.onScrollStateChanged: ${scrollStateToString(newState)}")
            }
        })
    }

    private fun scrollStateToString(state: Int): String {
        return when (state) {
            SCROLL_STATE_IDLE -> "SCROLL_STATE_IDLE"
            SCROLL_STATE_SETTLING -> "SCROLL_STATE_SETTLING"
            SCROLL_STATE_DRAGGING -> "SCROLL_STATE_DRAGGING"
            else -> "Unknown"
        }
    }

    /**
     * 调用 [RecyclerView.Adapter.notifyDataSetChanged] 触发的是 onChanged
     * 调用 [RecyclerView.Adapter.notifyItemRangeRemoved] 触发的是 onItemRangeRemoved
     * 调用 [RecyclerView.Adapter.notifyItemChanged] (实际内部调用的是 notifyItemRangeChanged(position, 1))触发的也是 onItemRangeRemoved
     *
     * 也就是说回调哪个方法其实跟我们 notifyXXX 调的是哪个有关
     */
    private fun testDataObserver() {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                Timber.d("RecyclerViewTest.onChanged ${(rv_common.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()} ${(rv_common.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()}")
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                Timber.d("RecyclerViewTest.onItemRangeChanged")
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                // super 会调用两个参数的同名方法
                Timber.d("RecyclerViewTest.onItemRangeChanged")
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                Timber.d("RecyclerViewTest.onItemRangeInserted")
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                Timber.d("RecyclerViewTest.onItemRangeRemoved")
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                Timber.d("RecyclerViewTest.onItemRangeMoved")
            }
        })
    }

    private fun initItemTouch() {
//            ItemTouchHelper(SwipeController(context!!)).attachToRecyclerView(rv_common)
    }

    private fun initEvent() {
        btn_change_data.setOnClickListener {
            adapter.setData(newDatas())
//                (rv_common.adapter as RecyclerView.Adapter<*>).notifyDataSetChanged()
        }
        btn_notify.setOnClickListener {
            // notify 可能触发 onCreateViewHolder，并且旧的 item view 会 detach
//            adapter.notifyDataSetChanged()
                            adapter.notifyItemChanged(2)
//            adapter.notifyItemChanged(2, "1111")
//            adapter.notifyItemChanged(2, "2222")
//            adapter.notifyItemChanged(3, "2222")
        }
        btn_add.setOnClickListener {
            adapter.addData("新增数据")
        }

        btn_remove_range.setOnClickListener {
            adapter.remove(0)
        }
        btn_scroll.setOnClickListener {
            // smoothScrollToPosition 滑动行为由 LayoutManger 负责，默认行为是如果 position 的 item 完全可见，则不滑动，否则滑动进入可见区域，并不是滑动到第一个位置
            rv_common.smoothScrollToPosition(16)
            /*
                rv_common.smoothScrollToPosition(
                    (rv_common.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                        ?.let { it + 20 } ?: 0
                )*/
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
            return R.layout.item_text
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            super.onBindViewHolder(holder, position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            Timber.d("ContentAdapter.onCreateViewHolder")
            val viewHolder = super.onCreateViewHolder(parent, viewType)
            viewHolder.itemView.apply {
                layoutParams.width = ScreenUtils.getScreenWidth() - 100
            }
            viewHolder.getView<View>(android.R.id.text1)
                .addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//                Timber.d("ContentAdapter.onCreateViewHolder onLayoutChange: $left $top $right $bottom $oldLeft $oldTop $oldRight $oldBottom")
                }
            val adapterPosition = viewHolder.layoutPosition
            viewHolder.itemView.addOnAttachStateChangeListener(object :
                View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View?) {
                    Timber.d("ContentAdapter.onViewAttachedToWindow $adapterPosition")
                }

                override fun onViewDetachedFromWindow(v: View?) {
                    Timber.d("ContentAdapter.onViewDetachedFromWindow $adapterPosition")
                }
            })
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
        for (i in 0..3) {
            newDatas.add(Shakespeare.TITLES[Random().nextInt(Shakespeare.TITLES.size)])
        }
        return newDatas
    }

}



