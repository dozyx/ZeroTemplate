package com.zerofate.template

import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import androidx.core.content.ContextCompat
import com.zerofate.template.base.BaseTestActivity

class FileActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("public picture 目录", Runnable {
            appendResult(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).absolutePath)
        })
        addButton("data 目录", Runnable {
            appendResult(Environment.getDataDirectory().absolutePath)
        })
        addButton("storage 目录", Runnable {
            appendResult(Environment.getExternalStorageDirectory().absolutePath)
        })
        addButton("download cache 目录", Runnable {
            appendResult(Environment.getDownloadCacheDirectory().absolutePath)
        })
        addButton("app cache 目录", Runnable {
            appendResult(this@FileActivity.cacheDir.absolutePath)
        })

        addButton("app data 目录", Runnable {
            appendResult(ContextCompat.getDataDir(this@FileActivity)?.absolutePath ?: "无")
        })

        addButton("app files 目录", Runnable {
            appendResult(this@FileActivity.filesDir.absolutePath)
        })
    }
}
