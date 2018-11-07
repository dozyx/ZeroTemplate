package com.zerofate.androidsdk.view.controller;

import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 因为考虑大部分使用场景的数字不会太多，所以使用 float 进行处理
 *
 * @author dozeboy
 * @date 2018/1/5
 */

public class IncreaseDecreaseController {
    private EditText editText;
    private View increaseView;
    private View decreaseView;
    private String format;
    private float step;
    private float max;
    private float min;
    private OnValueListener onValueListener;

    private View.OnClickListener increaseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            increase();
        }
    };

    private View.OnClickListener decreaseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            decrease();
        }
    };

    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                editText.setSelection(editText.getText().toString().length());
            } else {
                setValue(editText.getText().toString());
            }
        }
    };

    private IncreaseDecreaseController(Builder builder) {
        editText = builder.editText;
        increaseView = builder.increaseView;
        decreaseView = builder.decreaseView;
        format = builder.format;
        step = builder.step;
        max = builder.max;
        min = builder.min;
    }

    public void setOnValueListener(OnValueListener listener) {
        onValueListener = listener;
    }

    private void increase() {
        setValue(getValue() + step);
    }

    private void decrease() {
        setValue(getValue() - step);
    }

    private float getValue() throws NumberFormatException {
        String text = editText.getText().toString();
        float val;
        try {
            val = Float.valueOf(text);
        } catch (NumberFormatException e) {
            if (onValueListener != null) {
                onValueListener.onTextIllegal(text);
            }
            val = min;
        }
        return val;
    }

    private void setValue(String text) {
        float val;
        try {
            val = Float.valueOf(text);
        } catch (NumberFormatException e) {
            if (onValueListener != null) {
                onValueListener.onTextIllegal(text);
            }
            val = min;
        }
        setValue(val);
    }

    private void setValue(float val) {
        if (val < min) {
            if (onValueListener != null) {
                onValueListener.onValueBelowMin(val, min);
            }
            val = min;
        } else if (val > max) {
            if (onValueListener != null) {
                onValueListener.onValueAboveMax(val, max);
            }
            val = max;
        }
        editText.setText(format(val));
    }

    private String format(float value) {
        DecimalFormat format = new DecimalFormat(this.format);
        return format.format(value);
    }

    public static class Builder {
        private EditText editText;
        private View increaseView;
        private View decreaseView;
        private String format;
        private float step;
        private float max;
        private float min;

        public Builder(EditText editText, View increaseView, View decreaseView) {
            this.editText = editText;
            this.increaseView = increaseView;
            this.decreaseView = decreaseView;
        }

        public Builder format(String format) {
            this.format = format;
            return this;
        }

        public Builder step(float step) {
            this.step = step;
            return this;
        }

        public Builder max(float max) {
            this.max = max;
            return this;
        }

        public Builder min(float min) {
            this.min = min;
            return this;
        }

        public IncreaseDecreaseController create() {
            return new IncreaseDecreaseController(this);
        }
    }

    public interface OnValueListener {
        void onValueBelowMin(float val, float min);

        void onValueAboveMax(float val, float max);

        void onTextIllegal(String text);
    }
}
