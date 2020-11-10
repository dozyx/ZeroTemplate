package cn.dozyx.template.view.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import timber.log.Timber

/**
 * @author dozyx
 * @date 2019-10-20
 */
class CustomView : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Timber.d("CustomView.dispatchTouchEvent ${MotionEvent.actionToString(event.action)}")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Timber.d("CustomView.onTouchEvent ${MotionEvent.actionToString(event.action)}")
        if (event.action == MotionEvent.ACTION_DOWN){
//            return true
        }
//        parent.requestDisallowInterceptTouchEvent(true)
        return true
    }

}