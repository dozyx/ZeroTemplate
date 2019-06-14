package cn.dozyx.template.view.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import cn.dozyx.template.R

/**
 * @see <a href="https://medium.com/@kitek/recyclerview-swipe-to-delete-easier-than-you-thought-cff67ff5e5f6> </a>
 * @see <a href="https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28> </a>
 * Create by timon on 2019/5/30
 **/
abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_24)!!
    private val clipIcon = ClipDrawable(deleteIcon, Gravity.END, ClipDrawable.HORIZONTAL)
    private val intrinsicWidth = deleteIcon.intrinsicWidth
    private val intrinsicHeight = deleteIcon.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#F44336")
    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private var swipeBack: Boolean = false

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // 设置处理的动作
        if (viewHolder.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        LogUtils.d(dX, dY, itemView.right)
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive
        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        // margin 是为了使 icon 距离 top 和 right 的边距一致
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

//        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
//        deleteIcon.draw(c)
        // 直接绘制 deleteIcon 会导致 icon 绘制在 item 之上
        clipIcon.level = (-dX.toInt() - deleteIconMargin) * 10000 / deleteIcon.intrinsicWidth
        clipIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        clipIcon.draw(c)

        recyclerView.setOnTouchListener { v, event ->
            swipeBack = (event.action == MotionEvent.ACTION_CANCEL) or (event.action == MotionEvent.ACTION_UP)
            false
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}