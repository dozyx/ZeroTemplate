package cn.dozyx.template

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * @author dozyx
 * @date 2020/4/3
 */
class IntentTest : BaseTestActivity() {

    companion object {
        const val URI_INTENT =
                "intent://baidu.com/web?webtype=common#Intent;scheme=zxing;package=com.google.zxing.client.android;S.browser_fallback_url=http%3A%2F%2Fzxing.org;end";
    }

    override fun initActions() {
        addAction(object : Action("解析") {
            override fun run() {
                val intent = Intent.parseUri(URI_INTENT, Intent.URI_INTENT_SCHEME)
                Timber.d("IntentTest.origin $URI_INTENT")
                Timber.d("IntentTest.intent $intent")
                Timber.d("IntentTest.extras ${intent.extras}")
                Timber.d("IntentTest.data ${intent.data}")
                Timber.d("IntentTest.data ${intent.data?.getQueryParameter("webtype")}")
            }
        })

        addAction(object :Action("启动"){
            override fun run() {
//                val intentString = "intent://larkgame.com?utm_source=sp_settings&web_type=common#Intent;scheme=https;package=com.snaptube.premium;S.title=测试;end;"
                val intentString = "intent://snaptubeapp.com/web?utm_source=sp_settings&web_type=common#Intent;scheme=https;package=com.snaptube.premium;S.title=测试;S.url=https://www.baidu.com;end;"
                startActivity(Intent.parseUri(Uri.parse(intentString).toString(), Intent.URI_INTENT_SCHEME))
            }
        })

        addAction(object :Action("ACTION_SEND"){
            override fun run() {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "测试"))
                val resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                resolveInfos.forEachIndexed { index, resolveInfo ->
                    Timber.d("IntentTest.run $index $resolveInfo")
                }
            }
        })
        addAction(object : Action("Chooser") {
            override fun run() {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "coder")

                val chooserIntent = Intent.createChooser(intent, "标题")
                chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        })
    }

}