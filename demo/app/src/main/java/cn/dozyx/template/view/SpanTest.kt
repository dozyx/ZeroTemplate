package cn.dozyx.template.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_span_test.*

/**
 * Create by dozyx on 2019/6/4
 */
class SpanTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val spannableString = SpannableString("12MBMB3456789")
//        spannableString.setSpan(ImageSpan(this,R.drawable.ic_menu_camera),0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//        spannableString.setSpan(ForegroundColorSpan(Color.BLUE), 2, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        text1.text = spannableString
        edit1.setText(spannableString)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_span_test
    }
}
