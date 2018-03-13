package com.zerofate.template.justfortest;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zerofate.template.justfortest.databinding.ActivityDataBindingTestBinding;

public class DataBindingTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingTestBinding dataBindingTestActivity = DataBindingUtil.setContentView(this,R.layout.activity_data_binding_test);
    }
}
