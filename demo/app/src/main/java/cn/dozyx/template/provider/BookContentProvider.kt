package cn.dozyx.template.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import timber.log.Timber

/**
 * 除了 onCreate，另外 5 个方法运行在 Binder 线程池中
 * @author dozyx
 * @date 2019-11-05
 */
class BookContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        Timber.d("BookContentProvider.onCreate ${Thread.currentThread()}")
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        Timber.d("BookContentProvider.query ${Thread.currentThread()}")
        return null
    }

    override fun getType(uri: Uri): String? {
        Timber.d("BookContentProvider.getType ${Thread.currentThread()}")
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Timber.d("BookContentProvider.insert ${Thread.currentThread()}")
        return null
    }

    override fun delete(uri: Uri, selection: String?,
                        selectionArgs: Array<String>?): Int {
        Timber.d("BookContentProvider.delete ${Thread.currentThread()}")
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        Timber.d("BookContentProvider.update ${Thread.currentThread()}")
        return 0
    }
}
