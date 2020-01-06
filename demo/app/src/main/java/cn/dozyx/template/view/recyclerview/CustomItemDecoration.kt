package cn.dozyx.template.view.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * @author dozeboy
 * @date 2018/12/4
 */
class CustomItemDecoration(val context: Context, val orientation: Int) : RecyclerView.ItemDecoration() {

    val attrs = intArrayOf(android.R.attr.listDivider)

    private var divider: Drawable

    init {
        val typedArray = context.obtainStyledAttributes(attrs)
        divider = typedArray.getDrawable(0)!!
        typedArray.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        Timber.d("CustomItemDecoration.onDraw")
        if (orientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        // onDraw 在绘制 Item 之前调用；onDrawOver 在绘制 Item 之后调用
//        Timber.d("CustomItemDecoration.onDrawOver")
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingTop
        for (index in 0..parent.childCount) {
            val child = parent.getChildAt(index)
            val left = child.right + (child.layoutParams as RecyclerView.LayoutParams).rightMargin
            divider.setBounds(left, top, left + divider.intrinsicWidth, bottom)
            divider.draw(c)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        // 这里的实现没有考虑 mClipToPadding（子 view 是否与 padding 对齐，即是否绘制到 padding 区域），导致 divider 被绘制到了 padding 区域。如何处理可参考官方的 DividerItemDecoration
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            val top = child.bottom + (child.layoutParams as RecyclerView.LayoutParams).bottomMargin
            // 官方 DividerItemDecoration 的计算 top 和 bottom 的方法有点不一样：它是先利用 getItemOffsets 来得到整个 view 包括 decoration 的大小，这样就能直接得到 bottom
            divider.setBounds(left, top, right, top + divider.intrinsicHeight)
            divider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // outRect 表示 item 的内边距
        // 用来为 decoration 预留绘制空间
        when (orientation) {
            VERTICAL -> outRect.set(0+30, 0, 0, divider.intrinsicHeight)
            HORIZONTAL -> outRect.set(0, 0, divider.intrinsicWidth, 0)
        }
    }

    companion object {
        const val VERTICAL = LinearLayoutManager.VERTICAL
        const val HORIZONTAL = LinearLayoutManager.HORIZONTAL
    }

}