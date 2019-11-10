package cn.dozyx.template.view.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import timber.log.Timber

/**
 * @author dozyx
 * @date 2019-11-09
 */
class CustomLinearLayout(context: Context,
                         attrs: AttributeSet?) : LinearLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Timber.d("CustomLinearLayout.dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }
}
