package cn.dozyx.template.view

import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.SizeUtils
import timber.log.Timber
import java.util.*

/**
 * 1. 对比不同设备的文本显示效果
 */
class TextTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testPaint()
        val quantityString = resources.getQuantityString(R.plurals.count_video, 1)
        Timber.d("TextTest.onCreate $quantityString")
    }

    private fun testPaint() {
        val paint = Paint()
        paint.textSize = SizeUtils.sp2px(16F).toFloat()

        val text = "123"
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        // getTextBounds 和 measureText 拿到的宽度不同 https://stackoverflow.com/questions/7549182/android-paint-measuretext-vs-gettextbounds
        Timber.d("TextTest.testPaint: $bounds ${bounds.width()} ${paint.measureText(text)}")
    }

    override fun getLayoutId() = R.layout.test_text
}