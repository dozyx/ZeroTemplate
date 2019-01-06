package com.dozeboy.sample1;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.dozeboy.sample1.base.BaseSampleActivity;
import com.dozeboy.sample1.databinding.ActivityMainBinding;
import com.zerofate.androidsdk.util.ToastX;

import javax.inject.Inject;

import androidx.annotation.Nullable;

/**
 * @author dozeboy
 * @date 2019/1/6
 */
public class MainActivity extends BaseSampleActivity<ActivityMainBinding> {

    @Inject
    Application application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.textHello.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        DaggerMainComponent.builder().appComponent(
                SampleApplication.getAppComponent()).build().inject(this);
        ToastX.showLong(this,application.getPackageName());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
