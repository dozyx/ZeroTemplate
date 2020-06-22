package cn.dozyx.template.justfortest.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * Create by dozyx on 2019/11/6
 **/
public class CustomBindingAdapter {
    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int newPadding) {
        if (oldPadding != newPadding) {
            view.setPadding(newPadding,
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }
}
