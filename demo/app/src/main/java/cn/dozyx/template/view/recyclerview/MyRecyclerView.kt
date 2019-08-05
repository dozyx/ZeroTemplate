package cn.dozyx.template.view.recyclerview

import android.content.Context
import android.util.AttributeSet
import cn.dozyx.core.utli.log.LogUtil

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
        LogUtil.d("onDetachedFromWindow: ")
    }

    /*override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val density = resources.displayMetrics.density
        // 限制高度
        val heightSpecNew = MeasureSpec.makeMeasureSpec((density * 60 * 5).toInt(), MeasureSpec.AT_MOST)
        super.onMeasure(widthSpec, heightSpecNew)
    }*/
}