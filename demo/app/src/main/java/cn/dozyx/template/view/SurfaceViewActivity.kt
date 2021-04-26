package cn.dozyx.template.view

import android.os.Bundle
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_surface.*
import timber.log.Timber

class SurfaceViewActivity : BaseActivity(), SurfaceHolder.Callback {
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Timber.d("SurfaceViewActivity.surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Timber.d("SurfaceViewActivity.surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Timber.d("SurfaceViewActivity.surfaceCreated")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sv.holder.addCallback(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_surface
    }
}

