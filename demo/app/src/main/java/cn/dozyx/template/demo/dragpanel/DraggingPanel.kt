package cn.dozyx.template.demo.dragpanel

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout

import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import cn.dozyx.template.R

/**
 * 在这个示例里 DraggingPanel 的位置在 drag 过程中不会改变，变化的是 main_layout 的位置。这样就存在一个问题：DraggingPanel 下的 view 无法接收点击。
 * 试验证明我的想法是错的。。。因为 DraggingPanel 没有消耗 event，event 还是会传递给下面的 view。
 * @author dozyx
 * @date 2019-10-20
 */
class DraggingPanel(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private val AUTO_OPEN_SPEED_LIMIT = 800.0
    private var mDraggingState = 0
    private var mQueenButton: LinearLayout? = null
    private var mDragHelper: ViewDragHelper? = null
    private var mDraggingBorder: Int = 0
    private var mVerticalRange: Int = 0
    var isOpen: Boolean = false
        private set

    val isMoving: Boolean
        get() = mDraggingState == ViewDragHelper.STATE_DRAGGING || mDraggingState == ViewDragHelper.STATE_SETTLING


    inner class DragHelperCallback : ViewDragHelper.Callback() {
        override fun onViewDragStateChanged(state: Int) {
            if (state == mDraggingState) { // no change
                return
            }
            if ((mDraggingState == ViewDragHelper.STATE_DRAGGING || mDraggingState == ViewDragHelper.STATE_SETTLING) && state == ViewDragHelper.STATE_IDLE) {
                // view 停止移动

                if (mDraggingBorder == 0) {
                    onStopDraggingToClosed()
                } else if (mDraggingBorder == mVerticalRange) {
                    isOpen = true
                }
            }
            if (state == ViewDragHelper.STATE_DRAGGING) {
                onStartDragging()
            }
            mDraggingState = state
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            mDraggingBorder = top
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return mVerticalRange
        }

        override fun tryCaptureView(view: View, i: Int): Boolean {
            return view.id == R.id.main_layout
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val topBound = paddingTop
            val bottomBound = mVerticalRange
            // top 表示的是将要移动到的位置，这里限制了 top 的最小位置，即始终显示一部分
            return Math.min(Math.max(top, topBound), bottomBound)
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            // 脱离 drag 之后，确定要放置的位置
            val rangeToCheck = mVerticalRange.toFloat()
            if (mDraggingBorder == 0) {
                isOpen = false
                return
            }
            if (mDraggingBorder.toFloat() == rangeToCheck) {
                isOpen = true
                return
            }
            var settleToOpen = false
            if (yvel > AUTO_OPEN_SPEED_LIMIT) { // speed has priority over position
                settleToOpen = true
            } else if (yvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false
            } else if (mDraggingBorder > rangeToCheck / 2) {
                settleToOpen = true
            } else if (mDraggingBorder < rangeToCheck / 2) {
                settleToOpen = false
            }

            val settleDestY = if (settleToOpen) mVerticalRange else 0

            if (mDragHelper!!.settleCapturedViewAt(0, settleDestY)) {
                ViewCompat.postInvalidateOnAnimation(this@DraggingPanel)
            }
        }
    }

    init {
        isOpen = false
    }

    override fun onFinishInflate() {
        mQueenButton = findViewById(R.id.queen_button)
        mDragHelper = ViewDragHelper.create(this, 1.0f, DragHelperCallback())
        isOpen = false
        super.onFinishInflate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // 我觉得这个 0.84 的计算很随意。。。就是计算了隐藏占比
        mVerticalRange = (h * 0.84).toInt()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun onStopDraggingToClosed() {
        // To be implemented
    }

    private fun onStartDragging() {

    }

    private fun isQueenTarget(event: MotionEvent): Boolean {
        val queenLocation = IntArray(2)
        mQueenButton!!.getLocationOnScreen(queenLocation)
        val upperLimit = queenLocation[1] + mQueenButton!!.measuredHeight
        val lowerLimit = queenLocation[1]
        val y = event.rawY.toInt()
        return y in (lowerLimit + 1) until upperLimit
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        // 限制触摸区域
        return isQueenTarget(event) && mDragHelper!!.shouldInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isQueenTarget(event) || isMoving) {
            mDragHelper!!.processTouchEvent(event)
            return true
        } else {
            return super.onTouchEvent(event)
        }
    }

    override fun computeScroll() { // needed for automatic settling.
        if (mDragHelper!!.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}
