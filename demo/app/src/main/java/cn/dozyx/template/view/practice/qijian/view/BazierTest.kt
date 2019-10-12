package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Create by dozyx on 2019/8/16
 */
class BazierTest : View {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val path = Path()
        path.moveTo(100f, 300f)
        //二阶贝塞尔曲线
        path.quadTo(200f, 200f, 300f, 300f)
        path.quadTo(400f, 400f, 500f, 300f)
//        path.rQuadTo(100f, -100f, 200f, 0f)
//        path.rQuadTo(100f, 100f, 200f, 0f)

        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas.drawPath(path, paint)
    }
}
