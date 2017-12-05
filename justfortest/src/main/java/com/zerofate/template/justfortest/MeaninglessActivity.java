package com.zerofate.template.justfortest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import butterknife.OnClick;

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
public class MeaninglessActivity extends HelloActivity {

    private static final String TAG = "MeaninglessActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {

    }

}
