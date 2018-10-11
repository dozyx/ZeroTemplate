package com.zerofate.template.fragment;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zerofate.template.activity.LifeCycleTest;

public class FragmentLifeCycle extends LifeCycleTest {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(new FragmentLifeCycleFragment(), null);
            transaction.commit();
        }
    }

}
