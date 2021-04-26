package cn.dozyx.template.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * 蜡烛图，主要用于股票等软件
 * @author dozyx
 * @date 4/27/21
 */
class CandleDrawable : Drawable() {
    override fun draw(canvas: Canvas) {
        //绘制蜡烛
        //绘制基本信息
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE
}