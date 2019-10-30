package cn.dozyx.template.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import timber.log.Timber

/**
 * Create by timon on 2019/10/28
 */
class CustomViewPager : ViewPager {
    constructor(context: Context) : super(context) {}

    constructor(context: Context,
                attrs: AttributeSet?) : super(context, attrs) {
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
        Timber.d("CustomViewPager.requestDisallowInterceptTouchEvent")
    }
}
