package com.zerofate.template

import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import androidx.core.content.ContextCompat
import com.zerofate.template.base.BaseTestActivity
import java.util.*

class FileActivity : BaseTestActivity() {

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
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES): ${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}
        """.trimIndent())
    }
}
