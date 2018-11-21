package com.dozeboy.android.template

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.dozeboy.android.core.utli.util.ColorUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_root.setBackgroundColor(ColorUtil.random())
        text.setOnClickListener { ActivityUtils.startActivity(this@MainActivity.javaClass) }
    }
}
