package cn.dozyx.template.drawable

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColorInt
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.ex.dp
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_drawable.*

class DrawableTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val originalColor = "#FFCD22"
        val defaultGrey = "#a3202124"
        iv_mock_notification_small_icon.setColorFilter(
            Color.parseColor(originalColor),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    override fun getLayoutId() = R.layout.test_drawable


    fun transform() {
        // 利用扩展函数转换
        val bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888)
        val drawable = bitmap.toDrawable(resources)
        drawable.toBitmap()
    }
}

class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    //    val drawable = ColorDrawable(Color.RED)
    val drawable = MeshDrawable()// 网格

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        drawable.setBounds(50, 0, width, height)// 需要注意设置边界
        drawable.draw(canvas)

    }
}

/**
 * 网格间隔
 */
private val INTERNAL = 50.dp

class MeshDrawable : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#F9A825".toColorInt()
        strokeWidth = 5.dp
    }
    /***************************** 抽象方法 *******************************/
    /**
     * 最重要
     */
    override fun draw(canvas: Canvas) {

        // 绘制竖线
        var x = bounds.left.toFloat()
        while (x <= bounds.right) {
            canvas.drawLine(x, bounds.top.toFloat(), x, bounds.bottom.toFloat(), paint)
            x += INTERNAL
        }


        // 绘制横线
        var y = bounds.top.toFloat()
        while (y <= bounds.bottom) {
            canvas.drawLine(bounds.left.toFloat(), y, bounds.right.toFloat(), y, paint)
            y += INTERNAL
        }


    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        // 不透明度。主要用来判断如何与其他图像融合

        // 简单方式
        return when (paint.alpha) {
            0 -> PixelFormat.TRANSPARENT// 透明
            0xff -> PixelFormat.OPAQUE// 不透明
            else -> PixelFormat.TRANSLUCENT// 半透明
        }
    }

    /***************************************************************************/


    override fun getAlpha(): Int {
        // 返回设置进来的透明度
        return paint.alpha
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }
}

class CustomDrawable : Drawable() {
    override fun draw(canvas: Canvas) {
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}