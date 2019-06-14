package cn.dozyx.template.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.blankj.utilcode.util.ToastUtils
import cn.dozyx.zerofate.androidsdk.util.VersionUtil
import cn.dozyx.template.R
import cn.dozyx.template.util.Constants
import kotlinx.android.synthetic.main.activity_spinner_test.*

class SpinnerTestActivity : AppCompatActivity() {
    internal lateinit var window: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner_test)
        ButterKnife.bind(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Constants.sMessages)
        dialog_spinner.adapter = adapter
        dropdown_spinner.adapter = adapter
        dropdown_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                ToastUtils.showShort("onItemSelected: $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                ToastUtils.showShort("onNothingSelected")
            }
        }
        window = PopupWindow(this)
        popup.setOnClickListener {
            popup()
        }

        btn_toggle_selected.setOnClickListener {
            dropdown_spinner.isSelected = !dropdown_spinner.isSelected
        }

        btn_selection.setOnClickListener {
            dropdown_spinner.setSelection(-1)
        }
    }

    fun popup() {
        if (window.isShowing) {
            window.dismiss()
            return
        }
        val imageView = ImageView(this)
        imageView.setImageResource(R.mipmap.ic_launcher_round)
//        if (VersionUtil.hasLollipop()) {
//            window.elevation = 100f
//        }
        //
        //        window.setOverlapAnchor(true);
        window.isFocusable = true// 效果类似于modal,接收全部触摸事件，如果点击外围部分将隐藏
        window.isClippingEnabled = true// 是否允许超出屏幕，默认将摆放到边缘，为 flase 将允许 window 按精确位置放置（在这里验证时，不知道为什么只有垂直方向生效）
        window.contentView = imageView
        window.width = 300
        window.showAsDropDown(popup)
        dimBehind(window)
    }

    companion object {

        fun dimBehind(popupWindow: PopupWindow) {
            val container: View
            if (popupWindow.background == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    container = popupWindow.contentView.parent as View
                } else {
                    container = popupWindow.contentView
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    container = popupWindow.contentView.parent.parent as View
                } else {
                    container = popupWindow.contentView.parent as View
                }
            }
            val context = popupWindow.contentView.context
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val p = container.layoutParams as WindowManager.LayoutParams
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            p.dimAmount = 0.9f
            wm.updateViewLayout(container, p)
        }
    }


}
