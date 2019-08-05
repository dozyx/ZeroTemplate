package cn.dozyx.template.view.anim.arrowcircle

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import cn.dozyx.template.R

/**
 * Create by timon on 2019/7/18
 */
class ArrowCircleView : View {

    private var animator: ValueAnimator?
    private val circlePath = Path()
    private val dstPath = Path()
    private val paint = Paint()
    private var pathMeasure: PathMeasure
    private var curAnimValue = 0.0f
    private var arrowBitmap: Bitmap
    private var pos = FloatArray(2)
    private var tan = FloatArray(2)
    private val bitmapMatrix = Matrix()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        circlePath.addCircle(100f, 100f, 50f, Path.Direction.CW)
        pathMeasure = PathMeasure(circlePath, true)
        arrowBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_arrow)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
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
        dstPath.reset()
        canvas?.drawColor(Color.WHITE)
        pathMeasure.getSegment(0f, stop, dstPath, true)
        canvas?.drawPath(dstPath, paint)

        // 旋转箭头并绘制
        //getPosTan 实现
        /*pathMeasure.getPosTan(stop, pos, tan)
        val degrees = (atan2(tan[1], tan[0]) * 180.0 / Math.PI).toFloat()
        bitmapMatrix.reset()
        bitmapMatrix.postRotate(degrees, arrowBitmap.width / 2f, arrowBitmap.height / 2f)
        bitmapMatrix.postTranslate(pos[0] - arrowBitmap.width / 2, pos[1] - arrowBitmap.height / 2)*/

        // getMatrix 实现
        pathMeasure.getMatrix(stop, bitmapMatrix, PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG)
        bitmapMatrix.preTranslate(-arrowBitmap.width / 2f, -arrowBitmap.height / 2f)

        canvas?.drawBitmap(arrowBitmap, bitmapMatrix, paint)
    }
}
