package cn.dozyx.template.hardware.camera

import android.graphics.PixelFormat
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.core.utli.CameraUtil
import cn.dozyx.core.utli.log.LogUtil
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_surface_view.*


class CameraSurfaceViewActivity : AppCompatActivity() {

    private val numberOfCameras = Camera.getNumberOfCameras()
    private var camera: Camera? = null
    private var currentCameraId = 0
    private lateinit var holder: SurfaceHolder
    private var showPreviewFrameFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface_view)
        currentCameraId = getDefaultCameraId()
        initSurface()
        initEvent()
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
            setPreviewDisplay(holder)
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

    private fun initSurface() {
        holder = surface_preview.holder.apply {
            addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                    LogUtil.d("CameraSurfaceViewActivity.surfaceChanged: ")
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    LogUtil.d("CameraSurfaceViewActivity.surfaceDestroyed: ")
                }

                override fun surfaceCreated(holder: SurfaceHolder) {
                    LogUtil.d("CameraSurfaceViewActivity.surfaceCreated: ")
                }
            })
            setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        }
        // 将 SurfaceView 背景改为透明
        surface_preview.setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSPARENT)
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
}
