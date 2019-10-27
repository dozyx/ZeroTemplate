package cn.dozyx.template.demo.fixedcolumn

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import cn.dozyx.template.view.recyclerview.adapter.QuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_fixed_column.*
import timber.log.Timber

/**
 * 实现功能：列表中显示了多列内容，每一行一个水平滑动，滑动时，前几列保持固定
 * 思路1：使用 HorizontalScrollView 作为可滚动列的父布局，监听滚动，再同步滚动其他列。
 *        问题：如何避免同步滚动时，滚动事件被重复触发？(这个问题还好，scrollTo 是在位置变化才会发生滚动并触发回调，所以并不会循环回调)
 *        问题2：发生滚动后，滑动显示其他 item，新的 item 的位置可能为初始状态
 *              法一：在 RecyclerView 外套一层 NestedScrollView，这样所有的 view 都会加载到内存中。缺点就是失去了  RecyclerView 的缓存功能。
 * 思路2：ScrollView 包含两部分：左侧固定列部分，右侧 HorizontalScrollView 可水平滚动部分
 *        问题：两侧数据要单独添加，要注意对齐；view 会一次性全部加载
 * 思路3：垂直滚动无论用 ScrollView 还是列表都比较好实现，难点在于行的同步滚动。可以通过处理这个区域的根 ViewGroup 触摸事件来实现，
 * 首先，需要记录所有可以滑动的行的 view，接着在父布局的触摸处理中，检测触摸点是否在滚动的行上面，是的话对事件进行拦截，接着对行执行 scrollTo
 *
 * 其他问题：
 *     问题一：如何控制可滚动列的宽度，比如单屏显示两个固定列和两个可滚动列（HorizontalScrollView 不知道怎么实现）
 */
class FixedColumnListActivity : BaseActivity() {
    private lateinit var scrollListener: View.OnScrollChangeListener
    private var currentScrollX = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var scrollListener = object : View.OnScrollChangeListener {
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                Timber.d("FixedColumnListActivity.onScrollChange")
                recycler_view.layoutManager?.apply {
                    for (i in 0 until childCount) {
                        val child = getChildAt(i)
                        if (child != v) {
                            child!!.findViewById<HorizontalScrollView>(R.id.scroll_columns).scrollTo(scrollX, scrollY)
                        }
                    }
                }
                currentScrollX = scrollX
            }
        }
        var adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_fixed_column) {
            override fun convert(helper: BaseViewHolder, item: String?) {
                val scrollView = helper.getView<HorizontalScrollView>(R.id.scroll_columns)
                scrollView.setOnScrollChangeListener(scrollListener)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                val scrollView = viewHolder.getView<HorizontalScrollView>(R.id.scroll_columns)
                if (currentScrollX > 0) {
                    scrollView.scrollTo(currentScrollX, 0)
                }
                return viewHolder
            }
        }
        recycler_view.adapter = adapter
        var datas = ArrayList<String>()
        for (i in 0..20) {
            datas.add("$i")
        }
        recycler_view.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
            }
            return@setOnTouchListener false
        }
        adapter.setNewData(datas)

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fixed_column
    }
}
