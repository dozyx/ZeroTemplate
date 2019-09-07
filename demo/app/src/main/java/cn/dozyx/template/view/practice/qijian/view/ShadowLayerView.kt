package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.*
import android.view.View
import cn.dozyx.template.R

/**
 * 模糊
 * Create by timon on 2019/9/7
 **/
class ShadowLayerView(context: Context) : View(context) {
    private val paint = Paint()
    private val bmpDog = BitmapFactory.decodeResource(resources, R.drawable.ic_dog)

    init {
        // setShadowLayer 只有文字支持硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint.apply {
            color = Color.BLACK
            textSize = 25f
            // radius：模糊半径，越大越模糊
            // dx/dy：偏移
            // color：阴影颜色，对图片无效
            setShadowLayer(1f, 10f, 10f, Color.GREEN)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawColor(Color.WHITE)
            drawText("哈哈", 100f, 100f, paint)
            drawCircle(300f, 100f, 50f, paint)
            // 图片的阴影是对复制出来的图片的边缘进行模糊
            drawBitmap(bmpDog, null, Rect(500, 30, 500 + bmpDog.width, 50 + bmpDog.height), paint)
        }
    }

}