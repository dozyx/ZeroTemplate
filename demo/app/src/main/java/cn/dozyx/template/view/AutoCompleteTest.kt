package cn.dozyx.template.view

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListPopupWindow
import android.widget.PopupWindow
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.ReflectUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.test_auto_complete.*
import timber.log.Timber
import kotlin.math.max

/**
 * AutoCompleteTextView
 * 建议列表从 data adapter 中获取
 * 当匹配的字符数量超过 threshold 时，显示建议
 */
class AutoCompleteTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("AutoCompleteTest.onCreate ${ScreenUtils.getScreenHeight()}")
        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES)
        auto_text.apply {
            setAdapter(adapter)
            threshold = 1
        }
        auto_text.setDropDownBackgroundDrawable(ColorDrawable(Color.BLUE))
//        auto_text.dropDownHeight = ViewGroup.LayoutParams.MATCH_PARENT
//        auto_text.dropDownHeight = getPopupByReflection(auto_text).height
        auto_text.dropDownHeight = getMaxAvailableHeight(btn)

        btn.setOnClickListener {
            val window = PopupWindow(LayoutInflater.from(this).inflate(R.layout.popup_window, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            // 好像不能设置 modal，有一个 setTouchModal 方法，不过被标记为 @hide
            window.isFocusable = true
            window.setBackgroundDrawable(ColorDrawable())
            window.height = getMaxAvailableHeight(btn)
//            window.overlapAnchor = true // 显示在 anchor 上方还是下方
            window.showAsDropDown(btn)
        }

    }


    private fun getPopupByReflection(textview: AutoCompleteTextView) : ListPopupWindow {
        val reflect = ReflectUtils.reflect(textview)
        return reflect.field("mPopup").get()
    }


    private fun getFixedDropDownHeight(): Int {
        val anchorViewId: Int = auto_text.dropDownAnchor
        if (anchorViewId == View.NO_ID) {
            return ViewGroup.LayoutParams.WRAP_CONTENT
        }
        val anchorView: View = auto_text.rootView.findViewById(anchorViewId)
                ?: return ViewGroup.LayoutParams.WRAP_CONTENT
        val visibleDisplayFrame = Rect()
        anchorView.rootView.getWindowVisibleDisplayFrame(visibleDisplayFrame)
        val anchorPos = IntArray(2)
        anchorView.getLocationOnScreen(anchorPos)
        val height =  visibleDisplayFrame.bottom - anchorPos[1] - anchorView.height
        Timber.d("AutoCompleteTest.getFixedDropDownHeight $height")
        return height
    }

    fun getMaxAvailableHeight(anchor: View): Int {
        var displayFrame: Rect? = null
        val visibleDisplayFrame = Rect()
        val appView: View = anchor.rootView
        appView.getWindowVisibleDisplayFrame(visibleDisplayFrame)
        Timber.d("AutoCompleteTest.getMaxAvailableHeight $visibleDisplayFrame")
        displayFrame = visibleDisplayFrame
        val anchorPos: IntArray = IntArray(2)
        anchor.getLocationOnScreen(anchorPos)
        val bottomEdge = displayFrame.bottom
        val distanceToBottom: Int
        distanceToBottom = bottomEdge - (anchorPos[1] + anchor.height)
        val distanceToTop = anchorPos[1] - displayFrame.top

        // anchorPos[1] is distance from anchor to top of screen
        Timber.d("AutoCompleteTest.getMaxAvailableHeight $distanceToBottom $distanceToTop")
        return max(distanceToBottom, distanceToTop)
    }

    override fun getLayoutId() = R.layout.test_auto_complete

    companion object {
        private val COUNTRIES = arrayListOf("Belgium", "France", "Italy", "Germany", "Spain")
    }
}