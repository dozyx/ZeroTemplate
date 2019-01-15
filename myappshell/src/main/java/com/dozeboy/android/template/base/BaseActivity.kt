package com.dozeboy.android.template.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.dozeboy.android.template.R

/**
 * @author dozeboy
 * @date 2018/12/6
 */
open abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        findViewById<Toolbar>(R.id.toolbar)?.apply {
            setSupportActionBar(this)
        }
    }
}