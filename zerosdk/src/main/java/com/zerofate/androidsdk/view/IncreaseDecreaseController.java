package com.zerofate.androidsdk.view;

import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;


/**
 * 用于点击加减按钮来实现数字增减
 * 功能：增加、减小、可编辑、最大、最小值
 *
 * @author dozeboy
 * @date 2017/12/21
 */

public class IncreaseDecreaseController {
    private View viewIncrease;
    private View viewDecrease;
    private EditText editValue;
    private double step;
    private BigDecimal value;
    private BigDecimal max;
    private BigDecimal min;
    private ValueCallback valueCallback;

    private View.OnClickListener increaseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            increase();
        }
    };

    private View.OnClickListener decreaseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            decrease();
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                BigDecimal newValue = new BigDecimal(s.toString());
                checkRangeAndSetValue(newValue);
            } catch (NumberFormatException e) {
                if (valueCallback != null) {
                    valueCallback.onValueInputError(s.toString());
                }
                setValue(value);
            }
        }
    };

    private IncreaseDecreaseController(Builder builder) {
        viewIncrease = builder.viewIncrease;
        viewDecrease = builder.viewDecrease;
        editValue = builder.editValue;
        step = builder.step;
        value = new BigDecimal(builder.initValue);
        max = new BigDecimal(builder.max);
        min = new BigDecimal(builder.min);
        valueCallback = builder.valueCallback;

        viewIncrease.setOnClickListener(increaseClickListener);
        viewDecrease.setOnClickListener(decreaseClickListener);
        editValue.addTextChangedListener(textWatcher);
        editValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void increase() {
        ensureValueNotNull();
        BigDecimal temp = value.add(new BigDecimal(step));
        checkRangeAndSetValue(temp);
    }

    public void decrease() {
        ensureValueNotNull();
        BigDecimal temp = value.add(new BigDecimal(-step));
        checkRangeAndSetValue(temp);
    }

    private void ensureValueNotNull() {
        if (value == null) {
            value = new BigDecimal(0);
        }
    }

    private void checkRangeAndSetValue(@NonNull BigDecimal newValue) {
        if (newValue.compareTo(max) == 1) {
            if (valueCallback != null) {
                valueCallback.onValueOverMax(newValue.doubleValue(), max.doubleValue());
                value = max;
            }
        } else if (newValue.compareTo(min) == -1) {
            if (valueCallback != null) {
                valueCallback.onValueUnderMin(newValue.doubleValue(), min.doubleValue());
                value = min;
            }
        }
    }

    private void setValue(BigDecimal newValue) {
        value = newValue;
        editValue.setText(value.stripTrailingZeros().toPlainString());
    }

    public static class Builder {
        private View viewIncrease;
        private View viewDecrease;
        private EditText editValue;
        private double step;
        private double initValue;
        private double max;
        private double min;
        private ValueCallback valueCallback;

        public Builder increaseView(View view) {
            viewIncrease = view;
            return this;
        }

        public Builder decreaseView(View view) {
            viewDecrease = view;
            return this;
        }

        public Builder editView(EditText editText) {
            editValue = editText;
            return this;
        }

        public Builder step(double step) {
            this.step = step;
            return this;
        }

        public Builder initValue(double initValue) {
            this.initValue = initValue;
            return this;
        }

        public Builder max(double max) {
            this.max = max;
            return this;
        }

        public Builder min(double min) {
            this.min = min;
            return this;
        }

        public Builder callback(ValueCallback callback) {
            valueCallback = callback;
            return this;
        }

        public IncreaseDecreaseController create() {
            return new IncreaseDecreaseController(this);
        }


    }

    public interface ValueCallback {
        void onValueOverMax(double newValue, double max);

        void onValueUnderMin(double newValue, double min);

        void onValueInputError(String inputString);
    }


}
