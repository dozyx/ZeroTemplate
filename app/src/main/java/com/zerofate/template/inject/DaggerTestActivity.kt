package com.zerofate.template.inject

import android.os.Bundle
import com.zerofate.template.base.BaseGridButtonActivity
import javax.inject.Inject

class DaggerTestActivity : BaseGridButtonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("启动", Runnable {

        })
    }
}



