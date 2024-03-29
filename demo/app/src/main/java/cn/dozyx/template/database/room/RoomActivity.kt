package cn.dozyx.template.database.room

import android.os.Bundle
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.dozyx.core.utli.executor.get
import cn.dozyx.template.base.BaseTestActivity
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.ExecutorService
import kotlin.collections.ArrayList
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
            for (i in 0..10) {
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
        addButton("批量添加数据", Runnable {
            val users = mutableListOf<User>()
            thread {
                for (j in 0..1000) {
                    val user = generateFakeUser(100000)
                    users.add(user)
                }
                try {
                    appDatabase?.userDao()?.insertAll(users)
                } catch (e: Exception) {
                    Timber.d("catch exception $e")
                }
            }
        })

        addButton("添加数据集合", Runnable {
            for (i in 0..10) {
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
                    if (index == 0) {
                        Timber.d("删除数据 $index ${user.uid}")
                        appDatabase?.userDao()?.delete(user)
                    }
                }
//                }
            }
        })

        val allObservable = appDatabase?.userDao()?.allObservable
        addButton("flowable", Runnable {
            allObservable?.subscribeOn(Schedulers.io())?.subscribe {
                Timber.d("flowable: ${it.size}")
            }
        })

    }

    private fun createDatabase(): AppDatabase {
        val roomDb = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java,
            "database-name"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // 并不是在 build 的时候就回调，而是在正式访问读写数据库时调用
                Timber.d("Callback RoomActivity.onCreate start ${Thread.currentThread()}")
                for (i in 0..100000) {
                    val user = generateFakeUser()
//                    db.insert("User", SQLiteDatabase.CONFLICT_REPLACE, user.toContentValue())
                }

//                appDatabase?.userDao()?.insertAll(generateFakeUser()) // 会导致 onCreate 被递归调用
                Timber.d("Callback RoomActivity.onCreate end")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Timber.d("Callback RoomActivity.onOpen")
            }
        }).build()
        return roomDb
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