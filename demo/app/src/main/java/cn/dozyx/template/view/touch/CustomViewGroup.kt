package cn.dozyx.template.view.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import timber.log.Timber

/**
 * @author dozyx
 * @date 2019-10-20
 */
class CustomViewGroup : ViewGroup {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var child = getChildAt(0)
        child.layout(l, t, l + 360, t + 360)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 需要负责测量每一个子 view
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Timber.d("CustomViewGroup.dispatchTouchEvent ${MotionEvent.actionToString(ev.action)}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Timber.d("CustomViewGroup.onInterceptTouchEvent  ${MotionEvent.actionToString(ev.action)}")
        if (ev.action == MotionEvent.ACTION_MOVE) {
//            return true
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Timber.d("CustomViewGroup.onTouchEvent ${MotionEvent.actionToString(event.action)}")
        return true
    }
}