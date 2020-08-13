package cn.dozyx.template.device

import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber
import java.util.*

class LanguageTest : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("LanguageTest.onCreate ${Locale.getDefault().language}")
        super.onCreate(savedInstanceState)
        Timber.d("LanguageTest.onCreate ${Locale.getDefault().language}")
    }

    override fun initActions() {
        addAction(object : Action("locale default") {
            override fun run() {
                Timber.d("LanguageTest.run ${Locale.getDefault().language}")
            }
        })
    }
}