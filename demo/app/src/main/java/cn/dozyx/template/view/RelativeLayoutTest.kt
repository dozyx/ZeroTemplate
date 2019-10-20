package cn.dozyx.template.view

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_relativelayout.*

/**
 * @author dozyx
 * @date 2019-10-20
 */
class RelativeLayoutTest :BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_relativelayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button.setOnClickListener{
            ToastUtils.showShort("点击")
        }
    }
}