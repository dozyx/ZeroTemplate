package com.zerofate.template.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class TouchFollowView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val propertyPaint: Paint = Paint()
    private val out = IntArray(2)

    init {
        propertyPaint.color = Color.YELLOW
        propertyPaint.strokeWidth = 5F
        propertyPaint.textSize = 20F
    }

    private var lastX = 0
    private var lastY = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val x = it.x.toInt()
            val y = it.y.toInt()
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = x
                    lastY = y
                }
                MotionEvent.ACTION_MOVE -> {
                    val offsetX = x - lastX
                    val offsetY = y - lastY
                    // 方法一
//                    layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
                    // 方法二
//                    offsetLeftAndRight(offsetX)
//                    offsetTopAndBottom(offsetY)
                    // 方法三 这种方式在父布局重新 layout 时也不会还原
                    val params = layoutParams as LinearLayout.LayoutParams
                    params.leftMargin = left + offsetX
                    params.topMargin = top + offsetY
                    layoutParams = params
                }
                MotionEvent.ACTION_UP -> {
                    postDelayed({ parent.requestLayout() }, 3000)
                }
            }
            return true
        }

        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val textHeight = propertyPaint.fontMetricsInt.bottom - propertyPaint.fontMetricsInt.top
        canvas?.drawText("x: $x", 0F, textHeight.toFloat(), propertyPaint)
        canvas?.drawText("translationX: $translationX", 0F, textHeight.toFloat() * 2 + 10, propertyPaint)
        getLocationOnScreen(out)
        canvas?.drawText("locationOnScreen[0]: ${out[0]}", 0F, textHeight.toFloat() * 3 + 10, propertyPaint)
    }
}