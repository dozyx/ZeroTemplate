package cn.dozyx.template.demo.fixedcolumn

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.common_list.*
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * 使用 RecyclerView 的 OnItemTouchListener 拦截事件实现水平滚动
 * @author dozyx
 * @date 2019-11-09
 */
class FixedColumnLIstActivity4 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemfixed_column2) {
            override fun convert(helper: BaseViewHolder, item: String) {
                Timber.d("FixedColumnListActivity3.convert ${helper.itemView} $item")
                helper.setText(R.id.columns_0, item)
                if (recyclerView == null) {
                    return
                }

            }

            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)

            }
        }
        rv_common.adapter = adapter
        val datas = ArrayList<String>()
        for (i in 0..50) {
            datas.add(i.toString())
        }
        adapter.setNewData(datas)
        rv_common.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            var downX = 0f
            var downY = 0f
            var lastX = 0f
            var nestedHScrollX = 0f
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                Timber.d("FixedColumnLIstActivity4.onTouchEvent")
                if (e.action == MotionEvent.ACTION_MOVE) {
                    val maxHScroll = rv_common.getChildAt(0).findViewById<View>(R.id.scroll_columns).right - rv_common.right
                    nestedHScrollX = max(0F, min(nestedHScrollX + lastX - e.x, maxHScroll.toFloat()))
                    for (i in 0 until rv_common.childCount) {
                        val child = rv_common.getChildAt(i)
                        val scrollView = child.findViewById<View>(R.id.scroll_columns)
                        scrollView.scrollTo(nestedHScrollX.toInt(), 0)
                    }
                    lastX = e.x
                }
                // 还是存在垂直滚动列表时，缓存的一些 view 滚动位置错误的问题
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (rv_common.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    return false
                }
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downX = e.x
                        downY = e.y
                        lastX = e.x
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (abs(downX - e.x) > abs(downY - e.y)) {
                            return true
                        }
                    }


                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })

    }

    override fun getLayoutId(): Int {
        return R.layout.common_list
    }
}