package cn.dozyx.template.view.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import com.blankj.utilcode.util.SizeUtils
import timber.log.Timber

class TooltipLayout : FrameLayout {

    private val paint = Paint()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        paint.color = Color.MAGENTA
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        setWillNotDraw(false)// 默认请求如果 ViewGroup 没有设置 background 的话不会触发 onDraw
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight + SizeUtils.dp2px(indicatorHeightInDp))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Timber.d("TooltipViewGroup.onLayout ${SizeUtils.dp2px(indicatorHeightInDp)}")
        super.onLayout(changed, left, top + SizeUtils.dp2px(indicatorHeightInDp), right, bottom)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Timber.d("TooltipViewGroup.onDraw")
        onDrawTooltipBackground(canvas)
    }

    private fun onDrawTooltipBackground(canvas: Canvas?) {
        canvas?.apply {
            val indicatorHeight = SizeUtils.dp2px(indicatorHeightInDp).toFloat()
            val indicatorWidth = SizeUtils.dp2px(indicatorWidthInDp).toFloat()
            val path = Path()
            path.moveTo(100F, indicatorHeight)
            path.lineTo(100F + indicatorWidth / 2, 0F)
            path.lineTo(100F + indicatorWidth, indicatorHeight)
            drawPath(path, paint)
            drawRoundRect(RectF(0F, indicatorHeight, measuredWidth.toFloat(), measuredHeight.toFloat()),
                    SizeUtils.dp2px(4F).toFloat(), SizeUtils.dp2px(4F).toFloat(), paint)
        }
    }

    companion object {
        private const val indicatorWidthInDp = 10F
        private const val indicatorHeightInDp = 6F
    }

}