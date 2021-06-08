package cn.dozyx.template

import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.StatFs
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.provider.MediaStore
import android.text.TextUtils
import androidx.core.content.ContextCompat
import cn.dozyx.core.utli.system.StorageUtils
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.SDCardUtils
import timber.log.Timber
import java.io.*
import java.util.*

class FileTest : BaseTestActivity() {
    private val REQUEST_CODE_SD_CARD = 42
    override fun initActions() {
        logStorageInfo()

        addAction(object : Action("volumns") {
            override fun run() {
                val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val storageVolumes = storageManager.storageVolumes
                    Timber.d("all storageVolumes: $storageVolumes")
                    storageVolumes.forEach {
                        Timber.d("storageVolume: $it")
                        // StorageVolume#getPath 不是公开方法
                    }
                }
            }
        })

        addAction(object : Action("volumns reflect") {
            override fun run() {
                val mountedVolumeList = StorageUtils.getMountedVolumeList(this@FileTest)
                mountedVolumeList.forEach {
                    Timber.d("storageVolume: $it")
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

        addAction(object : Action("write external") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val mediaDirs = externalMediaDirs
                    mediaDirs.forEach {
                        val tempFile = File.createTempFile("${System.currentTimeMillis()}", ".txt", it)
                        try {
                            tempFile.createNewFile()
                            Timber.d("create tmp success: ${tempFile.absolutePath}")
                        }catch (e:Exception){
                            Timber.e("create tmp failed ${tempFile.absolutePath}: $e")
                        }
                    }
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            startActivityForResult(it.createOpenDocumentTreeIntent(), REQUEST_CODE_SD_CARD)
                        } else {
                            startActivityForResult(it.createAccessIntent(null), REQUEST_CODE_SD_CARD)
                        }
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
                ContextCompat.getExternalFilesDirs(this@FileTest, null/*DIRECTORY_PICTURES*/).forEach {
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

        addAction(object : Action("test Not Exist File") {
            override fun run() {
                val file = File(Environment.getExternalStorageDirectory(), "test1008")
                // canRead() 和 canWrite() 在文件存在的情况下才可能返回 true
                Timber.d("test Not Exist File \n" +
                        "exist: ${file.exists()} \n" + "canRead: ${file.canRead()} canWrite: ${file.canWrite()}")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val path = File(externalMediaDirs[0].absolutePath, "test")
                    val statFs = StatFs(path.absolutePath)
                    Timber.d("exist: ${path.exists()} ${statFs.availableBlocksLong}")
                }
            }
        })

        addAction(object : Action("nothing") {
            override fun run() {
                val path: String? = null
                val file = File("")
                file.mkdir()
            }
        })
        addAction(object : Action("test getExternalMediaDirs") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    externalMediaDirs.forEach {
                        if (it == null) {
                            return@forEach
                        }
                        if (!it.exists()) {
//                            it.mkdirs()
                        }
                        Timber.d("test getExternalMediaDirs \n%s \n%s \n%s \n%s",
                                "path: ${it.absolutePath}",
                                "state:${Environment.getExternalStorageState(it)}",
                                "available: ${getAvailable(it.absolutePath)}",
                                "removable: ${Environment.isExternalStorageRemovable(it)}")
                    }
                }
            }
        })

        addAction(object : Action("可用空间") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val externalMediaDir = externalMediaDirs[0]
                    if (!externalMediaDir.exists()){
                        externalMediaDir.mkdirs()
                    }
                    var dir = externalMediaDir
                    while (dir != null){
                        Timber.d("${dir.absolutePath} 可用空间: ${FileUtils.getFsAvailableSize(dir.absolutePath)}\n freeSpace ${dir.freeSpace}")
                        dir = dir.parentFile

                        val file = File(Environment.getExternalStorageDirectory(), "14.txt")
                        if (!file.exists()){
                            file.createNewFile()
                        }
                        // StatFs 获取可用空间大小要求传入的路径必须是存在的，可以是文件夹，也可以是文件
                        Timber.d("${file.absolutePath} ${file.exists()} 可用空间: ${FileUtils.getFsAvailableSize(file.absolutePath)}\n freeSpace ${file.freeSpace}")
                    }
                }
            }
        })

        addAction(object : Action("File API") {
            override fun run() {
                val file = File(Environment.getDownloadCacheDirectory(), "1.txt")
                Timber.d("file.name: ${file.name}") // 会包含扩展名
                Timber.d("file.name: ${File("${Environment.getDownloadCacheDirectory()}${File.separator}").name}")
            }
        })

        addAction(object : Action("observe") {
            override fun run() {
                observeMediaFileChanged()
            }
        })

        addAction(object : Action("delete") {
            override fun run() {
                val file = File(Environment.getExternalStorageDirectory(), "1.txt")
                if (!file.exists()){
                    file.createNewFile()
                }
                val fileWriter = FileWriter(file.absolutePath)
                fileWriter.write("0")
                fileWriter.flush()
                val result = file.delete()
                Timber.d("FileTest delete result: $result")
            }
        })
    }

    private fun observeMediaFileChanged() {
        contentResolver.registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, object :ContentObserver(null){
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                Timber.d("FileTest.onChange")
            }

            override fun onChange(selfChange: Boolean, uri: Uri?) {
                super.onChange(selfChange, uri)
                // 回调有点慢，可能有个 10s 的延迟
                Timber.d("FileTest.onChange ${uri?.toString()}")
            }
        })
    }

    private fun logStorageInfo() {
        addAction(object : Action("log info") {
            override fun run() {
                // 格式化之后 SD 卡名称会变
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val mediaDirs = externalMediaDirs
                    mediaDirs.forEach {
                        Timber.d("mediaDir : ${it.absolutePath}")
                    }
                }
            }
        })
    }

    fun getAvailable(path: String): Long {
        if (TextUtils.isEmpty(path)) {
            return -1
        }
        val file = File(path)
        if (!file.exists()) {
            return -1
        }
        val statFs = StatFs(path)
        return statFs.blockSizeLong * statFs.availableBlocksLong
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
            Timber.d("FileActivity.onActivityResult $resultCode ${data?.data}")
        }
    }
}
