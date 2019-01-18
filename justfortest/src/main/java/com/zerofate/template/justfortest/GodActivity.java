package com.zerofate.template.justfortest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 控制启动界面
 *
 * @author dozeboy
 * @date 2017/11/13
 */

public class GodActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MeaninglessActivity.class);
        startActivity(intent);
        finish();
    }
}
