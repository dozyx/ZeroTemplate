package cn.dozyx.template.view.touch

import android.view.MotionEvent
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import timber.log.Timber

/**
 * @author dozyx
 * @date 2019-10-20
 */
class TouchTest :BaseActivity() {
    override fun getLayoutId(): Int = R.layout.touch_test

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        Timber.d("TouchTest.onTouchEvent ${MotionEvent.actionToString(event.action)}")
        return super.onTouchEvent(event)
    }

}