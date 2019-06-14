package com.zerofate.template.inject

import android.os.Bundle
import com.zerofate.template.base.BaseTestActivity

class DaggerTestActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("启动", Runnable {

        })
    }
}



