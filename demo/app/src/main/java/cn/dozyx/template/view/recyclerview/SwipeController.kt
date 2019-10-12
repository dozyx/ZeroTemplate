package cn.dozyx.template.view.recyclerview

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * Create by dozyx on 2019/5/30
 **/
class SwipeController(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // 当 item 将要移动到 target 位置时回调，返回值为 true 时，ItemTouchHelper 将认为 viewHolder 已经被移到到 target 的位置
        // 疑问：好像返回 true，但实际没有修改时，没什么变化
        Timber.d("SwipeController.onMove ${viewHolder.adapterPosition} ${target.adapterPosition}")
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // item 被“划走”的时候回调
        Timber.d("SwipeController.onSwiped direction: $direction")
    }

    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        Timber.d("SwipeController.getSwipeDirs")
        return if (viewHolder.adapterPosition % 2 == 0) {
            ItemTouchHelper.LEFT
        } else {
            ItemTouchHelper.RIGHT
        }
//        return super.getSwipeDirs(recyclerView, viewHolder)
    }
}