package cn.dozyx.template.demo.swipedismiss

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat

import com.blankj.utilcode.util.ViewUtils

import androidx.customview.widget.ViewDragHelper
import timber.log.Timber

/**
 * @author dozyx
 * @date 2019-10-17
 */
class DragDismissFrameLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private lateinit var dragHelper: ViewDragHelper
    private var originalLeft = 0
    private var originalRight = 0
    private var originalTop = 0
    private var originalBottom = 0

    private var isFirstCaptured = true

    var dismissCallback: DragDismissCallback? = null

    private var shouldDismissOnRelease = false


    override fun onFinishInflate() {
        super.onFinishInflate()
        dragHelper = ViewDragHelper.create(this, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                Timber.d("DragDismissFrameLayout.tryCaptureView")
                return true
            }


            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                Timber.d("DragDismissFrameLayout.onViewPositionChanged $left $top $dx $dy")
                // 参考微信朋友圈图片，只在向下滑动时缩小
                if (top <= originalTop) {
                    return
                }
                val scale = 1 - (top - originalTop) / (originalBottom - originalTop).toFloat()
                changedView.scaleX = scale
                changedView.scaleY = scale
                shouldDismissOnRelease = scale < 0.6
            }

            override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
                super.onViewCaptured(capturedChild, activePointerId)
                // 多点触摸时会触发多次
                Timber.d("DragDismissFrameLayout.onViewCaptured ${capturedChild.left}")
                if (isFirstCaptured) {
                    originalLeft = capturedChild.left
                    originalRight = capturedChild.right
                    originalTop = capturedChild.top
                    originalBottom = capturedChild.bottom
                    isFirstCaptured = false
                }
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                Timber.d("DragDismissFrameLayout.clampViewPositionVertical")
                return top
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }

            override fun onViewDragStateChanged(state: Int) {
                super.onViewDragStateChanged(state)
                Timber.d("DragDismissFrameLayout.onViewDragStateChanged $state")
            }

            override fun getViewVerticalDragRange(child: View): Int {
                Timber.d("DragDismissFrameLayout.getViewVerticalDragRange")
                return 1
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return 0
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                Timber.d("DragDismissFrameLayout.onViewReleased $yvel")
                if (shouldDismissOnRelease && dismissCallback != null) {
                    dismissCallback?.onDisimiss()
                    return
                }
                val parent = releasedChild.parent as ViewGroup
//                dragHelper.flingCapturedView(parent.left,parent.top,0,parent.bottom)
                if (dragHelper.settleCapturedViewAt(originalLeft, originalTop)) {
//                    ViewCompat.postOnAnimation(releasedChild,SettleRunnable(releasedChild))
                    invalidate()
                }
            }

        })
    }

    private inner class SettleRunnable internal constructor(private val view: View) : Runnable {

        override fun run() {
            if (dragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(view, this)
            }
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Timber.d("DragDismissFrameLayout.onInterceptTouchEvent ${MotionEvent.actionToString(ev.action)}")
        if (dragHelper.shouldInterceptTouchEvent(ev)) {
            return true
        }
        return false

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Timber.d("DragDismissFrameLayout.onTouchEvent ${MotionEvent.actionToString(event.action)}")
        if (dragHelper != null) {
            dragHelper.processTouchEvent(event)
            return true
        }
        return super.onTouchEvent(event)
    }


}
