package cn.dozyx.template.hardware.camera

import android.Manifest
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.Bundle
import android.view.TextureView
import android.view.ViewGroup
import com.tbruyelle.rxpermissions2.RxPermissions
import cn.dozyx.template.base.BaseTestActivity
import io.reactivex.disposables.Disposable

/**
 * Create by dozyx on 2019/3/25
 **/
class CameraControlActivity : BaseTestActivity(), TextureView.SurfaceTextureListener {
    override fun initActions() {

    }

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

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        stopPreview()
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        showPreview()
    }

}