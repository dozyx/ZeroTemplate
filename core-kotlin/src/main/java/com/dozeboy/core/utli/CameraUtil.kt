package com.dozeboy.core.utli

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.Camera
import android.view.Surface
import java.io.ByteArrayOutputStream

class CameraUtil {
    companion object {
        fun setCameraDisplayOrientation(activity: Activity, cameraId: Int, camera: Camera) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(cameraId, info)
            val rotation = activity.windowManager.defaultDisplay.rotation
            var rotationDegrees = 0
            when (rotation) {
                Surface.ROTATION_0 -> rotationDegrees = 0
                Surface.ROTATION_90 -> rotationDegrees = 90
                Surface.ROTATION_180 -> rotationDegrees = 180
                Surface.ROTATION_270 -> rotationDegrees = 270
            }
            var displayDegrees = 0
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                displayDegrees = (info.orientation + rotationDegrees) % 360
                displayDegrees = (360 - displayDegrees) % 360 // compensate the mirror
            } else {
                displayDegrees = (info.orientation - rotationDegrees + 360) % 360
            }
            camera.setDisplayOrientation(displayDegrees)
        }

        fun previewFrameToBitmap(data: ByteArray, camera: Camera): Bitmap {
            return camera.let {
                val width = it.parameters.previewSize.width
                val height = it.parameters.previewSize.height
                val yuv = YuvImage(data, it.parameters.previewFormat, width, height, null)
                val out = ByteArrayOutputStream()
                yuv.compressToJpeg(Rect(0, 0, width, height), 50, out)
                val bytes = out.toByteArray()
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        }
    }
}