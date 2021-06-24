package cn.dozyx.template.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.SizeUtils
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
//        text1.text = spannableString
//        text1.text = getSpannableStringByBuilder()
        text1.text = getSpannableStringByBuilder2()
        edit1.setText(spannableString)
    }

    private fun getSpannableStringByBuilder(): CharSequence {
        val builder = SpannableStringBuilder()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 这个 append 方法有 api 版本限制
            builder.append(
                "111",
                AbsoluteSizeSpan(SizeUtils.sp2px(24F)),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
                .append(
                    "222",
                    AbsoluteSizeSpan(SizeUtils.sp2px(12F)),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
        }
        return builder
    }

    private fun getSpannableStringByBuilder2(): CharSequence {
        val builder = SpannableStringBuilder()
        val str1 = SpannableString("111")
        str1.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(24F)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.append(str1)
        val str2 = SpannableString("222")
        str2.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(12F)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.append(str2)
        return builder
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_span_test
    }
}
