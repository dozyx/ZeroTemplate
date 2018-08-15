package com.zerofate.template.view.list

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import com.dozeboy.android.core.utli.log.ZLog

/**
 * @author timon
 * @date 2018/8/14
 */

class MyListView : ListView {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onDetachedFromWindow() {
        ZLog.d("onDetachedFromWindow: ")
        super.onDetachedFromWindow()
    }
}