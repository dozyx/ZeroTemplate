package cn.dozyx.template.database.room

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.os.Bundle
import android.os.storage.StorageManager
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.dozyx.core.utli.executor.get
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ImageUtils
import com.nostra13.universalimageloader.utils.StorageUtils
import timber.log.Timber
import java.util.*
import java.util.concurrent.ExecutorService
import kotlin.concurrent.thread

/**
 * Create by dozyx on 2019/5/17
 */
class RoomActivity : BaseTestActivity() {
    private var appDatabase: AppDatabase? = null
    private var executorService: ExecutorService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executorService = get()
//        addButton("创建", Runnable {
        Timber.d("RoomActivity.onCreate database create start")
        appDatabase = createDatabase()
        Timber.d("RoomActivity.onCreate database create end")
        Timber.d("RoomActivity.onCreate dao create start")
        val userDao = appDatabase?.userDao()
        Timber.d("RoomActivity.onCreate dao create end")
//        })
        addButton("添加数据", Runnable {
            executorService!!.execute {
                if (appDatabase != null) {
                    val user = generateFakeUser()
                    appDatabase!!.userDao().insertAll(user)
                }
            }
        })
        addButton("多线程使用多 database 实例", Runnable {
            // 多线程访问不同实例容易出现 SQLiteDatabaseLockedException
            Thread {
                Timber.d("thread1 start")
                val database = createDatabase()
                for (i in 0..10000) {
                    val user = generateFakeUser()
                    appDatabase?.userDao()?.insertAll(user)
                }
                Timber.d("thread1 end")
            }.start()
            Thread {
                Timber.d("thread2 start")
                val database = createDatabase()
                for (i in 0..10000) {
                    val user = generateFakeUser()
                    appDatabase?.userDao()?.delete(user)
                }
                Timber.d("thread2 end")
            }.start()
        })

        addButton("添加大量数据", Runnable {
            for (i in 0..10){
                thread {
                    for (i in 0..1000) {
                        val user = generateFakeUser(100000)
                        Timber.d("插入数据 $i ${user.uid}")
//                        user.avatar = ImageUtils.drawable2Bytes(ContextCompat.getDrawable(this, R.drawable.bg_0), Bitmap.CompressFormat.PNG)
                        try {
                            appDatabase?.userDao()?.insertAll(user)
                        } catch (e: Exception) {
                            Timber.d("catch exception $e")
                        }
                    }
                }
            }
        })

        addButton("添加数据集合", Runnable {
            for (i in 0..10){
                thread {
                    val users = mutableListOf<User>()
                    for (i in 0..1000) {
                        val user = generateFakeUser(100000)
                        users.add(user)
                    }
                    Timber.d("$i 插入数据集合开始 ${users.size}")
                    appDatabase?.userDao()?.insertAll(*users.toTypedArray())
                    Timber.d("$i 插入数据集合结束 ${users.size}")
                }
            }
        })

        addButton("读取数据", Runnable {
            thread {
                val users = appDatabase?.userDao()?.all
                users?.forEach { user ->
                    Timber.d("RoomActivity.onCreate: $user")
                }
            }
        })

        addButton("删除数据", Runnable {
//            for (i in 0..10){
                thread {
                    val users = appDatabase?.userDao()?.all
                    users?.forEachIndexed { index, user ->
                        if (index  == 0) {
                            Timber.d("删除数据 $index ${user.uid}")
                            appDatabase?.userDao()?.delete(user)
                        }
                    }
//                }
            }
        })
    }

    private fun createDatabase(): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java,
                "database-name").addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Timber.d("Callback RoomActivity.onCreate start")
                for (i in 0..100000) {
                    val user = generateFakeUser()
//                    db.insert("User", SQLiteDatabase.CONFLICT_REPLACE, user.toContentValue())
                }
                Timber.d("Callback RoomActivity.onCreate end")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Timber.d("Callback RoomActivity.onOpen")
            }
        }).build()
    }

    private fun generateFakeUser(idBound: Int = 1000): User {
        val user = User()
        user.uid = Random().nextInt(idBound)
        user.firstName = "三"
        user.lastName = "张"
//        LogUtils.d(user)
        return user
    }


    override fun initActions() {}
}