package com.zerofate.androidsdk.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.zerofate.androidsdk.R;

/**
 * @author dozeboy
 * @date 2018/7/1
 */
public abstract class BaseSingleFragmentActivity extends AppCompatActivity {

    private static Activity sActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sActivity = this;
        setContentView(R.layout.base_activity_single_fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_container,
                getFragment()).commit();
    }

    public abstract Fragment getFragment();
}
