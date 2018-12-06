package com.zerofate.template.hardware.camera

import android.hardware.Camera
import android.os.Bundle
import android.view.ViewGroup
import com.zerofate.template.base.BaseTestActivity

class CameraControlActivity : BaseTestActivity() {

    private val numberOfCameras = Camera.getNumberOfCameras();
    private var defaultCameraId: Int = 0
    private lateinit var previewView: PreviewView
    private var camera: Camera? = null
    private var currentCameraId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        previewView = PreviewView(this)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500)
        previewView.layoutParams = params
        addView(previewView)
        initDefaultCameraId()
        addButton("预览", Runnable {
            camera = Camera.open()
            previewView.setCamera(camera)
            currentCameraId = defaultCameraId
        })
        addButton("切换摄像头", Runnable {
            if (numberOfCameras == 1) {
                return@Runnable
            }
            camera?.let {
                it.stopPreview()
                it.release()
            }
            previewView.setCamera(null)
            currentCameraId = (currentCameraId + 1) % numberOfCameras
            camera = Camera.open(currentCameraId)
            camera?.let {
                previewView.switchCamera(it)
            }
        })
    }

    private fun initDefaultCameraId() {
        val cameraInfo = Camera.CameraInfo()
        for (i in 0 until numberOfCameras) {
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i
            }
        }
    }

    private fun releaseCameraAndPreview() {

    }


}
