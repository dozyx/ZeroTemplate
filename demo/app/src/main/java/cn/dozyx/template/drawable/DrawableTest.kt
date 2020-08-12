package cn.dozyx.template.drawable

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_drawable.*

class DrawableTest : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val originalColor = "#FFCD22"
    val defaultGrey = "#a3202124"
    iv_mock_notification_small_icon.setColorFilter(Color.parseColor(originalColor), PorterDuff.Mode.SRC_ATOP)
  }

  override fun getLayoutId() = R.layout.test_drawable
}