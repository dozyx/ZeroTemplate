package cn.dozyx.template.view.anim

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.abs

/**
 * Create by timon on 2019/7/11
 **/

class GetSegmentView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var animator: ValueAnimator?
    private var curAnimValue: Float = 0f
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dstPath = Path()
    private val circlePath = Path()
    private val pathMeasure: PathMeasure

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        circlePath.addCircle(100f, 100f, 50f, Path.Direction.CW)
        pathMeasure = PathMeasure(circlePath, true)
        // 硬件加速会导致绘图出问题，因此关闭。
        // 改成启用硬件加速也没发现有什么问题
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
        animator = ValueAnimator.ofFloat(0f, 1f)
        animator?.apply {
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                curAnimValue = it.animatedValue as Float
                invalidate()
            }
            duration = 2000
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val length = pathMeasure.length
        val stop = length * curAnimValue
        // stop 小于0.5时，start 为0；stop大于0.5时，start逐渐靠近起点；进度到最后时，stop 和 start 重合
        val start = stop - (0.5 - abs(curAnimValue - 0.5)) * length
        dstPath.reset()
        canvas?.drawColor(Color.WHITE)
//        pathMeasure.getSegment(0f, stop, dstPath, true)
        pathMeasure.getSegment(start.toFloat(), stop, dstPath, true)
        canvas?.drawPath(dstPath, paint)
    }

}