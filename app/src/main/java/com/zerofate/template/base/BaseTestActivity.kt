package com.zerofate.template.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.zerofate.template.R
import kotlinx.android.synthetic.main.activity_base_test.*

/**
 * 添加按钮及其点击事件，显示日志，添加一个 fragment 页
 */
abstract class BaseTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_test)
    }

    protected fun addButton(text: String, task: Runnable?): Button {
        val button = Button(this)
        button.text = text
        button.setOnClickListener {
            task?.run()
        }
        flexbox.addView(button)
        return button
    }

    protected fun setButtonOnClickListener(view: View, task: Runnable) {
        view.setOnClickListener { task.run() }
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

    protected fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(frame_container.id, fragment).commit();
    }


}
