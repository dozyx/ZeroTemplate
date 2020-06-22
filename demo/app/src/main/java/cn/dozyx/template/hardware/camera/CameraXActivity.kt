package cn.dozyx.template.hardware.camera

import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.camerax_activity.*
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

/**
 * 库更新之后 API 有变动，代码先注释掉了
 * Create by dozyx on 2019/5/20
 */
private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)

class CameraXActivity : BaseActivity() {
    private lateinit var viewFinder: TextureView
    override fun getLayoutId(): Int = R.layout.camerax_activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewFinder = view_finder
        if (allPermissionGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> updateTransform() }
    }

    private fun startCamera() {
        /*val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setTargetResolution(Size(640, 640))
        }.build()
        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val imageCapureConfig = ImageCaptureConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
        }.build()
        val imageCapure = ImageCapture(imageCapureConfig)
        capture_button.setOnClickListener {
            val file = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
            imageCapure.takePicture(file, object : ImageCapture.OnImageSavedListener {
                override fun onError(useCaseError: ImageCapture.UseCaseError, message: String, cause: Throwable?) {
                    val msg = "Photo capture failed: $message"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    LogUtils.d(msg)
                    cause?.printStackTrace()
                }

                override fun onImageSaved(file: File) {
                    val msg = "Photo capture succeeded: ${file.absolutePath}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d("CameraXApp", msg)
                }
            })
        }

        val analysisConfig = ImageAnalysisConfig.Builder().apply {
            val analyserThread = HandlerThread("LuminosityAnalysis").apply {
                start()
            }
            setCallbackHandler(Handler(analyserThread.looper))
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
        }.build()

        val analyserUseCase = ImageAnalysis(analysisConfig).apply {
            analyzer = LuminosityAnalyzer()
        }

        CameraX.bindToLifecycle(this, preview, imageCapure, analyserUseCase)*/
    }

    private fun updateTransform() {
        val matrix = Matrix()
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
        viewFinder.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this, "permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}

private class LuminosityAnalyzer : ImageAnalysis.Analyzer {

    private var lastAnalyzedTimestamp = 0L
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    override fun analyze(image: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimestamp > TimeUnit.SECONDS.toMillis(1)) {
            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()
            LogUtils.d("Average luminosity: $luma")
            lastAnalyzedTimestamp = currentTimestamp
        }
    }
}
