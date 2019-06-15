package com.zerofate.java.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zerofate.template.justfortest.R;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author dozeboy
 * @date 2019-06-14
 */
public class CustomTextView extends TextView {


    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.customTextStyle);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.CustomTextStyleRes);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
