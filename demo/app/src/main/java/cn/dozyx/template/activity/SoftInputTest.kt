package cn.dozyx.template.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import cn.dozyx.template.bug.bug5497.AndroidBug5497Workaround1
import kotlinx.android.synthetic.main.test_soft_input.*

class SoftInputTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AndroidBug5497Workaround1.assistActivity(this)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        btn_full.setOnClickListener {
            // 设置全屏之后，adjustResize 会失效
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        btn_launch.setOnClickListener {
            startActivity(Intent(this, SoftInputTest::class.java))
        }
        btn_finish.setOnClickListener {
            finish()
        }
    }

    override fun getLayoutId() = R.layout.test_soft_input
}