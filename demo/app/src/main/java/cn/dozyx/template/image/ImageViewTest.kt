package cn.dozyx.template.image

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_image_view.*

class ImageViewTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iv.isEnabled = false
        iv.isSelected = true
        iv.isActivated = true
    }
    override fun getLayoutId() = R.layout.test_image_view
}