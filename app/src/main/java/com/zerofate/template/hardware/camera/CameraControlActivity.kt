package com.zerofate.template.hardware.camera

import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.dozeboy.android.core.utli.CameraUtil
import com.dozeboy.android.core.utli.log.ZLog
import com.zerofate.template.base.BaseTestActivity
import kotlinx.android.synthetic.main.activity_base_test.*


class CameraControlActivity : BaseTestActivity() {

    private val numberOfCameras = Camera.getNumberOfCameras()
    private var camera: Camera? = null
    private var currentCameraId = 0
    private lateinit var surfaceView: SurfaceView
    private lateinit var holder: SurfaceHolder
    private var previewSizes: List<Camera.Size>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        surfaceView = SurfaceView(this)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500)
        surfaceView.layoutParams = params
        addView(surfaceView)

        holder = surfaceView.holder.apply {
            addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

                }

                override fun surfaceDestroyed(holder: SurfaceHolder?) {
                }

                override fun surfaceCreated(holder: SurfaceHolder?) {
                }
            })
            setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        }

        currentCameraId = getDefaultCameraId()
        addButton("预览", Runnable {
            safeCameraOpen(currentCameraId)
            setCamera(camera)
        })
        addButton("切换摄像头", Runnable {
            if (numberOfCameras == 1) {
                return@Runnable
            }
            camera?.let {
                it.stopPreview()
                it.release()
            }
            currentCameraId = (currentCameraId + 1) % numberOfCameras
            camera = Camera.open(currentCameraId)
            camera?.let {

            }
        })
    }

    private fun safeCameraOpen(id: Int): Boolean {
        return try {
            releaseCameraAndPreview()
            camera = Camera.open(id)
            camera?.let {
                CameraUtil.setCameraDisplayOrientation(this, id, it)
            }
            true
        } catch (e: Exception) {
            ZLog.e(e)
            false
        }
    }

    private fun releaseCameraAndPreview() {
        camera = null
        camera?.also { camera ->
            camera.release()
            this.camera = null
        }
    }

    private fun setCamera(camera: Camera?) {
//        stopPreviewAndFreeCamera()
        this.camera = camera
        camera?.apply {
            previewSizes = parameters.supportedPreviewSizes
            try {
                setPreviewDisplay(holder)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            startPreview()
            setPreviewCallback(object : Camera.PreviewCallback {
                override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
                    camera?.run {
                        data?.let { val bitmap = CameraUtil.previewFrameToBitmap(it, this)
                            runOnUiThread{
                                image_view.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
            })
        }
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
