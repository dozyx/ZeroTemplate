package cn.dozyx.template.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.FileUtils
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
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        /*appendResult(
            "change key: " + key + " & newValue: " + sp.getInt(key, 100)
        )*/
    }
}