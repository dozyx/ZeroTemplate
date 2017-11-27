package com.zerofate.template.justfortest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
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
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
@RuntimePermissions
public class MeaninglessActivity extends HelloActivity {

    private static final String TAG = "MeaninglessActivity";
    private ViewTreeObserver viewTreeObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {
        MeaninglessActivityPermissionsDispatcher.testPermissionWithPermissionCheck(this);
        startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                Uri.fromParts("package", getPackageName(), null)), 0);
        testPermission();
    }

    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    public void testPermission() {

//        ToastX.showShort(this, "通讯录读取权限：" + PermissionChecker.checkSelfPermission(this,
//                Manifest.permission.READ_CONTACTS));
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.READ_CONTACTS)) {
//            ToastX.showShort(this, "需要显示帮助");
//        } else {
////            ActivityCompat.requestPermissions(this,
////                    new String[]{Manifest.permission.READ_CONTACTS,
////                            Manifest.permission.WRITE_CONTACTS},
////                    0);
////            ToastX.showShort(this, "开始请求");
//        }
//        startActivityForResult(
//                new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        MeaninglessActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                ToastX.showShort(this, "没有数据");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    public void onDenied() {
        ToastX.showShort(this, "拒绝");
    }

    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    public void onNever() {
        ToastX.showShort(this, "不再询问");
    }

    @OnShowRationale(Manifest.permission.READ_CONTACTS)
    public void onShowRationle(final PermissionRequest request) {
        ToastX.showShort(this, "理由");
    }
}
