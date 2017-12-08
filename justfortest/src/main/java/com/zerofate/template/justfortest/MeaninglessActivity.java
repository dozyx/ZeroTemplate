package com.zerofate.template.justfortest;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.ViewTreeObserver;

import com.google.gson.Gson;
import com.zerofate.androidsdk.util.ToastX;

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
        try {
            String json = "{\n"
                    + "    \"name\": \"张三\",\n"
                    + "    \"agea\": \"11\",\n"
                    + "    \"some\": 1\n"
                    + "}";
            Bean bean = new Gson().fromJson(json,Bean.class);
            ToastX.showLong(this, bean.toString());
        } catch (Exception e) {
            ToastX.showLong(this, e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
