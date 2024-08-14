package cn.dozyx.template.view.anim.paysuccess

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import timber.log.Timber

/**
 * Create by dozyx on 2019/7/18
 */
class PaySuccessView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private var animator: ValueAnimator?
    private val graphPath = Path()
    private val dstPath = Path()
    private val paint = Paint()
    private var pathMeasure: PathMeasure
    private var curAnimValue = 0.0f
    private val centerX = 100f
    private val centerY = 100f
    private val radius = 50f
    private var hasAnimToMiddle = false

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        //外圈
        graphPath.addCircle(centerX, centerY, radius, Path.Direction.CW)
        // 对勾
        graphPath.moveTo(centerX - radius / 2, centerY)
        graphPath.lineTo(centerX, centerY + radius / 2)
        graphPath.lineTo(centerX + radius / 2, centerY - radius / 3)

        pathMeasure = PathMeasure(graphPath, false)

        animator = ValueAnimator.ofFloat(0f, 2f)
        animator?.apply {
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                curAnimValue = it.animatedValue as Float
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) {
                    pathMeasure = PathMeasure(graphPath, false)
                    dstPath.rewind()
                    hasAnimToMiddle = false
                }

                override fun onAnimationEnd(animation: Animator) {

                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationStart(animation: Animator) {

                }
            })
            duration = 4000
        }
    }

    override fun onAttachedToWindow() {
        Timber.d("PaySuccessView.onAttachedToWindow")
        super.onAttachedToWindow()
        animator?.start()
    }

    override fun onDetachedFromWindow() {
        Timber.d("PaySuccessView.onDetachedFromWindow")
        animator?.cancel()
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.WHITE)
        when {
            curAnimValue < 1f -> {
                // 绘制外圈的圆
                val stop = pathMeasure.length * curAnimValue
                pathMeasure.getSegment(0f, stop, dstPath, true)
            }
            curAnimValue == 1f -> {
                // curAnimValue 为 float，不一定会完全等于1，所以导致这里的逻辑可能无法执行
                /* pathMeasure.getSegment(0f, pathMeasure.length, dstPath, true)
                 // 跳到对勾的路径
                 pathMeasure.nextContour()*/
            }
            else -> {
                if (!hasAnimToMiddle) {
                    pathMeasure.getSegment(0f, pathMeasure.length, dstPath, true)
                    // 跳到对勾的路径
                    pathMeasure.nextContour()
                    hasAnimToMiddle = true
                }
                val stop = pathMeasure.length * (curAnimValue - 1)
                pathMeasure.getSegment(0f, stop, dstPath, true)
            }
        }
        canvas?.drawPath(dstPath, paint)
    }
}
