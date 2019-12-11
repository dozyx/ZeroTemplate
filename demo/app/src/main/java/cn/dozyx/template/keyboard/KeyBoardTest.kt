package cn.dozyx.template.keyboard

import android.os.Bundle
import android.view.WindowManager


import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_key_board.*

/**
 * Create by timon on 2019/12/11
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
