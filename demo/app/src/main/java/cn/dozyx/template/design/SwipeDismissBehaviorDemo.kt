package cn.dozyx.template.design

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.google.android.material.behavior.SwipeDismissBehavior
import cn.dozyx.template.R

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_swipe_dismiss_behavior_demo.*
import timber.log.Timber

class SwipeDismissBehaviorDemo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_dismiss_behavior_demo)
        val behavior = object :SwipeDismissBehavior<View>(){
            override fun canSwipeDismissView(view: View): Boolean {
                // 解决错误操作没有 Behavior 的 view 的问题
//                return view == swipe_dismiss_text
                return super.canSwipeDismissView(view)
            }
        }
        behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
        (swipe_dismiss_text!!.layoutParams as CoordinatorLayout.LayoutParams).behavior = behavior
        // 当第一次触摸 swipe_dismiss_text 之后，其他 view 也会有 swipe dismiss 的特性。。。感觉是个 bug
        // 查看源码，发现是因为第一次触摸带 SwipeDismissBehavior 的 view 之后，后续触摸其他 view，因为 child 都没有消耗事件，
        // 这时候会回到 CoordinatorLayout 的 onTouchEvent，在这里调用了 SwipeDismissBehavior 的 onTouchEvent()，而 SwipeDismissBehavior 的 onTouchEvent()
        // 会在发现之后存在过拖拉之后一直消耗该事件。并且其滑动的 View 是根据触摸位置寻找的，这就导致了其他没有设置 Behavior 的 View 被移动。。。

        // 解决方案：重写 SwipeDismissBehavior 的 canSwipeDismissView 来判断是否为可移动的 view

        // 的确有人提供这个问题 https://issuetracker.google.com/issues/62400991，但两年多了还没解决。。。
    }
}
