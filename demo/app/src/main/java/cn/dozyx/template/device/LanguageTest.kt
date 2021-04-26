package cn.dozyx.template.device

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.webkit.WebView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.os.LocaleListCompat
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.LanguageUtils
import timber.log.Timber
import java.util.*

class LanguageTest : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        Timber.d("LanguageTest.onCreate ${Locale.getDefault().language}")
        super.onCreate(savedInstanceState)
//        Timber.d("LanguageTest.onCreate ${Locale.getDefault().language}")
//        Timber.d("LanguageTest.onCreate ${Arrays.toString(resources.assets.locales)}")
    }

    override fun initActions() {
        addAction(object : Action("default") {
            override fun run() {
                Timber.d("Locale.getDefault(): ${Locale.getDefault()}")
                // LocaleList.getDefault()：
                // 包含 Locale.getDefault() 返回的 locale，但不确保在第一位。
                // Locale.setDefault() 被调用会导致 localelist 改变
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Timber.d("LocaleList.getDefault() ${LocaleList.getDefault()}\n" +
                            "applicationContext.resources.configuration.locales: ${applicationContext.resources.configuration.locales}\n" +
                            "resources.configuration.locale: ${resources.configuration.locale}\n" +
                            "resources.configuration.locales: ${resources.configuration.locales}\n" +
                            "Resources.getSystem().configuration.locale ${Resources.getSystem().configuration.locale}\n" +
                            "Resources.getSystem().configuration.locales ${Resources.getSystem().configuration.locales}\n" +
                            "LocaleListCompat.getAdjustedDefault() ${LocaleListCompat.getAdjustedDefault()}" +
                            "\nresources.configuration.uiMode ${resources.configuration.uiMode }")
                }
                // 这个方法返回的应该是系统和应用支持 locale 的并集，AssetManager 有一个 api getNonSystemLocales() 返回的应该只包含应用支持的 locale，不过这个 api 是 hide 的
//                Timber.d("resources.assets.locales ${Arrays.toString(resources.assets.locales)}")
            }
        })
        addAction(object : Action("update language") {
            override fun run() {
                var oldConfig = resources.configuration
//                Timber.d("LanguageTest.run before ${resources.configuration.locales}")
                oldConfig.locale = Locale("pt")
//                oldConfig.setLocales(LocaleList.getDefault())// setLocales 会同时改变 locale
//                oldConfig.locale = null
//                oldConfig.setLocales(null)
//                oldConfig = Configuration()
//                createConfigurationContext(oldConfig)
                resources.updateConfiguration(oldConfig, resources.displayMetrics)
                applicationContext.resources.updateConfiguration(oldConfig, resources.displayMetrics)

                Timber.d("LanguageTest.run ${Locale.getDefault().language}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Timber.d("LanguageTest.run ${applicationContext.resources.configuration.locales}")
                    Timber.d("LanguageTest.run locale ${resources.configuration.locale}")
                    Timber.d("LanguageTest.run locales ${resources.configuration.locales}")
                }
            }
        })
        addAction(object : Action("locale setDefault") {
            override fun run() {
                Locale.setDefault(Locale("en"))
                Timber.d("LanguageTest.run ${Locale.getDefault().language}")
            }
        })
        addAction(object : Action(getString(R.string.test)) {
            override fun run() {
                Timber.d("LanguageTest.run ${Locale.getDefault().language}")
            }
        })

        addAction(object : Action("launch activity") {
            override fun run() {
                startActivity(Intent(this@LanguageTest, LanguageTest::class.java))
            }
        })

        addAction(object : Action("enable debug") {
            override fun run() {
                try {
                    val debugConfigurationField = Class.forName("android.app.ActivityThread").getDeclaredField("DEBUG_CONFIGURATION")
                    debugConfigurationField.isAccessible = true
                    debugConfigurationField.setBoolean(null, true)
                    Timber.d("LanguageTest.run " + debugConfigurationField.getBoolean(null))
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        })

        addAction(object : Action("webview") {
            override fun run() {
                // 会导致多语言失效
                // 在初始化完 WebView 之后再设置一次语言的话可以正常
                val start = System.currentTimeMillis()
                WebView(this@LanguageTest)
                Timber.d("new WebView(): ${(System.currentTimeMillis() - start)}")
            }
        })

        addAction(object : Action("destory webview") {
            override fun run() {
                WebView(this@LanguageTest).destroy()
            }
        })

        addAction(object : Action("override") {
            override fun run() {
                var oldConfig = resources.configuration
                oldConfig.locale = Locale("pt")
                applyOverrideConfiguration(oldConfig)
            }
        })

        addAction(object : Action("Blankj") {
            override fun run() {
                LanguageUtils.applyLanguage(Locale("pt"))
            }
        })

        addAction(object : Action("ui mode") {
            override fun run() {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        })

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("LanguageTest.onConfigurationChanged")
    }

    override fun attachBaseContext(newBase: Context?) {
        /*val context = LanguageUtil.attachBaseContext(newBase, 1)
        val configuration = context.resources.configuration
        val wrappedContext: ContextThemeWrapper = object : ContextThemeWrapper(context,
                R.style.Theme_AppCompat_Empty) {
            override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
                overrideConfiguration?.setTo(configuration)
                super.applyOverrideConfiguration(overrideConfiguration)
            }
        }
        super.attachBaseContext(wrappedContext)*/
        super.attachBaseContext(newBase)
    }
}