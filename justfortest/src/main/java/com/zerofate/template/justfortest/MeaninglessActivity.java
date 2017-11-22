package com.zerofate.template.justfortest;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.zerofate.androidsdk.activity.AdPagerActivity;
import com.zerofate.androidsdk.adapter.ImagePagerAdapter;
import com.zerofate.androidsdk.util.PermissionHelper;
import com.zerofate.androidsdk.util.ToastX;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
public class MeaninglessActivity extends HelloActivity {

    private static final String TAG = "MeaninglessActivity";
    private ViewTreeObserver viewTreeObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {
        testPermission();
    }

    private void testPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)){
            ToastX.showShort(this,"需要显示帮助");
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS},
                    0);
            ToastX.showShort(this,"开始请求");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
