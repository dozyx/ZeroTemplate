package cn.dozyx.template.view

import android.os.Bundle
import android.widget.SeekBar
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.ex.dp
import cn.dozyx.core.utli.dp
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_card_view.*

class CardViewTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_elevation.setOnClickListener {
            val text = et_elevation.text.toString()
            text.toFloatOrNull()?.let {
                cardView.cardElevation = it.dp
            }
        }

        sb_elevation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tv_current_elevation.setText("$progress")
                cardView.cardElevation = progress.dp
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun getLayoutId() = R.layout.test_card_view
}