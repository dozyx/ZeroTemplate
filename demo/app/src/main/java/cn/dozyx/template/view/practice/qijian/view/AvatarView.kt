package cn.dozyx.template.view.practice.qijian.view

import android.content.Context
import android.graphics.*
import android.view.View
import cn.dozyx.template.R

/**
 * BitmapShader 实现不规则头像
 * Create by timon on 2019/9/7
 **/
class AvatarView(context: Context) : View(context) {
    private val paint = Paint()
    private val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_dog)
    private val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val matrix = Matrix()
        val scale = width / bitmap.width.toFloat()
        matrix.setScale(scale, scale)
        shader.setLocalMatrix(matrix)
        paint.shader = shader

        val half = width / 2.toFloat()
        canvas?.drawCircle(half, half, width / 2.toFloat(), paint)
    }
}