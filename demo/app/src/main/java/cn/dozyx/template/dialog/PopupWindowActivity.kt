package cn.dozyx.template.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity

class PopupWindowActivity : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button = addButton("显示", Runnable { })
        setButtonOnClickListener(button, Runnable {
            val window = PopupWindow(LayoutInflater.from(this@PopupWindowActivity).inflate(R.layout.popup_window, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.elevation = 50f
            // 好像不能设置 modal，有一个 setTouchModal 方法，不过被标记为 @hide
            window.isFocusable = true
            window.setBackgroundDrawable(ColorDrawable())
//            window.height = window.getMaxAvailableHeight(button)
//            window.overlapAnchor = true // 显示在 anchor 上方还是下方
            window.showAsDropDown(button)
        })
    }
}

class MyPopupWindow(val context: Context) : PopupWindow(context) {
    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_window, null)
    }

}
