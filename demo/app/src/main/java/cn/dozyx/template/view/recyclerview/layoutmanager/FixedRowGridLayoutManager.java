package cn.dozyx.template.view.recyclerview.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;

public class FixedRowGridLayoutManager extends GridLayoutManager {

    public FixedRowGridLayoutManager(Context context) {
        super(context, 3, GridLayoutManager.HORIZONTAL, false);
    }



}
