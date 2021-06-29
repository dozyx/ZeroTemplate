package cn.dozyx.template.view

import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import androidx.appcompat.content.res.AppCompatResources
import cn.dozyx.core.ex.dp
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.activity_base_test.*
import kotlinx.android.synthetic.main.test_text.*

/**
 * Create by dozyx on 2019/6/4
 */
class SpanTest : BaseTestActivity() {

    override fun initActions() {
        addButton("nothing") {
            val spannableString = SpannableString("12MBMB3456789")
//        spannableString.setSpan(ImageSpan(this,R.drawable.ic_menu_camera),0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//        spannableString.setSpan(ForegroundColorSpan(Color.BLUE), 2, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                4,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
//        text_log.text = spannableString
//        text_log.text = getSpannableStringByBuilder()
            text_log.text = getSpannableStringByBuilder2()
            et_test.setText(spannableString)
        }

        addButton("image span") {
            val drawable = AppCompatResources.getDrawable(this, R.drawable.ic_image_span)
            drawable?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                val imageSpan = ImageSpan(this, ImageSpan.ALIGN_BASELINE)
                val spannableString = SpannableString("12345678")
                spannableString.setSpan(imageSpan, 7, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                text_log.text = spannableString
            }
        }
        val default = "1 h 36 min"
        text_log.text = default
        text_log.setTypeface(null, Typeface.BOLD)
        addButton("typeface span") {
            val spannableString = SpannableString(default)
            spannableString.setSpan(
//                TypefaceSpan("sans-serif-condensed"),
                TypefaceSpan(Typeface.SANS_SERIF),
                4,
                6,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannableString.setSpan(
                AbsoluteSizeSpan(24.dp.toInt()),
                4,
                6,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            /*spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                4,
                6,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )*/
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                spannableString.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )

            text_log.text = spannableString
        }
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
}
