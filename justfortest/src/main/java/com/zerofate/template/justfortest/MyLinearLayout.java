package com.zerofate.template.justfortest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Create by timon on 2019/4/9
 **/
public class MyLinearLayout extends LinearLayout implements Runnable {
    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*public void onClick(View v) {
        // 会导致编译不过去，改成 private 可以编译过去
    }*/

    public void myClick(View v) {

    }

    @Override
    public void run() {

    }
}
