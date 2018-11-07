package com.zerofate.template.view.recyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import com.dozeboy.android.core.utli.log.ZLog

/**
 * @author dozeboy
 * @date 2018/8/14
 */

class MyRecyclerView : androidx.recyclerview.widget.RecyclerView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ZLog.d("onDetachedFromWindow: ")
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val density = resources.displayMetrics.density
        val heightSpecNew = MeasureSpec.makeMeasureSpec((density * 60 * 5).toInt(), MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpecNew)
    }
}