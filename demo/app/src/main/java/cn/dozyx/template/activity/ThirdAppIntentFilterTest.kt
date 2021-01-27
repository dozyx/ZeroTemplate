package cn.dozyx.template.activity

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_empty.*
import timber.log.Timber

/**
 * 由其他 app 通过 intent 启动
 */
class ThirdAppIntentFilterTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("ThirdAppIntentFilterTest.onCreate ${intent.data?.path}")
        text.text = intent.dataString
    }

    override fun getLayoutId() = R.layout.activity_empty
}