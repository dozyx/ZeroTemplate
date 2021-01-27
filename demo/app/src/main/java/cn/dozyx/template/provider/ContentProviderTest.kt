package cn.dozyx.template.provider

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import cn.dozyx.template.BuildConfig
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ContentProviderTest : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentResolver.registerContentObserver(Uri.parse("content://${AUTHORITY}"), false, object : ContentObserver(Handler()) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                Timber.d("ContentProviderTest.onChange")
            }
        })
    }
    override fun initActions() {
        addAction(object : Action("查询") {
            override fun run() {
                val uriBuilder = Uri.Builder()
                // Uri 的 scheme 必须是 content://
                // Uri 错误是找不到相应的 provider 的
//                uriBuilder.scheme("content")
//                        .authority("cn.dozyx.template.provide.book")// authority 包括 host 和 port
                val cursor = contentResolver.query(Uri.parse("content://${AUTHORITY}///111"),
                        arrayOf("projection1", "projection2"), null, null, "funny")
                cursor?.apply {
                    Timber.d("ContentProviderTest ${cursor.isFirst}")
                    if (!cursor.isFirst) {
                        cursor.moveToFirst()
                    }
                    do {
                        Timber.d("ContentProviderTest query result ${cursor.getLong(0)} ${cursor.getString(1)}")
                    } while (cursor.moveToNext())
                    close()
                }
            }
        })
    }

    companion object {
        private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.book"
    }
}