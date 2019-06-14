package cn.dozyx.shell.base

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import cn.dozyx.core.base.BaseActivity
import com.dozeboy.android.template.R

/**
 * @author dozeboy
 * @date 2018/12/6
 */
open abstract class BaseShellActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        findViewById<Toolbar>(R.id.toolbar)?.apply {
            setSupportActionBar(this)
        }
    }
}
