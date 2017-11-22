package com.zerofate.template.justfortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zerofate.template.justfortest.purchase.PurchaseActivity;

/**
 * 控制启动界面
 * @author Timon
 * @date 2017/11/13
 */

public class GodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MeaninglessActivity.class);
        startActivity(intent);
        finish();
    }
}
