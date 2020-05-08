package cn.dozyx.template

import android.content.Intent
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * @author dozyx
 * @date 2020/4/3
 */
class IntentTest : BaseTestActivity() {

    override fun initActions() {
        addAction(object : Action("解析") {
            override fun run() {
                var intent = Intent.parseUri(URI_INTENT, Intent.URI_INTENT_SCHEME)
                Timber.d("IntentTest.origin $URI_INTENT")
                Timber.d("IntentTest.intent $intent")
                Timber.d("IntentTest.extras ${intent.extras}")
            }
        })
    }

    companion object {
        const val URI_INTENT =
            "intent://scan/#Intent;scheme=zxing;package=com.google.zxing.client.android;S.browser_fallback_url=http%3A%2F%2Fzxing.org;end";
    }
}