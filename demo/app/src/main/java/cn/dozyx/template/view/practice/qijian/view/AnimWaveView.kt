package cn.dozyx.template.view.practice.qijian.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.view.animation.LinearInterpolator
import timber.log.Timber

/**
 * Create by dozyx on 2019/9/3
 **/
class AnimWaveView(context: Context) : View(context) {
    private val paint: Paint = Paint()
    private val path: Path = Path()
    private val itemWaveLength = 1200f
    private var dx = 0f
    private var animator: ValueAnimator? = null

    init {
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnim()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.reset()
        val originY = 300f
        val halfWaveLen = itemWaveLength / 2f
        path.moveTo(-itemWaveLength + dx, originY)
        var i = -itemWaveLength
        while (i <= width + itemWaveLength) {
            i += itemWaveLength
            path.rQuadTo(halfWaveLen / 2, -200f, halfWaveLen, 0f)
            path.rQuadTo(halfWaveLen / 2, 200f, halfWaveLen, 0f)
        }
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        canvas?.drawPath(path, paint)
    }

    private fun startAnim() {
        animator = ValueAnimator.ofFloat(0f, itemWaveLength)
        animator?.apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                dx = it.animatedValue as Float
                Timber.d("AnimWaveView.startAnim $dx")
                postInvalidate()
            }
        }
        animator?.start()
    }
}