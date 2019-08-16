package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import timber.log.Timber

/**
 * Create by timon on 2019/8/14
 */
class DrawTextView : View {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val baseLineX = 0f
        val baseLineY = 200f

        // 绘制基线
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawLine(baseLineX, baseLineY, 3000f, baseLineY, paint)

        paint.color = Color.GREEN
        paint.textSize = 120f
        paint.textAlign = Paint.Align.LEFT
        // x,y 参数表示的是基线所在的坐标
        canvas.drawText("harvic \'s blog", baseLineX.toFloat(), baseLineY.toFloat(), paint)

        val fontMetrics = paint.fontMetrics
        Timber.d("DrawTextView.onDraw ascent: ${fontMetrics.ascent} descent: ${fontMetrics.descent} top: ${fontMetrics.top} bottom: ${fontMetrics.bottom}")
        // 计算每条线的位置
        val ascent = baseLineY + fontMetrics.ascent
        val descent = baseLineY + fontMetrics.descent
        val top = baseLineY + fontMetrics.top
        val bottom = baseLineY + fontMetrics.bottom
        paint.color = Color.BLUE
        canvas.drawLine(baseLineX, top, 3000f, top, paint)
        paint.color = Color.GREEN
        canvas.drawLine(baseLineX, ascent, 3000f, ascent, paint)
        paint.color = Color.GREEN
        canvas.drawLine(baseLineX, descent, 3000f, descent, paint)
        paint.color = Color.RED
        canvas.drawLine(baseLineX, bottom, 3000f, bottom, paint)
    }
}
