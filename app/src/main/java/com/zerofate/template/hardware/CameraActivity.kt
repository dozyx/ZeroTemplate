package com.zerofate.template.hardware

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.ImageUtils
import com.zerofate.androidsdk.util.PrintUtil
import com.zerofate.androidsdk.util.VersionUtil

import com.zerofate.template.base.BaseTestActivity
import kotlinx.android.synthetic.main.activity_base_test.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : BaseTestActivity() {
    private var currentPicturePath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("拍照", Runnable {
            takePictureByIntent()
        })

        addButton("拍大照片", Runnable {
            takeFullSizePictureByIntent()
        })

        addButton("添加照片到图库", Runnable {
            // getExternalFilesDir 是 app 私有的目录，媒体扫描器不会主动进行扫描
            if (TextUtils.isEmpty(currentPicturePath)) {
                appendResult("照片路径为空")
                return@Runnable
            }
            galleryAddPic()
        })

        addButton("显示小图", Runnable {
            showScaledImage()
        })
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPicturePath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    private fun takePictureByIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 返回的是图片的缩略图
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun takeFullSizePictureByIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 返回的是图片的缩略图
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val file: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                appendResult("io exception")
                null
            }
            file?.also {
                val photoUri: Uri? = FileProvider.getUriForFile(this, "com.zerofate.template.FileProvider", it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // createTempFile 会自动生成一个名称
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        currentPicturePath = image.absolutePath
        appendResult("image path $currentPicturePath")
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        appendResult("requestCode: $requestCode resultCode: ${PrintUtil.printResultCode(resultCode)} $data")
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            image_view.setImageBitmap(data?.extras?.get("data") as Bitmap)
        } else if (requestCode == REQUEST_TAKE_PHOTO) {
            image_view.setImageURI(Uri.fromFile(File(currentPicturePath)))
        }
    }

    private fun showScaledImage() {
        val targetWidth = 200
        val targetHeight = 300
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(currentPicturePath, this)
            val photoWidth = outWidth
            val photoHeight = outHeight
            val scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        BitmapFactory.decodeFile(currentPicturePath, options)?.also {
            // 某些机型的照片会出现旋转
            var matrix = Matrix()
            matrix.postRotate(ImageUtils.getRotateDegree(currentPicturePath).toFloat())
            image_view.setImageBitmap(Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true))
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 0
        private const val REQUEST_TAKE_PHOTO = 1
    }
}
