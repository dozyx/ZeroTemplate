package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Create by timon on 2019/8/16
 **/
class GestureTrackView(context: Context?) : View(context) {
    val path = Path()
    val paint = Paint()

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
    }

    var preX: Float = 0F
    var preY: Float = 0F
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                preX = event.x
                preY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = (preX + event.x) / 2
                val endY = (preY + event.y) / 2
                path.quadTo(preX, preY, endX, endY)
                preX = event.x
                preY = event.y
                postInvalidate()
//                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.drawPath(path, paint)
    }
}