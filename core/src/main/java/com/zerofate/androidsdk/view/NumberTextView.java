package com.zerofate.androidsdk.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * 只能显示数字的 TextView
 *
 * @author dozeboy
 * @date 2017/11/7
 */

public class NumberTextView extends TextView {
    // TODO: 2017/11/7 添加 Style
    private int max = 100;
    private int min = 0;
    private OnOverRangeListener onOverRangeListener;

    public NumberTextView(Context context) {
        super(context);
        init();
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setNumber(int number) {
        int result = Math.max(min, Math.min(number, max));
        setText(String.valueOf(result));
    }

    private boolean isInRange(int number) {
        if (number >= min && number <= max) {
            return true;
        }
        return false;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setOnOverRangeListener(OnOverRangeListener listener) {
        onOverRangeListener = listener;
    }

    private void init() {
        setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    public interface OnOverRangeListener {
        void onOverMax();
        void onBelowMin();
    }
}
