package cn.dozyx.template.view

import android.os.Bundle
import android.widget.ArrayAdapter
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_auto_complete.*

/**
 * AutoCompleteTextView
 * 建议列表从 data adapter 中获取
 * 当匹配的字符数量超过 threshold 时，显示建议
 */
class AutoCompleteTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES)
        auto_text.apply {
            setAdapter(adapter)
            threshold = 1
        }

    }

    override fun getLayoutId() = R.layout.test_auto_complete

    companion object {
        private val COUNTRIES = arrayListOf("Belgium", "France", "Italy", "Germany", "Spain")
    }
}