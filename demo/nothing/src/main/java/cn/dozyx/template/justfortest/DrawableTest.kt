package cn.dozyx.template.justfortest

import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.os.Bundle
import android.widget.SeekBar
import cn.dozyx.core.base.BaseActivity
import kotlinx.android.synthetic.main.test_drawable.*

class DrawableTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawable = image.drawable
        if (drawable is LayerDrawable) {
            val clipDrawable = drawable.getDrawable(1)
            if (clipDrawable is ClipDrawable) {
                clipDrawable.level = 7000
            }
        }

        sb_scale_level.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                changeScaleLevel(progress)
            }
        })
    }

    private fun changeScaleLevel(level: Int) {
        val drawable2 = image_scale.drawable
        if (drawable2 is LayerDrawable) {
            val scaleDrawable = drawable2.getDrawable(1)
            if (scaleDrawable is ScaleDrawable) {
                scaleDrawable.level = level * 100
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.test_drawable
    }
}