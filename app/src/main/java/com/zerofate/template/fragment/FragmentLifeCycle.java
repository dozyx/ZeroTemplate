package com.zerofate.template.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zerofate.template.activity.LifeCycleTest;
import com.zerofate.template.service.SimpleService;

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
