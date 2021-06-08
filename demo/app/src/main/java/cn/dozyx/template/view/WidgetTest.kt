package cn.dozyx.template.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_widget_test.*

/**
 * 测试各种控件的使用
 */
class WidgetTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_test)
        testExtendedFloatButton()
    }

    private fun testExtendedFloatButton() {
        extend_float_button.setOnClickListener {
            if (extend_float_button.isExtended) {
                extend_float_button.shrink()
            } else {
                extend_float_button.extend()
            }
        }
    }
}