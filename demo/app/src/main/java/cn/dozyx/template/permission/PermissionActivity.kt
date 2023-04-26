package cn.dozyx.template.permission

import android.Manifest
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Bundle
import android.os.Environment
import android.os.Process
import androidx.core.app.ActivityCompat
import androidx.core.app.AppOpsManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.PermissionUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import timber.log.Timber
import java.io.File
import java.io.RandomAccessFile
import java.util.*

class PermissionActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("定位", Runnable { requestLocationPermission() })
        addButton("存储权限", Runnable {
            val rxPermission = RxPermissions(this)
            rxPermission
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe {
                        try {
//                            val dir = "${ContextCompat.getExternalFilesDirs(this, null)[0].absoluteFile}"
                            val dir = "${Environment.getExternalStorageDirectory().absoluteFile}"

                            val filePath = "${dir}${File.separator}15\n1.txt"
                            Timber.d("$filePath exist: ${File(filePath).exists()}")
                            RandomAccessFile(filePath, "rw")// targetSdkVersion 23，在 Android11 设备上，向外部存储写入带换行符号文件名的文件会出现异常。如果写入的目录为应用的专属目录会正常。
                        } catch (e: Exception) {
                            Timber.e(e)
                        }
                    }
        })
        addButton("文件", Runnable {
            val dir = "${Environment.getExternalStorageDirectory().absoluteFile}"
            val filePath = "${dir}${File.separator}15\n1.txt"
            val file = File(filePath)
            if (file.exists()) {
                file.delete()
            } else {
                file.createNewFile()
            }
            Timber.d("$filePath exist: ${File(filePath).exists()}")
        })
        addButton("应用列表", Runnable {
            Timber.d("读取应用列表权限 start")
            val installedPackages =
                packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
            // 权限拒绝时只返回了系统应用列表及当前应用信息
            val filterResult =
                installedPackages.filter { (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
//            installedPackages.forEach {
//                Timber.d("应用列表 package: %s", it)
//            }
            Timber.d("应用列表 size: ${installedPackages.size}")
            Timber.d("应用列表 size 非系统应用: ${filterResult.size}")
            // 弹出权限确认弹窗时会阻塞
            Timber.d("读取应用列表权限 end")
        })

        addButton("应用列表权限查询", Runnable {
//            Handler().postDelayed({
//                val result =
//                    PermissionChecker.checkSelfPermission(
//                        this,
//                        Manifest.permission.QUERY_ALL_PACKAGES
//                    )
//                Timber.d("应用列表权限查询: $result")
//            }, 5000)
            val result1 =
                PermissionChecker.checkSelfPermission(this, Manifest.permission.QUERY_ALL_PACKAGES)
//            val result = PermissionUtils.isGranted(Manifest.permission.QUERY_ALL_PACKAGES)
            val result2 = isGetInstalledAppPermissionGranted()
            Timber.d("应用列表权限查询: $result1 $result2")
            // 0 granted，-1 denied
        })

        addButton("请求应用列表权限", Runnable {
            PermissionUtils.permission(Manifest.permission.QUERY_ALL_PACKAGES).request()
        })
    }

    private fun isGetInstalledAppPermissionGranted(): Boolean {
        try {
            val permissionInfo: PermissionInfo? =
                packageManager.getPermissionInfo(
                    "com.android.permission.GET_INSTALLED_APPS",
                    0
                )
            if (permissionInfo != null) {
                return ContextCompat.checkSelfPermission(
                    this,
                    "com.android.permission.GET_INSTALLED_APPS"
                ) == PackageManager.PERMISSION_GRANTED
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return false
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
        addAction(object : Action("mkdirs") {
            override fun run() {
                val granted = ContextCompat.checkSelfPermission(this@PermissionActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (granted != PackageManager.PERMISSION_GRANTED) {
                    PermissionUtils.launchAppDetailsSettings()
                    Timber.d("File mkdir goto settings")
                    return
                }
                val file = File(Environment.getExternalStorageDirectory(), "test")
                if (file.exists()) {
                    file.delete()
                }
                val mkdirResult = file.mkdir()
                Timber.d("File mkdir result $mkdirResult")
            }
        })
    }
}
