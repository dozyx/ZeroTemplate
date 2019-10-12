package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.*
import android.view.View
import cn.dozyx.template.R

/**
 * 绘制纯色阴影
 * 原理：绘制一个灰色副本，并使其发光，偏移作为阴影
 * Create by dozyx on 2019/9/7
 **/
class BitmapShadowView(context: Context) : View(context) {
    private val paint = Paint()
    private val orignalBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_cat_dog)
    // extractAlpha 创建一幅仅具有 alpha 值的空白图像，图像颜色将由画笔决定
    private val shadowBitmap = orignalBitmap.extractAlpha()

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            val width = 200
            val height = width * shadowBitmap.width / shadowBitmap.height

            //绘制灰色阴影
            paint.color = Color.GRAY
            paint.maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL) // 感觉不明显
            drawBitmap(shadowBitmap, null, Rect(10, 10, width, height), paint)

            // 绘制黑色阴影
//            translate(width.toFloat(), 0f)
//            paint.color = Color.BLACK
//            drawBitmap(shadowBitmap, null, Rect(10, 10, width, height), paint)

            // 绘制黑色阴影
            translate(-5f, -5f)
//            paint.maskFilter = null
            drawBitmap(orignalBitmap, null, Rect(10, 10, width, height), paint)
        }
    }

}