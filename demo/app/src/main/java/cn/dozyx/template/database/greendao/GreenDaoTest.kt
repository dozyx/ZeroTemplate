package cn.dozyx.template.database.greendao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import org.greenrobot.greendao.database.Database
import timber.log.Timber
import kotlin.random.Random

/**
 * Kotlin 中调用 greenDao 的生成类会提示找不到 https://stackoverflow.com/questions/41507389/unresolved-reference-daosession-using-greendao-and-kotlin
 * 额外指定 sourceSets 的方法有效
 *
 * green dao 的 gradle plugin 不支持多个 schema，按我的理解是不支持单独根据某一个数据库的版本。。。
 * https://greenrobot.org/greendao/documentation/modelling-entities/
 * Note that multiple schemas are currently not supported when using the Gradle plugin.
 *
 * 关于如何使用多 schema https://greenrobot.org/greendao/documentation/generator/
 * 似乎新版本没有 Schema。。。
 *
 * @author dozyx
 * @date 2020/6/23
 */
class GreenDaoTest : BaseTestActivity() {
    lateinit var daoSession: DaoSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val openHelper = object : DaoMaster.DevOpenHelper(this, "green_dao") {
            override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                super.onUpgrade(db, oldVersion, newVersion)
                Timber.d("DevOpenHelper.onUpgrade2 old:${oldVersion} new:${newVersion}")
            }

            override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
                super.onUpgrade(db, oldVersion, newVersion)
                Timber.d("DevOpenHelper.onUpgrade1 old:${oldVersion} new:${newVersion}")
            }
        }
        // DaoMaster 管理所有可用的 DAO 对象
        // 需要注意耗时
        daoSession = DaoMaster(openHelper.writableDatabase).newSession()// 初始化得到 DaoSession，全局只需要一个
        // 注意：DevOpenHelper 会在 schema 改变时丢弃所有的表格（在 onUpgrade(）方法中）。建议自行实现 DaoMaster.OpenHelper 类。生产环境不要用 DevOpenHelper
    }

    override fun initActions() {
        addAction(object : Action("insert") {
            override fun run() {
                val entityDao = daoSession.greenDaoEntityDao
                val greenDaoEntity = GreenDaoEntity()
                greenDaoEntity.intValue = Random(100).nextInt()
                greenDaoEntity.stringValue = "🐂"
                greenDaoEntity.boolValue = false
                val insertId = entityDao.insert(greenDaoEntity)
                Timber.d("GreenDaoTest insert id: $insertId")
            }
        })

        addAction(object : Action("query") {
            override fun run() {
                val greenDaoEntityDao = daoSession.greenDaoEntityDao
                Timber.d(
                    "query: ${arrayOf(
                        greenDaoEntityDao.queryRaw(
                            ""
                        )
                    ).contentToString()}"
                )
            }
        })

        addAction(object : Action("update") {
            override fun run() {

            }
        })

        addAction(object : Action("custom helper") {
            override fun run() {
                val db = CustomOpenHelper(this@GreenDaoTest).writableDatabase
                val daoSession = DaoMaster(db).newSession()
                daoSession.greenDaoEntity2Dao.insert(GreenDaoEntity2("1000", "2000"))
            }
        })

        addAction(object : Action("schema") {
            override fun run() {

            }
        })
    }

    class CustomOpenHelper(context: Context) : DaoMaster.OpenHelper(context, "custom_helper") {
        override fun onCreate(db: Database?) {
            super.onCreate(db)
        }

        override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
            super.onUpgrade(db, oldVersion, newVersion)
            Timber.d("CustomOpenHelper.onUpgrade1 old:${oldVersion} new:${newVersion}")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            super.onUpgrade(db, oldVersion, newVersion)
            // Entity 增加字段，需要更新版本，并在这里处理
            Timber.d("CustomOpenHelper.onUpgrade2 old:${oldVersion} new:${newVersion}")
        }
    }

}