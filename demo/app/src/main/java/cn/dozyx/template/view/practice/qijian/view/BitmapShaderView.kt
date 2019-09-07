package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.*
import android.view.View
import cn.dozyx.template.R

/**
 * shader：着色器，用来给空白图形上色。效果与 ps 中的印章工具类似。
 * Create by timon on 2019/9/7
 **/
class BitmapShaderView(context: Context) : View(context) {
    private val paint = Paint()
    private val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_dog_edge)

    init {
        paint.apply {
            // 填充顺序：先竖向，再横向
            shader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        canvas?.drawRect((width / 3).toFloat(), (height / 3).toFloat(), width * 2 / 3.toFloat(), height * 2 / 3.toFloat(), paint)
    }
}