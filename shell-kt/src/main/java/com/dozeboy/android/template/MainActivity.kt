package com.dozeboy.android.template

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.dozeboy.android.template.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDrawer()
    }

    override fun getLayoutId(): Int = R.layout.activity_main
    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }
}
