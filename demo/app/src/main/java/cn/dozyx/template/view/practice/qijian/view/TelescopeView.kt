package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import cn.dozyx.template.R

/**
 * BitmapShader 实现望远镜效果，显示触摸位置的图像
 * Create by dozyx on 2019/9/7
 **/
class TelescopeView(context: Context) : View(context) {
    private val paint = Paint()
    private val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_scenery)
    private var bitmapBG: Bitmap? = null
    private var lastX = -1
    private var lastY = -1

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x.toInt()
                lastY = event.y.toInt()
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                lastX = event.x.toInt()
                lastY = event.y.toInt()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                lastX = -1
                lastY = -1
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (bitmapBG == null) {
            bitmapBG = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvasBG = Canvas(bitmapBG)
            canvasBG.drawBitmap(bitmap, null, Rect(0, 0, width, height), paint)
        }
        if (lastX != -1 && lastY != -1) {
            paint.shader = BitmapShader(bitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            canvas?.drawCircle(lastX.toFloat(), lastY.toFloat(), 150f, paint)
        }
    }
}