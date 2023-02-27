package cn.dozyx.template.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View

class CanvasTestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.BLUE
    }

    private val textPaint = Paint().apply {
        color = Color.BLUE
        textSize = 36F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
        for (mode in PorterDuff.Mode.values()) {
            drawShape(canvas, mode)
        }
    }

    @SuppressLint("NewApi")
    private fun drawShape(canvas: Canvas, mode: PorterDuff.Mode) {
        val column = mode.ordinal % COLUMN_COUNT
        val row = mode.ordinal / COLUMN_COUNT
        val startX = column * (270 + 60)
        val startY = row * (360 + 60)

        val savedCount = canvas.saveLayer(null, null) // 不先保存的话会导致部分效果直接把白色背景去除，显示为黑色

        paint.color = Color.BLUE
        canvas.drawCircle(startX + 90F, startY + 90F, 90F, paint)

        paint.color = Color.GREEN
        canvas.drawCircle(startX + 90F + 90F, startY + 90F, 90F, paint)

        paint.xfermode = PorterDuffXfermode(mode)
        paint.color = Color.YELLOW
        canvas.drawRect(startX + 90F, startY + 90F, startX + 270F, startY + 270F, paint)

        paint.xfermode = null
        canvas.restoreToCount(savedCount)
        canvas.drawText(mode.name, startX + 90F, startY + 300F, textPaint)
    }

    companion object {
        const val COLUMN_COUNT = 4
    }
}