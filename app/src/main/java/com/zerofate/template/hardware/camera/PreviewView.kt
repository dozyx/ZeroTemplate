package com.zerofate.template.hardware.camera

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import java.io.IOException

class PreviewView(context: Context) : ViewGroup(context), SurfaceHolder.Callback {
    private val TAG = "Preview"

    var mSurfaceView: SurfaceView = SurfaceView(context)
    var mHolder: SurfaceHolder
    var mPreviewSize: Camera.Size? = null
    var mSupportedPreviewSizes: List<Camera.Size>? = null
    var mCamera: Camera? = null

    init {

        addView(mSurfaceView)

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.holder
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    fun setCamera(camera: Camera?) {
        if (mCamera == camera){
            return
        }
        stopPreviewFreeCamera()
        mCamera = camera
        mCamera?.apply {
            mSupportedPreviewSizes = parameters.supportedPreviewSizes
            requestLayout()
            try {
                setPreviewDisplay(mHolder)
            } catch (e:IOException){
                e.printStackTrace()
            }
            startPreview()
        }
    }

    private fun stopPreviewFreeCamera() {
        mCamera?.apply {
            stopPreview()
            release()
            mCamera = null
        }
    }

    fun switchCamera(camera: Camera) {
        setCamera(camera)
        try {
            camera.setPreviewDisplay(mHolder)
        } catch (exception: IOException) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception)
        }

        val parameters = camera.parameters
        parameters.setPreviewSize(mPreviewSize!!.width, mPreviewSize!!.height)
        requestLayout()

        camera.parameters = parameters
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
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


    private fun getOptimalPreviewSize(sizes: List<Camera.Size>?, w: Int, h: Int): Camera.Size? {
        val ASPECT_TOLERANCE = 0.1
        val targetRatio = w.toDouble() / h
        if (sizes == null) return null

        var optimalSize: Camera.Size? = null
        var minDiff = java.lang.Double.MAX_VALUE

// Try to find an size match aspect ratio and size
        for (size in sizes) {
            val ratio = size.width.toDouble() / size.height
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue
            if (Math.abs(size.height - h) < minDiff) {
                optimalSize = size
                minDiff = Math.abs(size.height - h).toDouble()
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = java.lang.Double.MAX_VALUE
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
        mCamera?.apply {
            val newParam = parameters
            newParam.setPreviewSize(mPreviewSize!!.width, mPreviewSize!!.height)
            requestLayout()

            parameters = newParam
            startPreview()
        }

    }

}

