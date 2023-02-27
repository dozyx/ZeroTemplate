package cn.dozyx.template

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cn.dozyx.core.base.BaseFragment
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.IntentUtils
import kotlinx.android.synthetic.main.animation_test.*
import kotlinx.android.synthetic.main.fragment_edit.*
import timber.log.Timber
import java.io.File
import java.util.*

/**
 * @author dozyx
 * @date 2020/4/3
 */
class IntentTest : BaseTestActivity() {

    lateinit var editFragment: EditFragment

    companion object {
        //        const val URI_INTENT =
//                "intent://baidu.com/web?webtype=common#Intent;scheme=zxing;package=com.google.zxing.client.android;S.browser_fallback_url=http%3A%2F%2Fzxing.org;end";
        const val URI_INTENT =
            "https://docs.google.com/forms/d/1MnRQJ3XZxsRBSVp28M7Sm1M8Gm5jniE/viewform?entry.1040949360=*%7CFNAME%7C*&entry.271521054=*%7CLNAME%7C*";
    }

    override fun initActions() {
        addAction(object : Action("解析") {
            override fun run() {
                val intent = Intent.parseUri(URI_INTENT, Intent.URI_INTENT_SCHEME)
//                Timber.d("IntentTest.origin $URI_INTENT")
                Timber.d("IntentTest.intent $intent")
                Timber.d("IntentTest.extras ${intent.extras}")
                Timber.d("IntentTest.data ${intent.data}")
                Timber.d("IntentTest.data ${intent.data?.getQueryParameter("webtype")}")
            }
        })

        addAction(object : Action("启动") {
            override fun run() {
                val intentString = ""
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentString))
                val intent = Intent.parseUri(intentString, Intent.URI_INTENT_SCHEME)
//                intent.addCategory(Intent.CATEGORY_DEFAULT)
                startActivity(intent)
            }
        })

        addAction(object : Action("market") {
            override fun run() {
                val intentString = "market://details?id=com.xueqiu.android&referrer=utm_source%3Dst"
                val intent =
                    Intent.parseUri(Uri.parse(intentString).toString(), Intent.URI_INTENT_SCHEME)
                startActivity(intent)
            }
        })

        addAction(object : Action("ACTION_SEND") {
            override fun run() {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "https://youtu.be/E3RBFhyjjqU")
                startActivity(intent)
                val resolveInfos =
                    packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
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

        val launchIntent = IntentUtils.getLaunchAppIntent(packageName)
        addAction(object : Action("Pending Intent") {
            override fun run() {
                val pendingIntent = PendingIntent.getActivity(
                    this@IntentTest, 0,
                    launchIntent, PendingIntent.FLAG_CANCEL_CURRENT
                )
                Timber.d("IntentTest.run $pendingIntent")
            }
        })

        addAction(object : Action("Start Activity") {
            override fun run() {
                val intent = Intent("phoenix.intent.action.LOG_REPORT")
//                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.putExtra("report_from", "test")
                startActivity(intent)
            }
        })

        addAction(object : Action("Edit Intent") {
            override fun run() {
                val intent = Intent.parseUri(editFragment.getInput(), Intent.URI_INTENT_SCHEME)
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        })

        addAction(object : Action("print") {
            override fun run() {
                val intent = Intent("")
                intent.setDataAndType(Uri.fromFile(File("${externalCacheDir?.absolutePath}/1.txt")), "video/*")
                intent.setPackage(packageName)
                Timber.d(intent.toUri(Intent.URI_INTENT_SCHEME))
                val fileUri =
                    Uri.fromFile(File("${externalCacheDir?.absolutePath}/1.txt"))
                Timber.d(fileUri.toString())
                val uriIntent = Intent.parseUri(fileUri.toString(), 0)
                Timber.d(uriIntent.toUri(Intent.URI_INTENT_SCHEME))
                val intentFormat=""
                val intentUri = intentFormat.replace("{file_uri}", fileUri.toString())
                val formatIntent = Intent.parseUri(intentUri, 0)
                Timber.d(formatIntent.toUri(Intent.URI_INTENT_SCHEME))
                startActivity(formatIntent)

            }
        })

        addAction(object : Action("filter test") {
            override fun run() {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentString))
                val intent = Intent()
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                intent.addCategory(Intent.CATEGORY_APP_BROWSER)
                intent.data = Uri.parse("http://dozyx.com")
//                intent.setPackage(packageName)
//                intent.data = Uri.parse("content://dozyx.com")
                val resolveActivity = packageManager.resolveActivity(intent, 0)
                Timber.d("best: $resolveActivity")
                val activities = packageManager.queryIntentActivities(intent, 0)
                Timber.d("match size: ${activities.size}")
                activities.forEach {
                    Timber.d("filter: $it}")
                }
                resolveActivity?.let {
                    startActivity(intent)
                }
            }
        })

        addAction("pending") {
            val intent =
                PendingIntent.getBroadcast(this, 0, Intent("cn.dozyx.action.pending"), 0)
            NotificationCompat.Builder(this, NotificationChannelCompat.DEFAULT_CHANNEL_ID)
                .apply {
                    this.setSmallIcon(R.drawable.idlefish_ic_launcher)
                        .setContentTitle("标题")
                        .setContentText("内容")
                }.also {
                    NotificationManagerCompat.from(this).notify(1, it.build())
                }

        }
        editFragment = EditFragment()
        addFragment(editFragment)
    }

}

class EditFragment : BaseFragment() {
    fun getInput(): String {
        edit.text ?: return ""
        return edit.text.toString()
    }

    override fun getLayoutId() = R.layout.fragment_edit
}