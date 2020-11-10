package cn.dozyx.template

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import androidx.core.content.ContextCompat
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.SDCardUtils
import timber.log.Timber
import java.io.File
import java.util.*

class FileActivity : BaseTestActivity() {
    private val REQUEST_CODE_SD_CARD = 42
    override fun initActions() {
        addAction(object : Action("volumns") {
            override fun run() {
                val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val storageVolumes = storageManager.storageVolumes
                    Timber.d("FileActivity.run $storageVolumes")
                }
            }
        })

        addAction(object : Action("sd card") {
            override fun run() {
                try {
                    val sdCardRoot = getSdCardRoot()
                    val file = File(sdCardRoot, "${System.currentTimeMillis()}")
//                    FileOutputStream(file).close()
                    file.createNewFile()
                    Timber.d("FileActivity.run success")
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        })

        addAction(object : Action("sd card 权限") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REQUEST_CODE_SD_CARD)
                }
                val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val storageVolumes = storageManager.storageVolumes
                    var sdVolume: StorageVolume? = null
                    storageVolumes.forEach {
                        if (it.isRemovable) {
                            sdVolume = it
                        }
                    }
                    sdVolume?.let {
                        startActivityForResult(it.createAccessIntent(null), REQUEST_CODE_SD_CARD)
                    }
                }
            }
        })
    }

    private fun getSdCardRoot(): String? {
        val sdCardInfos = SDCardUtils.getSDCardInfo()
        sdCardInfos.forEach {
            if (it.isRemovable) {
                return it.path
            }
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appendResult("""
            Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES): ${Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).absolutePath}\n

            Environment.getDataDirectory(): ${Environment.getDataDirectory().absolutePath}

            Environment.getExternalStorageDirectory(): ${Environment.getExternalStorageDirectory().absolutePath}

            Environment.getDownloadCacheDirectory(): ${Environment.getDownloadCacheDirectory().absolutePath}

            ContextCompat.getDataDir: ${ContextCompat.getDataDir(this)?.absolutePath ?: "无"}

            context.filesDir: ${filesDir.absolutePath}

            ContextCompat.getExternalCacheDirs: ${Arrays.toString(ContextCompat.getExternalCacheDirs(this))}

            context.cacheDir: ${cacheDir.absolutePath}
            
            context.getDir: ${getDir("test", Context.MODE_PRIVATE)}

            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES): ${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}
            
        """.trimIndent())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appendResult("""
                context.getExternalMediaDirs(): ${Arrays.toString(externalMediaDirs)}
                
                context.getExternalFilesDirs: ${Arrays.toString(getExternalFilesDirs(null))}
            """.trimIndent())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SD_CARD) {
            Timber.d("FileActivity.onActivityResult ${data?.data}")
        }
    }
}
