package cn.dozyx.template.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_view_event.*
import timber.log.Timber

class ViewEventTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testKeyEvent()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Timber.d("ViewEventTest.onBackPressed")
    }

    private fun testKeyEvent() {
        Handler(Looper.getMainLooper()).post {
            setViewFocus(window.decorView)// 不能接收 key event
            setViewFocus(btn1)
            setViewFocus(btn2)
//            setViewFocus(tv1)
            // 只有当前焦点的 view 会接收到 key event
        }
        window.decorView.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                Timber.d("decor view on back")
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        btn1.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                Timber.d("btn1 on back")
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        btn2.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                Timber.d("btn2 on back")
                return@setOnKeyListener false
            }
            return@setOnKeyListener false
        }

        tv1.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                Timber.d("tv1 on back")
//                return@setOnKeyListener true // 返回 true 的话，Activity#onBackPressed 不会再被触发
                return@setOnKeyListener false
            // 返回 false 的话，会触发 Activity#onBackPressed。
            // 如果给多个 view 设置了 OnKeyListener，最后一个返回 false，那么还是不会触发前一个，而是交给了 Activity。
            }
            return@setOnKeyListener false
        }
    }

    fun setViewFocus(view: View) {
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
    }

    override fun getLayoutId() = R.layout.test_view_event
}