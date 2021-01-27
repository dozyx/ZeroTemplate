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
import java.io.FileOutputStream
import java.io.IOException
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

        addAction(object : Action("文件访问 API") {
            override fun run() {
                // 读写私有目录文件
                val fileContent = "Hello world!"
                openFileOutput("file_putput", MODE_PRIVATE).use {
                    it.write(fileContent.toByteArray())
                    it.close()
                }
                openFileInput("file_putput").bufferedReader().useLines { lines ->
                    lines.fold("") { some, text ->
                        "$some\n$text"
                    }
                }
                Timber.d("fileList: ${Arrays.toString(fileList())}")
                getDir("dir", MODE_PRIVATE)

                // 缓存
                val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // 获取可使用缓存容量大小
                    val cacheQuotaBytes = storageManager.getCacheQuotaBytes(storageManager.getUuidForPath(cacheDir))
                    Timber.d("cacheQuotaBytes: ${cacheQuotaBytes / 1024 / 1024} ${cacheDir.path}")

                    val cacheQuotaBytesExternal = externalCacheDir?.let { storageManager.getUuidForPath(it) }?.let { storageManager.getCacheQuotaBytes(it) }
                    if (cacheQuotaBytesExternal != null) {
                        Timber.d("cacheQuotaBytesExternal: ${cacheQuotaBytesExternal / 1024 / 1024} ${externalCacheDir?.path}")
                    }
                }
                val tempFile = File.createTempFile("temp_file_prefix", "suffixxx")
                Timber.d("File.createTempFile ${tempFile.path}")
                // 删除文件
//                tempFile.delete()
//                deleteFile(tempFile.name)

                // 外置存储
                Timber.d("Environment.getExternalStorageState(): ${Environment.getExternalStorageState()}")
                ContextCompat.getExternalFilesDirs(this@FileActivity, null/*DIRECTORY_PICTURES*/).forEach {
                    // getExternalFilesDirs 的 type 传 null 返回的是私有目录的 files 根目录
                    // 有时候设备会将内置存储的一部分作为外置存储
                    // getExternalFilesDirs 返回的第一个 volume 是主要的外置存储 volume
                    Timber.d("getExternalFilesDirs: ${it.path}")
                }

                // 查询可用空间
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val allocatableBytes = storageManager.getAllocatableBytes(storageManager.getUuidForPath(getExternalFilesDir(null)!!))
                    Timber.d("getAllocatableBytes: ${allocatableBytes / 1024 / 1024}")
                }
            }
        })
        addAction(object : Action("反射") {
            override fun run() {
                val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager
                val method = storageManager.javaClass.getMethod("getVolumeList")
                val volumes: Array<StorageVolume> = method.invoke(storageManager) as Array<StorageVolume>
                volumes.forEach {
                    val methodGetPath = it.javaClass.getMethod("getPath")
                    Timber.d("getVolumeList: ${methodGetPath.invoke(it)}")
                }
            }
        })
        addAction(object : Action("反射") {
            override fun run() {
                val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager
                val method = storageManager.javaClass.getMethod("getVolumeList")
                val volumes: Array<StorageVolume> = method.invoke(storageManager) as Array<StorageVolume>
                volumes.forEach {
                    val methodGetPath = it.javaClass.getMethod("getPath")
                    Timber.d("getVolumeList: ${methodGetPath.invoke(it)}")
                }
            }
        })

        addAction(object : Action("test io") {
            override fun run() {
                Timber.d("test io start")
                canWrite(getExternalFilesDir(null))
                Timber.d("test io end")
            }
        })
    }

    @Synchronized
    fun canWrite(directory: File?): Boolean {
        if (directory == null || !directory.exists()) {
            return false
        }
        val canCreateFile: Boolean
        val randomFileName = ("." + System.currentTimeMillis()
                + "_" + Thread.currentThread().id + "_" + Random().nextLong())
        val testFile = File(directory, randomFileName)
        canCreateFile = try {
            createEmptyFile(testFile)
            true
        } catch (ignore: Throwable) {
            false
        }
        if (!canCreateFile) {
            return false
        }
        val canDeleteFile = testFile.delete()
        if (!canDeleteFile) {
            return false
        }
        val testDirCreation = ("." + System.currentTimeMillis()
                + "_" + Thread.currentThread().id + "_" + Random().nextLong())
        val testDir = File(directory, testDirCreation)
        val canCreateDir = testDir.mkdir()
        return if (!canCreateDir) {
            false
        } else testDir.delete()
    }

    private fun createEmptyFile(file: File?) {
        requireNotNull(file) { "file is null" }
        require(!file.exists()) { "file already exists" }
        require(!file.isDirectory) { "file is directory" }
        FileOutputStream(file).close()
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
