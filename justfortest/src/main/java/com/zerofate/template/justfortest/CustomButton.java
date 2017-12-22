package com.zerofate.template.justfortest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.zerofate.androidsdk.util.ToastX;

/**
 * @author Timon
 * @date 2017/12/21
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean performClick() {
        ToastX.showShort(getContext(),"performClick");
        return super.performClick();
    }
}
