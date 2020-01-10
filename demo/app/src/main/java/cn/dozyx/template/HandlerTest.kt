package cn.dozyx.template

import android.os.Bundle
import android.os.Handler
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * @author dozyx
 * @date 2020-01-05
 */
class HandlerTest : BaseTestActivity() {
    private val handler = Handler(Handler.Callback {
        // 利用 Callback 可以 hook Handler 的消息
        Timber.d("HandlerTest.handleMessage $it")
        false
    })

    override fun initActions() {
        addAction(object : Action("点击") {
            override fun run() {
                Timber.d("HandlerTest.run")
                handler.sendEmptyMessage(1)
            }
        })
    }
}