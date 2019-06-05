package com.zerofate.template.view.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils

/**
 * Create by timon on 2019/5/30
 **/
class SwipeController(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private var swipeBack = false
    private var buttonState = ButtonState.GONE
    private val buttonWidth = 300f
    private var isCancelSwipe = false
    private var lastActiveState = false

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        LogUtils.d(flags, layoutDirection)
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        LogUtils.d("position: ${viewHolder.adapterPosition}, dX: $dX, dY: $dY, actionState: $actionState, isCurrentlyActive: $isCurrentlyActive")
        isCancelSwipe = lastActiveState  && !isCurrentlyActive
        lastActiveState = isCurrentlyActive
        if (isCancelSwipe) {
            super.onChildDraw(c, recyclerView, viewHolder, -300F, dY, actionState, isCurrentlyActive)
            return
        }

        if (actionState == ACTION_STATE_SWIPE) {
            var newDx = dX
            if (dX > 0) {
                newDx = 0F
            } else if (dX < -300F) {
                newDx = -300F
            }
            super.onChildDraw(c, recyclerView, viewHolder, newDx, dY, actionState, isCurrentlyActive)
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    private fun setTouchListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, currentlyActive: Boolean) {
        recyclerView.setOnTouchListener { v, event ->
            swipeBack = (event.action == MotionEvent.ACTION_CANCEL) or (event.action == MotionEvent.ACTION_UP)
            if (swipeBack) {
                if (dX < -buttonWidth) {
                    buttonState = ButtonState.RIGHT_VISIBLE
                } else if (dX > buttonWidth) {
                    buttonState = ButtonState.LEFT_VISIBLE
                }
                if (buttonState != ButtonState.GONE) {
                    setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, currentlyActive)
                    setItemClickable(recyclerView, false)
                }
            }
            false
        }
    }

    private fun setTouchDownListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, currentlyActive: Boolean) {
        recyclerView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, currentlyActive)
            }
            false
        }
    }

    private fun setTouchUpListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, currentlyActive: Boolean) {
        recyclerView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, currentlyActive)
                recyclerView.setOnTouchListener { v, event ->
                    false
                }
                setItemClickable(recyclerView, true)
                swipeBack = false
                buttonState = ButtonState.GONE
            }
            false
        }
    }

    private fun setItemClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        val childCount = recyclerView.childCount
        if (childCount < 1) return
        for (i in 0 until childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
}