package cn.dozyx.template.design

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

import timber.log.Timber

/**
 * Create by dozyx on 2019/6/25
 */
class CustomBehavior<V : View>: CoordinatorLayout.Behavior<V> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, ev: MotionEvent): Boolean {
        Timber.d("CustomBehavior.onInterceptTouchEvent: ${MotionEvent.actionToString(ev.action)}")
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, ev: MotionEvent): Boolean {
        Timber.d("CustomBehavior.onTouchEvent: ${MotionEvent.actionToString(ev.action)}")
        return super.onTouchEvent(parent, child, ev)
    }
}
