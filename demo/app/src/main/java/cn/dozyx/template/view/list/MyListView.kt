package cn.dozyx.template.view.list

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import cn.dozyx.core.utli.log.LogUtil

/**
 * @author dozeboy
 * @date 2018/8/14
 */

class MyListView : ListView {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onDetachedFromWindow() {
        LogUtil.d("onDetachedFromWindow: ")
        super.onDetachedFromWindow()
    }
}