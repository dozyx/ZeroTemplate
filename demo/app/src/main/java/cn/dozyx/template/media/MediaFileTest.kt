package cn.dozyx.template.media

import android.Manifest
import android.annotation.SuppressLint
import android.media.MediaScannerConnection
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.FileUtils
import timber.log.Timber
import java.io.File

@SuppressLint("LogNotTimber")
class MediaFileTest : BaseTestActivity() {
    private val permissionLauncher = registerForActivityResult(RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun initActions() {
        addAction("ext mime") {
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("webm")
            Log.d(TAG, "MediaFileTest.initActions: $mimeType")
        }

        addAction("MediaStore") {
            scanAllAudio()
            scanAllVideo()
        }

        addAction("scan") {
//            val newPath = FILE_PATH.replace("webm", "mp4")
            val newPath = "/storage/emulated/0/output11.webm"
//            val renameResult = File(FILE_PATH).renameTo(File(newPath))
            val mimeType = getMimeType(newPath)
            Timber.d("mime type: $mimeType")
//            Log.d(TAG, "rename result: $renameResult")
            scanFile(newPath, mimeType)
        }
    }

    private fun scanAllAudio() {
        contentResolver.query(
            AUDIO_URI,
            null,
            null,
            null,
            MediaStore.Audio.Media.DATE_ADDED
        )?.apply {
            while (moveToNext()) {
                val path = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val mimeType = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE))
                Log.d(TAG, "MediaFileTest.scanAllAudio path: $path mimeType: $mimeType")
            }
            close()
        }
    }

    private fun scanAllVideo() {
        contentResolver.query(
            VIDEO_URI,
            null,
            null,
            null,
            MediaStore.Audio.Media.DATE_ADDED
        )?.apply {
            while (moveToNext()) {
                val path = getString(getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                val mimeType = getString(getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE))
                Log.d(TAG, "MediaFileTest.scanAllVideo path: $path mimeType: $mimeType")
            }
            close()
        }
    }

    private fun scanFile(filePath: String, mimeType: String?) {
        MediaScannerConnection.scanFile(
            this,
            arrayOf(filePath),
            arrayOf(mimeType)
        ) { path, uri ->
            Timber.d("onScanCompleted path: $path uri: $uri")
        }
    }

    private fun getMimeType(filePath: String) = MimeTypeMap.getSingleton()
        .getMimeTypeFromExtension(FileUtils.getFileExtension(filePath))

    companion object {
        private const val TAG = "MediaFileTest"
        val AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    }
}