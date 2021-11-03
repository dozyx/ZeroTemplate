package cn.dozyx.lib.ui

import android.app.Activity
import android.os.Bundle
import cn.dozyx.lib.MySubComplileOnlyClass
import cn.dozyx.lib.R

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proguard)
        val clazz = MySubComplileOnlyClass()
        clazz.toString()
    }
}