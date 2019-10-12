package cn.dozyx.template.view.practice.qijian.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView

/**
 * 闪光文字
 * Create by dozyx on 2019/9/7
 **/
class ShimmerTextView : TextView {

    private var dx = 0
    private lateinit var linearGradient: LinearGradient
    private val mPaint = paint

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val length = paint.measureText(text.toString()).toInt()
        createAnim(length)
        createLinearGradient(length)
    }

    override fun onDraw(canvas: Canvas?) {
        val matrix = Matrix()
        matrix.setTranslate(dx.toFloat(), 0.toFloat())
        linearGradient.setLocalMatrix(matrix)
        paint.shader = linearGradient
        super.onDraw(canvas)
    }

    private fun createLinearGradient(length: Int) {
        linearGradient = LinearGradient(-length.toFloat(), 0f, 0f, 0f, intArrayOf(currentTextColor, 0xff00ff00.toInt(), currentTextColor), floatArrayOf(0f, 0.5f, 1f), Shader.TileMode.CLAMP)
    }

    private fun createAnim(length: Int) {
        val animator = ValueAnimator.ofInt(0, 2 * length)
        animator.addUpdateListener {
            dx = animator.animatedValue as Int
            postInvalidate()
        }
        animator.apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            duration = 2000
            start()
        }
    }
}