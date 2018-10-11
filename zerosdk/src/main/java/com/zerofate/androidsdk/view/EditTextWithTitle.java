package com.zerofate.androidsdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerofate.androidsdk.R;


/**
 * TODO: 添加最小的宽度或者实现不同字数等距显示
 * @author dozeboy
 * @date 2017/12/4
 */

public class EditTextWithTitle extends LinearLayout {

    TextView textTitle;
    EditText editContent;

    public EditTextWithTitle(Context context,
            @Nullable AttributeSet attrs) {
        // TODO: 添加一个默认的 style
        this(context, attrs, 0);
    }

    public EditTextWithTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_edit_with_title, this);
        textTitle = (TextView) root.findViewById(R.id.text_title);
        editContent = (EditText) root.findViewById(R.id.edit_content);

        setGravity(Gravity.CENTER_VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithTitle);
        textTitle.setText(a.getString(R.styleable.EditTextWithTitle_title));
        textTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                a.getDimension(R.styleable.EditTextWithTitle_titleSize, 16));
        textTitle.setTextColor(a.getColor(R.styleable.EditTextWithTitle_titleColor, Color.WHITE));
        editContent.setText(a.getString(R.styleable.EditTextWithTitle_content));
        editContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                a.getDimension(R.styleable.EditTextWithTitle_contentSize, 16));
        editContent.setTextColor(
                a.getColor(R.styleable.EditTextWithTitle_contentColor, Color.WHITE));
        editContent.setHint(a.getString(R.styleable.EditTextWithTitle_android_hint));
    }


}
