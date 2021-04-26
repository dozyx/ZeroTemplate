package cn.dozyx.template.drawable

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 简单的蜡烛图 View
 * @author dozyx
 * @date 4/27/21
 */
class SimpleCandleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val drawable = CandleDrawable()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(0, 0, 10, 10)
        drawable.draw(canvas)
    }
}