package cn.dozyx.template.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import org.greenrobot.greendao.database.DatabaseOpenHelper
import timber.log.Timber

class SQLiteTest : BaseTestActivity() {
    private val dbHelper = CustomDatabaseHelper(this)
    override fun initActions() {
        addAction(object : Action("创建") {
            override fun run() {

            }
        })

        addAction(object : Action("插入") {
            override fun run() {
                val contentValues = ContentValues()
                contentValues.put(COLUMN_ID, 0)
                contentValues.put(COLUMN_AGE, 18)
                contentValues.put(COLUMN_NAME, "www.youtube")
                contentValues.put(COLUMN_CREATED_TIME, System.currentTimeMillis().toString())
                dbHelper.writableDatabase.insert(TABLE_NAME, null, contentValues)
                contentValues.put(COLUMN_ID, 1)
                contentValues.put(COLUMN_AGE, 18)
                contentValues.put(COLUMN_NAME, "youtube")
                contentValues.put(COLUMN_CREATED_TIME, System.currentTimeMillis().toString())
                contentValues.put(COLUMN_ID, 2)
                contentValues.put(COLUMN_AGE, 18)
                contentValues.put(COLUMN_NAME, "myoutube")
                contentValues.put(COLUMN_CREATED_TIME, System.currentTimeMillis().toString())
                dbHelper.writableDatabase.insert(TABLE_NAME, null, contentValues)
            }
        })

        addAction(object : Action("查询") {
            override fun run() {
                val filter = "you"
                val cursor = dbHelper.writableDatabase
                        .query(TABLE_NAME, null, "$COLUMN_NAME LIKE ? OR $COLUMN_NAME LIKE ?", arrayOf("$filter%", "%.$filter%"),
                                null, null, "$COLUMN_CREATED_TIME DESC", null)
                Timber.d("SQLiteTest.run result count ${cursor.count}")
                if (cursor.moveToFirst()) {
                    do {
                        Timber.d("SQLiteTest.run ${cursor.getString(cursor.getColumnIndex(COLUMN_NAME))}")
                    } while (cursor.moveToNext())
                }
            }
        })
    }

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_CREATED_TIME = "created_time"

        const val DB_NAME = "student.db"
        const val TABLE_NAME = "student"

        const val VERSION = 1
    }

    class CustomDatabaseHelper(context: Context) : DatabaseOpenHelper(context, DB_NAME, VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            Timber.d("CustomDatabaseHelper.onCreate")
            db.execSQL("""
                CREATE TABLE $TABLE_NAME(
                  $COLUMN_ID INTEGER PRIMARY KEY,
                  $COLUMN_NAME TEXT,
                  $COLUMN_AGE INTEGER,
                  $COLUMN_CREATED_TIME TEXT
                )
            """.trimIndent())
        }

    }

}
