package cn.dozyx.template.view.list

import android.os.Bundle
import android.os.Handler
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.test_swipe_refresh.*

class SwipeRefreshTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        swipe_refresh.setOnRefreshListener {
            Handler().postDelayed({
                swipe_refresh.isRefreshing = false
            }, 3000)
        }
        swipe_refresh.setDistanceToTriggerSync(SizeUtils.dp2px(100f))
    }

    override fun getLayoutId() = R.layout.test_swipe_refresh
}