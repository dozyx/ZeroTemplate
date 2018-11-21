package com.dozeboy.android.core.utli.util

import android.view.View
import android.view.ViewGroup

/**
 * @author dozeboy
 * @date 2018/11/21
 */

class VaryViewHelper(private val primaryView: View) {
    private var param: ViewGroup.LayoutParams? = null
    private var viewIndex = -1
    private var currentView = primaryView
    private lateinit var parentView: ViewGroup

    fun replace(view: View) {
        if (param == null) {
            init()
        }
        if (parentView.getChildAt(viewIndex) == view) {
            return
        }
        if (parentView.getChildAt(viewIndex) == currentView) {
            parentView.removeViewAt(viewIndex)
        }
        parentView.addView(view, viewIndex, param)
        currentView = view
    }

    fun restore() {
        replace(primaryView)
    }

    private fun init() {
        param = primaryView.layoutParams
        parentView = primaryView.parent as ViewGroup
        for (i in 0..(parentView.childCount - 1)) {
            if (primaryView == parentView.getChildAt(i)) {
                viewIndex = i
                break
            }
        }
    }

}