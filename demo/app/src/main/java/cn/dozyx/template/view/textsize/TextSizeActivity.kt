package cn.dozyx.template.view.textsize

import android.os.Bundle
import android.widget.SeekBar
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_text_size.*

class TextSizeActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_text_size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                text1.textSize = progress.toFloat()
                text2.textSize = progress.toFloat()
                text3.textSize = progress.toFloat()
                text_current.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}