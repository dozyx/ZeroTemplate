package com.zerofate.template.dialog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.widget.PopupWindowCompat
import com.zerofate.template.R
import com.zerofate.template.base.BaseTestActivity

class PopupWindowActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button = addButton("显示", Runnable {  })
        setButtonOnClickListener(button, Runnable {
            val window = PopupWindow(LayoutInflater.from(this@PopupWindowActivity).inflate(R.layout.popup_window, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.elevation = 50f
            // 好像不能设置 modal，有一个 setTouchModal 方法，不过被标记为 @hide
            window.showAsDropDown(button)
        })
    }
}

class MyPopupWindow(val context: Context) : PopupWindow(context) {
    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_window, null)
    }

}