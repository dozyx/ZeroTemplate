package cn.dozyx.template.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Environment
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.FileUtils
import timber.log.Timber
import java.io.File

class SharedPreferenceTest : BaseTestActivity(),
        SharedPreferences.OnSharedPreferenceChangeListener {
    override fun initActions() {
        addAction(object : Action("add") {
            override fun run() {
                appendResult("add 1")
                sp.edit().putInt(TEST_KEY, 1).apply()
            }
        })
        addAction(object : Action("remove") {
            override fun run() {
                sp.edit().remove(TEST_KEY).apply()
            }
        })
        addAction(object : Action("change") {
            override fun run() {
                sp.edit().putInt(TEST_KEY, 2).apply()
            }
        })
        addAction(object : Action("read") {
            override fun run() {
                appendResult("current value: ${sp.getInt(TEST_KEY, 999)}")
            }
        })
        addAction(object : Action("put long") {
            override fun run() {
                sp.edit().putLong(TEST_KEY2, 999L).apply()
            }
        })
        addAction(object : Action("get int") {
            override fun run() {
                // crash
                appendResult("current value: ${sp.getInt(TEST_KEY2, 999)}")
            }
        })
        addAction(object : Action("read quickly") {
            override fun run() {

                for (i in 0..10) {
                    sp.edit().putString("test_1", "test_$i").apply()
                    Timber.d("SharedPreferenceTest.run ${sp.getString("test_1", "default")}")
                }
            }
        })

        addAction(object : Action("thread") {
            override fun run() {

                for (i in 0..10) {
                    sp.edit().putString("test_1", "test_$i").apply()
                    Timber.d("SharedPreferenceTest.run ${sp.getString("test_1", "default")}")
                }
            }
        })

        addAction(object : Action("speed_write") {
            override fun run() {
                val startTime = System.currentTimeMillis()
                val sharedPreferences = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                for (i in 0 until 1) {
                    editor.putString("key_$i", "value_$i").apply()
                }
                editor.apply()
                Timber.d("SharedPreferenceTest.run write time: ${System.currentTimeMillis() - startTime}")
            }
        })

        addAction(object : Action("speed_clear") {
            override fun run() {
                val sharedPreferences = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear().apply()
            }
        })
        addAction(object : Action("speed_first_read") {
            override fun run() {
                // sp 在读入内存时是在子线程中，但是因为有锁，所以在读的时候会阻塞等待
                val startTime = System.currentTimeMillis()
                val sharedPreferences = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
                val result = sharedPreferences.getString("key_1", "没有该值")
                Timber.d("SharedPreferenceTest.run read time: ${System.currentTimeMillis() - startTime} $result")
                // 小米 8
                // 200 个值，首次读大概花了 23ms，后面因为是从内存中读取，基本是在 1ms 可以读到值
                // 即使只有 10 个数据，也花了 6ms
                // 1个值的时候，数值变化有点大，2 到 6
            }
        })

        addAction(object : Action("file_create") {
            override fun run() {
                val startTime = System.currentTimeMillis()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val file = File(dataDir, "test")
                    if (!file.exists()){
                        file.createNewFile()
                    }
                }
                Timber.d("SharedPreferenceTest.create read time: ${System.currentTimeMillis() - startTime}")
            }
        })
        addAction(object : Action("file_exist") {
            override fun run() {
                val startTime = System.currentTimeMillis()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Timber.d("SharedPreferenceTest.exist: ${File(dataDir, "test").exists()}")
                }
                Timber.d("SharedPreferenceTest.exist read time: ${System.currentTimeMillis() - startTime}")
                // 判断文件是否存在，大概要 2ms
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp.registerOnSharedPreferenceChangeListener(this)
    }


    private val sp: SharedPreferences
        get() = getSharedPreferences("pref", Context.MODE_PRIVATE)

    companion object {
        const val TEST_KEY = "test_key"
        const val TEST_KEY2 = "test_key2"

        const val SP_FILE_NAME = "test_read_speed";
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        /*appendResult(
            "change key: " + key + " & newValue: " + sp.getInt(key, 100)
        )*/
    }
}