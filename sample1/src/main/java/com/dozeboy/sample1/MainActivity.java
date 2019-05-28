package com.dozeboy.sample1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dozeboy.sample1.base.BaseSampleActivity;
import com.dozeboy.sample1.databinding.ActivityMainBinding;

/**
 * @author dozeboy
 * @date 2019/1/6
 */
public class MainActivity extends BaseSampleActivity<ActivityMainBinding> {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.textHello.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
