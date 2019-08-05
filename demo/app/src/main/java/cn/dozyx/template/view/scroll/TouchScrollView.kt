package cn.dozyx.template.view.scroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils

/**
 * View 相当于一个窗口，scroll 过程中，窗口的位置是固定的，scroll 的实际对象是 content。
 * 向上向左 scroll 时，scroll 距离是正的，反之为负。
 * Create by timon on 2019/5/29
 */
class TouchScrollView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.rawX
                lastTouchY = event.rawY
//                (parent as ViewGroup).scrollBy(-100, -100)
            }
            MotionEvent.ACTION_MOVE -> run {
                LogUtils.d(event.rawX - lastTouchX)
                val scrollX = -(event.rawX - lastTouchX).toInt()
                val scrollY = -(event.rawY - lastTouchY).toInt()
                (parent as ViewGroup).scrollBy(scrollX, scrollY)
                lastTouchX = event.rawX
                lastTouchY = event.rawY
            }
        }
        return true
    }
}
