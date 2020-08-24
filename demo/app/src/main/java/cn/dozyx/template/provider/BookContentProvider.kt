package cn.dozyx.template.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import timber.log.Timber
import java.util.*

/**
 * 除了 onCreate，另外 5 个方法运行在 Binder 线程池中
 * @author dozyx
 * @date 2019-11-05
 */
class BookContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        Log.d("Dozyx", "BookContentProvider.onCreate ${Thread.currentThread()}")
        // 初始化
        // 所有在清单文件中注册的 content provider，在 application 启动时都会在主线程回调这个方法。
        // 不要在这里执行耗时操作
        // 返回 true 表示 provider 成功加载
        return false // 返回 false 似乎也没啥影响 https://stackoverflow.com/questions/13813598/what-happen-if-return-false-in-oncreate-of-contentprovider
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        Timber.d("BookContentProvider.query ${Thread.currentThread()} $uri ${Arrays.toString(projection)} $sortOrder")
        // uri: 请求的完整的 uri
        // projection: 哪些列要添加到 cursor 中
        // selection: 用来过滤某些行，如果为 null，返回所有的行
        // selectionArgs: 如果在 selection 中存在 ?，selectionArgs 将替换这些 ?(这个其实得看实现吧，不一定非得按数据库查询来使用，其实自己想怎么处理应该都可以)
        // sortOrder: 返回数据的排序
        val matrixCursor = MatrixCursor(projection)
        matrixCursor.addRow(arrayOf(1, 2L))
        matrixCursor.addRow(arrayOf(3, 4))
        return matrixCursor // 返回的 cursor 保存了所有查询到的数据
    }

    override fun getType(uri: Uri): String? {
        Timber.d("BookContentProvider.getType ${Thread.currentThread()} $uri")
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Timber.d("BookContentProvider.insert ${Thread.currentThread()} $uri")
        return null
    }

    override fun delete(uri: Uri, selection: String?,
                        selectionArgs: Array<String>?): Int {
        Timber.d("BookContentProvider.delete ${Thread.currentThread()} $uri")
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        Timber.d("BookContentProvider.update ${Thread.currentThread()} $uri")
        return 0
    }
}
