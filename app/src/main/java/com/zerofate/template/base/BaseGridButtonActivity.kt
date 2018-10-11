package com.zerofate.template.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button

import com.zerofate.template.R
import kotlinx.android.synthetic.main.main_grid_button.*

abstract class BaseGridButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_grid_button)
    }

    protected fun addButton(text: String, task: Runnable) {
        val button = Button(this)
        button.text = text
        flexbox.addView(button)
    }

    protected fun appendLog(log: String) {
        var previousLog = text_log!!.text as String
        if (TextUtils.isEmpty(previousLog)) {
            previousLog = ""
        }
        val builder = StringBuilder()
        builder.append(log).append("\n").append(previousLog)
        text_log!!.text = builder.toString()
    }


}
