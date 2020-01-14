package cn.dozyx.template.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import androidx.core.app.ActivityCompat
import androidx.core.app.AppOpsManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

import cn.dozyx.template.base.BaseTestActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import timber.log.Timber

import java.util.Arrays

class PermissionActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("定位", Runnable { requestLocationPermission() })
    }

    override fun onResume() {
        super.onResume()
        // 权限请求返回结果之后会触发 onResume
        Timber.d("PermissionActivity.onResume")
    }

    private fun requestLocationPermission() {
        appendResult("location rationale: " + ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION))
        appendResult(
                "location xiaomi check: " + hasSelfPermissionForXiaomi(this,
                        Manifest.permission.ACCESS_FINE_LOCATION))
        appendResult("location check: " + ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION))
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
    }

    private fun hasSelfPermissionForXiaomi(context: Context, permission: String): Boolean {
        val permissionToOp = AppOpsManagerCompat.permissionToOp(permission)
                ?: // in case of normal permissions(e.g. INTERNET)
                return true
        val noteOp = AppOpsManagerCompat.noteOp(context, permissionToOp, Process.myUid(),
                context.packageName)
        return noteOp == AppOpsManagerCompat.MODE_ALLOWED && PermissionChecker.checkSelfPermission(
                context, permission) == PermissionChecker.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Timber.d("PermissionActivity.onRequestPermissionsResult")
        appendResult(Arrays.toString(permissions) + " & " + Arrays.toString(grantResults))
    }

    override fun initActions() {

    }
}
