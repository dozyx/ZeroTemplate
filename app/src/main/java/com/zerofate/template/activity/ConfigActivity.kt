package com.zerofate.template.activity

import android.os.Bundle
import com.zerofate.template.base.BaseTestActivity

class ConfigActivity:BaseTestActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("orientation", Runnable {
            appendResult("orientation: ${resources.configuration.orientation}")
        })
        addButton("display orientation", Runnable {
            appendResult("display orientation: ${windowManager.defaultDisplay.rotation}")
        })
    }
}