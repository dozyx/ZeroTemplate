package cn.dozyx.template.webview

import android.webkit.WebSettings
import android.webkit.WebView
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class NewWebViewTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("user agent") {
            override fun run() {
                Timber.d("WebSettings.getDefaultUserAgent start")
                Timber.d("WebSettings.getDefaultUserAgent %s", WebSettings.getDefaultUserAgent(this@NewWebViewTest))// 小米 8 240ms 好像跟 new 一个 WebView 耗时没多大区别
                Timber.d("WebSettings.getDefaultUserAgent end")
                Timber.d("new WebView() start")
                Timber.d("new WebView() %s", WebView(this@NewWebViewTest).settings.userAgentString)
                Timber.d("new WebView() end")
            }
        })
    }

}