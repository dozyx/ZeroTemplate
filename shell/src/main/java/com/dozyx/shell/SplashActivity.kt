package com.dozyx.shell

import android.content.Intent
import android.os.Bundle
import com.dozeboy.android.template.R
import com.dozyx.shell.base.BaseShellActivity
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber

class SplashActivity : BaseShellActivity() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text_welcome.setOnClickListener {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
        Timber.d("SplashActivity.onCreate")
    }
}
