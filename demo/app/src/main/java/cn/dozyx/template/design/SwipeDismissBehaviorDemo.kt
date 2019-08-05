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

class SwipeDismissBehaviorDemo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_dismiss_behavior_demo)
        ButterKnife.bind(this)
        val behavior = SwipeDismissBehavior<View>()
        behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
        (swipe_dismiss_text!!.layoutParams as CoordinatorLayout.LayoutParams).behavior = behavior
    }
}
