package cn.dozyx.template.view.touch.conflict

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import timber.log.Timber

/**
 * @author dozyx
 * @date 2019-10-29
 */
class HorizontalScrollViewEx(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            Timber.d("HorizontalScrollViewEx.onLayout $l $t $r $b")
            val distance = i * (r - l)
            getChildAt(i).layout(l + distance, t, r + distance, b)
        }
    }
}
