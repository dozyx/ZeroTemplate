package cn.dozyx.template.gl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.AttributeSet
import android.view.View

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import cn.dozyx.core.base.BaseActivity
import timber.log.Timber

class OpenGLES20Activity : BaseActivity() {
    private var glSurfaceView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int {
        return 0
    }

    override fun getLayoutView(): View? {
        glSurfaceView = CustomGlSurfaceView(this)
        return glSurfaceView
    }

    class CustomGlSurfaceView(context: Context) : GLSurfaceView(context) {
        private val renderer: CustomRenderer

        init {
            setEGLContextClientVersion(2)
            renderer = CustomRenderer()
            setRenderer(renderer)
        }
    }

    class CustomRenderer : GLSurfaceView.Renderer {
        lateinit var triangle: Triangle
        lateinit var square: Square
        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            Timber.d("CustomRenderer.onSurfaceCreated")
            GLES20.glClearColor(0f, 0f, 0f, 1f)
            triangle = Triangle()
            square = Square()
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            Timber.d("CustomRenderer.onSurfaceChanged")
            GLES20.glViewport(0, 0, width, height)
        }

        override fun onDrawFrame(gl: GL10) {
//            Timber.d("CustomRenderer.onDrawFrame")
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
            triangle.draw()
        }
    }
}
