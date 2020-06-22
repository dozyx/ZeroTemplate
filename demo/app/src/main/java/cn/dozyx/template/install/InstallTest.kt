package cn.dozyx.template.install

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.AppUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File

/**
 * 声明权限：android.permission.REQUEST_INSTALL_PACKAGES
 */
class InstallTest : BaseTestActivity() {

    override fun initActions() {
        addAction(object : Action("安装") {
            override fun run() {
                installApk()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_PACKAGE_ADDED)// 安装了新的应用
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED)
        filter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED) // 对某个应用的数据进行了清除
        filter.addAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH)
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED) // app 被卸载
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED) // 覆盖安装了新版本
        filter.addDataScheme("package")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                appendResult("action: ${intent?.action} data: ${intent?.data} replacing: ${intent?.getBooleanExtra(Intent.EXTRA_REPLACING, false)}")
            }
        }, filter)
    }

    @SuppressLint("CheckResult")
    fun installApk() {
        RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
            appendResult("申请权限结果 $it")
            if (!it) {
                appendResult("没有读写权限")
                return@subscribe
            }
//            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            // targetSdkVersion 为 28，用 getExternalStoragePublicDirectory 可以得到共享的下载目录，但是 listFiles 出错
            val downloadDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            if (downloadDir == null) {
                appendResult("找不到下载目录")
                return@subscribe
            }
            appendResult("下载目录 ${downloadDir.path}")
            var apkFile: File? = null
            for (listFile in downloadDir.listFiles()) {
                if (listFile.name.endsWith(".apk")) {
                    apkFile = listFile
                    break
                }
            }
            if (apkFile == null) {
                appendResult("下载目录没有找到 apk 文件")
                return@subscribe
            }
            appendResult("安装 ${apkFile.path}")
            AppUtils.installApp(apkFile)
        }
    }
}