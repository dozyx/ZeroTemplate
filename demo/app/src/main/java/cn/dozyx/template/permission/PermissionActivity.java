package cn.dozyx.template.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import cn.dozyx.template.base.BaseTestActivity;

import java.util.Arrays;

public class PermissionActivity extends BaseTestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addButton("定位", () -> requestLocationPermission());
    }

    private void requestLocationPermission() {
        appendResult("location rationale: " + ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION));
        appendResult(
                "location xiaomi check: " + hasSelfPermissionForXiaomi(this,
                        Manifest.permission.ACCESS_FINE_LOCATION));
        appendResult("location check: " + ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION));
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    private static boolean hasSelfPermissionForXiaomi(Context context, String permission) {
        String permissionToOp = AppOpsManagerCompat.permissionToOp(permission);
        if (permissionToOp == null) {
            // in case of normal permissions(e.g. INTERNET)
            return true;
        }
        int noteOp = AppOpsManagerCompat.noteOp(context, permissionToOp, Process.myUid(),
                context.getPackageName());
        return noteOp == AppOpsManagerCompat.MODE_ALLOWED && PermissionChecker.checkSelfPermission(
                context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        appendResult(Arrays.toString(permissions) + " & " + Arrays.toString(grantResults));
    }

    @Override
    public void initActions() {

    }
}
