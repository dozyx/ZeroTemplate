/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zerofate.template.hardware.camera

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.hardware.Camera.Size
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

import java.io.IOException

// Need the following import to get access to the app resources, since this
// class is in a sub-package.

// ----------------------------------------------------------------------

class CameraPreview : Activity() {
    private var mPreview: Preview? = null
    internal var mCamera: Camera? = null
    internal var numberOfCameras: Int = 0
    internal var cameraCurrentlyLocked: Int = 0

    // The first rear facing camera
    internal var defaultCameraId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Create a RelativeLayout container that will hold a SurfaceView,
        // and set it as the content of our activity.
        mPreview = Preview(this)
        setContentView(mPreview)

        // Find the total number of cameras available
        numberOfCameras = Camera.getNumberOfCameras()

        // Find the ID of the default camera
        val cameraInfo = CameraInfo()
        for (i in 0 until numberOfCameras) {
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i
            }
        }

        mPreview?.setOnClickListener{
            if (numberOfCameras == 1) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("只有一个摄像头")
                        .setNeutralButton("Close", null)
                val alert = builder.create()
                alert.show()
                return@setOnClickListener
            }

            // OK, we have multiple cameras.
            // Release this camera -> cameraCurrentlyLocked
            if (mCamera != null) {
                mCamera!!.stopPreview()
                mPreview!!.setCamera(null)
                mCamera!!.release()
                mCamera = null
            }

            // Acquire the next camera and request Preview to reconfigure
            // parameters.
            mCamera = Camera
                    .open((cameraCurrentlyLocked + 1) % numberOfCameras)
            cameraCurrentlyLocked = (cameraCurrentlyLocked + 1) % numberOfCameras
            mPreview!!.switchCamera(mCamera)

            // Start the preview
            mCamera!!.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()

        // Open the default i.e. the first rear facing camera.
        mCamera = Camera.open()
        cameraCurrentlyLocked = defaultCameraId
        mPreview!!.setCamera(mCamera)
    }

    override fun onPause() {
        super.onPause()

        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (mCamera != null) {
            mPreview!!.setCamera(null)
            mCamera!!.release()
            mCamera = null
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {

        // Inflate our menu which can gather user input for switching camera
        val inflater = menuInflater
        inflater.inflate(R.menu.camera_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.switch_cam -> {
                // check for availability of multiple cameras
                if (numberOfCameras == 1) {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(this.getString(R.string.camera_alert))
                            .setNeutralButton("Close", null)
                    val alert = builder.create()
                    alert.show()
                    return true
                }

                // OK, we have multiple cameras.
                // Release this camera -> cameraCurrentlyLocked
                if (mCamera != null) {
                    mCamera!!.stopPreview()
                    mPreview!!.setCamera(null)
                    mCamera!!.release()
                    mCamera = null
                }

                // Acquire the next camera and request Preview to reconfigure
                // parameters.
                mCamera = Camera
                        .open((cameraCurrentlyLocked + 1) % numberOfCameras)
                cameraCurrentlyLocked = (cameraCurrentlyLocked + 1) % numberOfCameras
                mPreview!!.switchCamera(mCamera)

                // Start the preview
                mCamera!!.startPreview()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }*/
}

// ----------------------------------------------------------------------

/**
 * A simple wrapper around a Camera and a SurfaceView that renders a centered preview of the Camera
 * to the surface. We need to center the SurfaceView because not all devices have cameras that
 * support preview sizes at the same aspect ratio as the device's display.
 */
internal class Preview(context: Context) : ViewGroup(context), SurfaceHolder.Callback {
    private val TAG = "Preview"

    var mSurfaceView: SurfaceView
    var mHolder: SurfaceHolder
    var mPreviewSize: Size? = null
    var mSupportedPreviewSizes: List<Size>? = null
    var mCamera: Camera? = null

    init {

        mSurfaceView = SurfaceView(context)
        addView(mSurfaceView)

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.holder
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    fun setCamera(camera: Camera?) {
        mCamera = camera
        if (mCamera != null) {
            mSupportedPreviewSizes = mCamera!!.parameters.supportedPreviewSizes
            requestLayout()
        }
    }

    fun switchCamera(camera: Camera?) {
        setCamera(camera)
        try {
            camera!!.setPreviewDisplay(mHolder)
        } catch (exception: IOException) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception)
        }

        val parameters = camera!!.parameters
        parameters.setPreviewSize(mPreviewSize!!.width, mPreviewSize!!.height)
        requestLayout()

        camera.parameters = parameters
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // We purposely disregard child measurements because act as a
        // wrapper to a SurfaceView that centers the camera preview instead
        // of stretching it.
        val width = View.resolveSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = View.resolveSize(suggestedMinimumHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)

        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed && childCount > 0) {
            val child = getChildAt(0)

            val width = r - l
            val height = b - t

            var previewWidth = width
            var previewHeight = height
            if (mPreviewSize != null) {
                previewWidth = mPreviewSize!!.width
                previewHeight = mPreviewSize!!.height
            }

            // Center the child SurfaceView within the parent.
            // 使图像居中显示
            if (width * previewHeight > height * previewWidth) {
                val scaledChildWidth = previewWidth * height / previewHeight
                child.layout((width - scaledChildWidth) / 2, 0,
                        (width + scaledChildWidth) / 2, height)
            } else {
                val scaledChildHeight = previewHeight * width / previewWidth
                child.layout(0, (height - scaledChildHeight) / 2,
                        width, (height + scaledChildHeight) / 2)
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        try {
            if (mCamera != null) {
                mCamera!!.setPreviewDisplay(holder)
            }
        } catch (exception: IOException) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception)
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            mCamera!!.stopPreview()
        }
    }


    private fun getOptimalPreviewSize(sizes: List<Size>?, w: Int, h: Int): Size? {
        // 可以接受的比例误差
        val ASPECT_TOLERANCE = 0.1
        // 显示比例
        val targetRatio = w.toDouble() / h
        if (sizes == null) return null

        var optimalSize: Size? = null
        var minDiff = java.lang.Double.MAX_VALUE

// Try to find an size match aspect ratio and size
        // 找出最适合的比例和大小
        for (size in sizes) {
            val ratio = size.width.toDouble() / size.height
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue
            if (Math.abs(size.height - h) < minDiff) {
                optimalSize = size
                minDiff = Math.abs(size.height - h).toDouble()
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        // 没有满足条件的比例，忽略
        if (optimalSize == null) {
            minDiff = java.lang.Double.MAX_VALUE
            // 找出与显示高度最接近的预置大小
            for (size in sizes) {
                if (Math.abs(size.height - h) < minDiff) {
                    optimalSize = size
                    minDiff = Math.abs(size.height - h).toDouble()
                }
            }
        }
        return optimalSize
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        val parameters = mCamera!!.parameters
        parameters.setPreviewSize(mPreviewSize!!.width, mPreviewSize!!.height)
        requestLayout()

        mCamera!!.parameters = parameters
        mCamera!!.startPreview()
    }

}
