package cn.dozyx.template.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import okio.ByteString.Companion.decodeHex
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SQLiteTest : BaseTestActivity() {
    val executors: ExecutorService = Executors.newFixedThreadPool(100)

    private val dbHelper = CustomDatabaseHelper(this)
    private val countDownLatch = CountDownLatch(100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initActions() {
        addAction(object : Action("多线程") {
            override fun run() {
                for (i in 0..100) {
                    Thread{
                        countDownLatch.countDown()
                        countDownLatch.await()
                        // 在多线程中使用不同的 SQLiteOpenHelper 实例，会导致线程问题
                        // 原因：getWritableDatabase() 有同步处理 synchronized (this)，但是这个锁对象是 helper 实例，
                        // 多个线程调用不同 helper 实例的 getWritableDatabase() 方法时，导致 onCreate 调用了多次，这样就出来了表已创建的问题
                        queryNewHelper()
//                        query()
                    }.start()
                }
            }
        })

        addAction(object : Action("插入") {
            override fun run() {
                val contentValues = ContentValues()
                contentValues.put(COLUMN_ID, 0)
                contentValues.put(COLUMN_AGE, 18)
                contentValues.put(COLUMN_NAME, "www.youtube")
                val title =
                    "\uD835\uDC73\uD835\uDC86\uD835\uDC95'\uD835\uDC94 \uD835\uDC6E\uD835\uDC86\uD835\uDC95 \uD835\uDC73\uD835\uDC90\uD835\uDC96\uD835\uDC85 - \uD835\uDC6A\uD835\uDC89\uD835\uDC82-\uD835\uDC6A\uD835\uDC89\uD835\uDC82 (\uD835\uDC69\uD835\uDC82\uD835\uDC8D\uD835\uDC8D\uD835\uDC93\uD835\uDC90\uD835\uDC90\uD835\uDC8E - \uD835\uDC73\uD835\uDC82\uD835\uDC95\uD835\uDC8A\uD835\uDC8F \uD835\uDC7A\uD835\uDC95\uD835\uDC9A\uD835\uDC8D\uD835\uDC86)"
                Timber.d("SQLiteTest.run: $title")
                contentValues.put(COLUMN_TITLE, title)
                contentValues.put(COLUMN_CREATED_TIME, System.currentTimeMillis().toString())
                dbHelper.writableDatabase.insert(TABLE_NAME, null, contentValues)
                contentValues.put(COLUMN_ID, 1)
                contentValues.put(COLUMN_AGE, 18)
                contentValues.put(COLUMN_NAME, "youtube")
                Timber.d("SQLiteTest.run sub(0, 50): ${title.substring(0, 50)}")
                contentValues.put(COLUMN_TITLE, title.substring(0, 50))
                contentValues.put(COLUMN_CREATED_TIME, System.currentTimeMillis().toString())
                contentValues.put(COLUMN_ID, 2)
                contentValues.put(COLUMN_AGE, 18)
                contentValues.put(COLUMN_NAME, "myoutube")
                val title3 = "😄"
                Timber.d("SQLiteTest.run sub(0, 50): ${title3.substring(0, 1)}")
                contentValues.put(COLUMN_TITLE, title3.substring(0, 1))
                contentValues.put(COLUMN_CREATED_TIME, System.currentTimeMillis().toString())
                dbHelper.writableDatabase.insert(TABLE_NAME, null, contentValues)
            }

            val sqlSaved = "\uD835\uDC73\uD835\uDC86\uD835\uDC95'\uD835\uDC94 \uD835\uDC6E\uD835\uDC86\uD835\uDC95 \uD835\uDC73\uD835\uDC90\uD835\uDC96\uD835\uDC85 - \uD835\uDC6A\uD835\uDC89\uD835\uDC82-\uD835\uDC6A\uD835\uDC89\uD835\uDC82 (\uD835\uDC69\uD835\uDC82\uD835\uDC8D?";
            val origin = "\uD835\uDC73\uD835\uDC86\uD835\uDC95'\uD835\uDC94 \uD835\uDC6E\uD835\uDC86\uD835\uDC95 \uD835\uDC73\uD835\uDC90\uD835\uDC96\uD835\uDC85 - \uD835\uDC6A\uD835\uDC89\uD835\uDC82-\uD835\uDC6A\uD835\uDC89\uD835\uDC82 (\uD835\uDC69\uD835\uDC82\uD835\uDC8D�";
        })

        addAction(object : Action("查询") {
            override fun run() {
                query()
            }
        })
    }

    private fun query() {
        val filter = "you"
        val cursor = dbHelper.writableDatabase
                .query(TABLE_NAME, null, "$COLUMN_NAME LIKE ? OR $COLUMN_NAME LIKE ?", arrayOf("$filter%", "%.$filter%"),
                        null, null, "$COLUMN_CREATED_TIME DESC", null)
        Timber.d("SQLiteTest.run result count ${cursor.count}")
        if (cursor.moveToFirst()) {
            do {
                Timber.d("SQLiteTest.run " +
                        "${cursor.getString(cursor.getColumnIndex(COLUMN_NAME))}" +
                        "${cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))}")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun queryNewHelper() {
        //
        val filter = "you"
        val cursor = CustomDatabaseHelper(this).writableDatabase
                .query(TABLE_NAME, null, "$COLUMN_NAME LIKE ? OR $COLUMN_NAME LIKE ?", arrayOf("$filter%", "%.$filter%"),
                        null, null, "$COLUMN_CREATED_TIME DESC", null)
        Timber.d("SQLiteTest.run result count ${cursor.count}")
        if (cursor.moveToFirst()) {
            do {
                Timber.d("SQLiteTest.run ${cursor.getString(cursor.getColumnIndex(COLUMN_NAME))}")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CREATED_TIME = "created_time"

        const val DB_NAME = "student.db"
        const val TABLE_NAME = "student"

        const val VERSION = 1
    }

    class CustomDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            Timber.d("CustomDatabaseHelper.onCreate")
            db.execSQL("""
                CREATE TABLE $TABLE_NAME(
                  $COLUMN_ID INTEGER PRIMARY KEY,
                  $COLUMN_NAME TEXT,
                  $COLUMN_AGE INTEGER,
                  $COLUMN_TITLE TEXT,
                  $COLUMN_CREATED_TIME TEXT
                )
            """.trimIndent())
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }
    }

}
