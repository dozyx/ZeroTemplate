package cn.dozyx.template.activity

import android.app.Activity
import android.app.Instrumentation
import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * @author dozyx
 * @date 2020/7/30
 */
class ActivityTest : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testField()
        val instrumentation = reflectField<Instrumentation>("mInstrumentation")
        val mActivityMonitors = reflectField<List<Instrumentation.ActivityMonitor>>(
            "mActivityMonitors",
            instrumentation!!.javaClass,
            instrumentation
        )
        Timber.d("ActivityTest.onCreate ${mActivityMonitors?.size}")
    }

    private fun testField() {
        reflectField<Activity>("mParent")
        reflectField<Activity>("mEmbeddedID")
    }

    private fun <T> reflectField(fieldName: String): T? {
        return reflectField<T>(fieldName, javaClass, this)
    }


    private fun <T> reflectField(fieldName: String, clazz: Class<*>, obj: Any): T? {
        var clz: Class<in Any>? = javaClass
        while (clz != null) {
            try {
                val field = clz.getDeclaredField(fieldName)
                field.isAccessible = true
                val value = field.get(this)
                Timber.d("reflectField $clz $value")
                return value as T?
            } catch (e: Exception) {
                //                Timber.d("ActivityTest.onCreate $e")
            }
            clz = clz.superclass
        }
        return null
    }

    override fun initActions() {
    }
}