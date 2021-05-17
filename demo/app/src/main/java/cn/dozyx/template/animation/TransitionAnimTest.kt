package cn.dozyx.template.animation

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionValues
import cn.dozyx.template.R
//import kotlinx.android.synthetic.main.test_transition_anim.*

import kotlinx.android.synthetic.main.test_transition_anim_constraint.*
//import kotlinx.android.synthetic.main.test_transition_anim_constraint.iv_anim

class TransitionAnimTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_transition_anim_motion)
    }
}

class CustomTransition : Transition() {
    override fun captureStartValues(transitionValues: TransitionValues) {

    }

    override fun captureEndValues(transitionValues: TransitionValues) {
    }
}
