package com.zerofate.template.hardware.camera

import android.Manifest
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.Bundle
import android.view.TextureView
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zerofate.template.base.BaseTestActivity
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Create by timon on 2019/3/25
 **/
class CameraControlActivity : BaseTestActivity(), TextureView.SurfaceTextureListener {
    private lateinit var rxPermissions: RxPermissions;
    private val textureView: TextureView by lazy { TextureView(this) }
    private var camera: Camera? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(this)
        textureView.surfaceTextureListener = this
        textureView.layoutParams = ViewGroup.LayoutParams(640, 640)
        addView(textureView)
        addButton("预览", Runnable { showPreview() })
        addButton("聚焦", Runnable { camera?.autoFocus(Camera.AutoFocusCallback { success, camera -> }) })
        disposable = rxPermissions.request(Manifest.permission.CAMERA).subscribe { granted ->
            if (!granted) {
                finish()
            }
        }
    }

    private var disposable: Disposable? = null

    private fun showPreview() {
        camera = Camera.open()
        camera?.setPreviewTexture(textureView.surfaceTexture)
        camera?.startPreview()
    }

    private fun stopPreview() {
        camera?.stopPreview()
        camera?.release()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        stopPreview()
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        showPreview()
    }
}