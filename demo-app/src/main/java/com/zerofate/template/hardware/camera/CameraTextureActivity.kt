package com.zerofate.template.hardware.camera

import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.Bundle
import android.view.TextureView
import androidx.appcompat.app.AppCompatActivity
import com.dozeboy.core.utli.CameraUtil
import com.dozeboy.core.utli.log.LogUtil
import com.zerofate.template.R
import kotlinx.android.synthetic.main.activity_texture_view.*

class CameraTextureActivity : AppCompatActivity(), TextureView.SurfaceTextureListener {

    private val numberOfCameras = Camera.getNumberOfCameras()
    private var camera: Camera? = null
    private var currentCameraId = 0
    private var showPreviewFrameFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_texture_view)
        currentCameraId = getDefaultCameraId()
        initEvent()
        texture_preview.surfaceTextureListener = this
    }

    private fun initEvent() {
        btn_start_preview.setOnClickListener {
            startPreview()
        }

        btn_stop_preview.setOnClickListener {
            stopPreview()
        }

        btn_switch_camera.setOnClickListener {
            switchCamera()
        }

        btn_show_preview_frame.setOnClickListener {
            showPreviewFrameFlag = true
        }
    }

    private fun stopPreview() {
        releaseCamera()
    }

    private fun switchCamera() {
        if (numberOfCameras == 1) {
            return
        }
        currentCameraId = (currentCameraId + 1) % numberOfCameras
        startPreview()
    }

    private fun startPreview() {
        safeCameraOpen(currentCameraId)
        camera?.apply {
            setPreviewTexture(texture_preview.surfaceTexture)
            startPreview()
            setPreviewCallback(object : Camera.PreviewCallback {
                override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
                    camera?.run {
                        data?.let {
                            if (showPreviewFrameFlag) {
                                val bitmap = CameraUtil.previewFrameToBitmap(it, this)
                                runOnUiThread {
                                    image_preview.setImageBitmap(bitmap)
                                }
                                showPreviewFrameFlag = false
                            }
                        }
                    }
                }
            })
        }
    }

    private fun safeCameraOpen(id: Int): Boolean {
        return try {
            releaseCamera()
            camera = Camera.open(id)
            camera?.let {
                CameraUtil.setCameraDisplayOrientation(this, id, it)
            }
            true
        } catch (e: Exception) {
            LogUtil.e(e)
            false
        }
    }

    private fun releaseCamera() {
        camera?.also { camera ->
            camera.stopPreview()
            camera.setPreviewCallback(null)
            camera.release()
        }
        camera = null
    }


    private fun stopPreviewAndFreeCamera() {
        if (camera == null) {
            return
        }
        camera?.apply {
            stopPreview()
            release()
            camera = null
        }

    }

    private fun getDefaultCameraId(): Int {
        val cameraInfo = Camera.CameraInfo()
        for (i in 0 until numberOfCameras) {
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return i
            }
        }
        return -1
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        LogUtil.d("CameraTextureActivity.onSurfaceTextureSizeChanged: ")
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        LogUtil.d("CameraTextureActivity.onSurfaceTextureUpdated: ")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        LogUtil.d("CameraTextureActivity.onSurfaceTextureDestroyed: ")
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        LogUtil.d("CameraTextureActivity.onSurfaceTextureAvailable: ")
    }
}