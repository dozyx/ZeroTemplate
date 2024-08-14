package cn.dozyx.template.demo.fixedcolumn

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Scroller
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_fixed_column3.*
import timber.log.Timber
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.min

/**
 * @author dozyx
 * @date 2019-10-27
 */
class FixedColumnListActivity3 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemfixed_column2) {
            override fun convert(helper: BaseViewHolder, item: String) {
                Timber.d("FixedColumnListActivity3.convert ${helper.itemView} $item")
                helper.setText(R.id.columns_0, item)
                if (recyclerView == null){
                    return
                }
                val tag = recyclerView.tag
                if (tag != null) {
                    helper.getView<ViewGroup>(R.id.scroll_columns).scrollTo(tag as Int, 0)
                }
            }

            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)

            }
        }
        rv_common.adapter = adapter
        val datas = ArrayList<String>()
        for (i in 0..20) {
            datas.add(i.toString())
        }
        adapter.setNewData(datas)


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fixed_column3
    }
}

class FixedColumnRecyclerView : RecyclerView {
    private lateinit var gestureDetector: GestureDetector
    private var lastDownX = 0f
    private var lastDownY = 0f
    private var isHorizontalScrolling = false
    private var scroller: Scroller

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        scroller = Scroller(context)
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                for (i in 0 until childCount) {
                    val scrollView = getChildAt(i).findViewById<ViewGroup>(R.id.scroll_columns)
                    var newScrollX = (scrollView.scrollX + distanceX).toInt()
                    val max = scrollView.width - (right - scrollView.left)
                    newScrollX = kotlin.math.max(0, min(max, newScrollX))
                    Timber.d("FixedColumnRecyclerView.onScroll $newScrollX $distanceX $max")
                    scrollView.scrollTo(newScrollX, 0)
                    tag = newScrollX
                }
                return true
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                gestureDetector.onTouchEvent(ev)
                lastDownX = ev.x
                lastDownY = ev.y
                isHorizontalScrolling = false
            }
            MotionEvent.ACTION_UP -> {
                if (isHorizontalScrolling) {
                    return gestureDetector.onTouchEvent(ev)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val distanceX = ev.x - lastDownX
                val distanceY = ev.y - lastDownY
                if (isHorizontalScrolling || abs(distanceX) > abs(distanceY)) {
                    isHorizontalScrolling = true
                    return gestureDetector.onTouchEvent(ev)
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                gestureDetector.onTouchEvent(ev)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}
