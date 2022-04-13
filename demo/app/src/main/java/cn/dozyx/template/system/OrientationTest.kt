package cn.dozyx.template.system

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class OrientationTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("竖屏") {
            override fun run() {
                val intent = Intent(this@OrientationTest, LandscapeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
        })

        addAction(object : Action("Dialog") {
            override fun run() {
                CustomDialog(this@OrientationTest).show()
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("OrientationTest.onConfigurationChanged $newConfig")
    }

    private class CustomDialog(context: Context) :
        Dialog(context, R.style.Theme_AppCompat_Light_Dialog) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_orientation_test)
            Timber.d("CustomDialog.onCreate")
        }
    }

}