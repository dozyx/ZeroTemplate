package cn.dozyx.template.design

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

import timber.log.Timber

/**
 * Behavior 理解：提供一个机会使 child view 可以提前接管 CoordinatorLayout 的触摸事件。
 * Create by dozyx on 2019/6/25
 */
class CustomBehavior<V : View>: CoordinatorLayout.Behavior<V> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    /**
     * 在 CoordinatorLayout 的触摸事件分发给子 View 之前进行拦截。一旦 Behavior 拦截了某事件，后续的事件流都会传给 onTouchEvent 方法
     */
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, ev: MotionEvent): Boolean {
        Timber.d("CustomBehavior.onInterceptTouchEvent: ${MotionEvent.actionToString(ev.action)}")
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    /**
     * 在 Behavior 拦截了 CoordinatorLayout 的 touch event 后，处理后续的 event。
     * 应该在此方法中改变 view 的布局。
     */
    override fun onTouchEvent(parent: CoordinatorLayout, child: V, ev: MotionEvent): Boolean {
        Timber.d("CustomBehavior.onTouchEvent: ${MotionEvent.actionToString(ev.action)}")
        return super.onTouchEvent(parent, child, ev)
    }

    /**
     * 指定 child 是否有一个作为 layout 依赖的 view
     */
    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        return super.layoutDependsOn(parent, child, dependency)
    }

    /**
     * dependent view 发生改变（尺寸或位置）时回调。
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        return super.onDependentViewChanged(parent, child, dependency)
    }
}
