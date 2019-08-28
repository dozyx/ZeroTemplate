package com.zerofate.java.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView

import androidx.appcompat.widget.AppCompatTextView

import cn.dozyx.template.justfortest.R
import timber.log.Timber


/**
 * @author dozeboy
 * @date 2019-06-14
 */
@SuppressLint("AppCompatCustomView")
class CustomTextView @SuppressLint("NewApi")
constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : TextView(context, attrs, defStyleAttr, defStyleRes) {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.customTextStyle) : this(context, attrs, defStyleAttr, R.style.CustomTextStyleRes) {
    }

    init {
        val typedArray = resources.obtainAttributes(attrs,
                intArrayOf(android.R.attr.textSize))
        val value = typedArray.getDimension(0, 10f)
        Timber.d("CustomTextView $value ${TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, context.resources.displayMetrics)}")
        typedArray.recycle()
    }

}
