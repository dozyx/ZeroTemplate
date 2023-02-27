package cn.dozyx.template.keyboard


import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_key_board.*

/**
 * Create by dozyx on 2019/12/11
 */
class KeyBoardTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn.setOnClickListener {
//            KeyboardUtils.hideSoftInput(this)
            finish()
        }
        edit.requestFocus()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_key_board
    }
}
