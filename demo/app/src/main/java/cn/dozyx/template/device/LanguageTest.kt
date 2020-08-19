package cn.dozyx.template.device

import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
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
                Timber.d("LocaleList.getDefault() ${LocaleList.getDefault()}")

                Timber.d("applicationContext.resources.configuration.locales: ${applicationContext.resources.configuration.locales}")
                Timber.d("resources.configuration.locale: ${resources.configuration.locale}")
                Timber.d("resources.configuration.locales: ${resources.configuration.locales}")
                Timber.d("Resources.getSystem().configuration.locale ${Resources.getSystem().configuration.locale}")
                Timber.d("Resources.getSystem().configuration.locales ${Resources.getSystem().configuration.locales}")
                // 这个方法返回的应该是系统和应用支持 locale 的并集，AssetManager 有一个 api getNonSystemLocales() 返回的应该只包含应用支持的 locale，不过这个 api 是 hide 的
//                Timber.d("resources.assets.locales ${Arrays.toString(resources.assets.locales)}")
            }
        })
        addAction(object : Action("update language") {
            override fun run() {
                var oldConfig = resources.configuration
//                Timber.d("LanguageTest.run before ${resources.configuration.locales}")
//                oldConfig.locale = Locale("pt")
//                oldConfig.setLocales(LocaleList.getDefault())// setLocales 会同时改变 locale
                oldConfig.locale = null
//                oldConfig.setLocales(null)
//                oldConfig = Configuration()
//                resources.updateConfiguration(oldConfig, resources.displayMetrics)
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

    }
}