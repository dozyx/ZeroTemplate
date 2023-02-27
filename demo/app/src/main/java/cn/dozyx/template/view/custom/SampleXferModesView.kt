package cn.dozyx.template.view.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * https://android.googlesource.com/platform/development/+/master/samples/ApiDemos/src/com/example/android/apis/graphics/Xfermodes.java
 */
class SampleXferModesView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    /**
     * 矩形
     */
    private val mSrcB: Bitmap

    /**
     * 圆
     */
    private val mDstB: Bitmap
    private val mBG // background checker-board pattern
            : Shader

    init {
        mSrcB = makeSrc(W, H)
        mDstB = makeDst(W, H)
        // make a ckeckerboard pattern
        val bm: Bitmap = Bitmap.createBitmap(
            intArrayOf(
                -0x1, -0x333334,
                -0x333334, -0x1
            ), 2, 2,
            Bitmap.Config.RGB_565
        )
        mBG = BitmapShader(
            bm,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        )
        val m = Matrix()
        m.setScale(6F, 6F)
        mBG.setLocalMatrix(m)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        val labelP = Paint(Paint.ANTI_ALIAS_FLAG)
        labelP.setTextAlign(Paint.Align.CENTER)
        val paint = Paint()
        paint.setFilterBitmap(false)
        canvas.translate(15F, 35F)
        var x = 0
        var y = 0
        for (i in sModes.indices) {
            // draw the border
            paint.setStyle(Paint.Style.STROKE)
            paint.setShader(null)
            canvas.drawRect(
                x - 0.5f, y - 0.5f,
                x + W + 0.5f, y + H + 0.5f, paint
            )
            // draw the checker-board pattern
            paint.setStyle(Paint.Style.FILL)
            paint.setShader(mBG)
            canvas.drawRect(x.toFloat(), y.toFloat(), (x + W).toFloat(), (y + H).toFloat(), paint)
            // draw the src/dst example into our offscreen bitmap
            val sc: Int = canvas.saveLayer(x.toFloat(), y.toFloat(), (x + W).toFloat(),
                (y + H).toFloat(), null)
            canvas.translate(x.toFloat(), y.toFloat())
            // 先画圆
            canvas.drawBitmap(mDstB, 0F, 0F, paint)
            paint.setXfermode(sModes[i])
            // 再画矩形
            canvas.drawBitmap(mSrcB, 0F, 0F, paint)
            paint.setXfermode(null)
            canvas.restoreToCount(sc)
            // draw the label
            canvas.drawText(
                sLabels[i],
                (x + W / 2).toFloat(), y - labelP.getTextSize() / 2, labelP
            )
            x += W + 10
            // wrap around when we've drawn enough for one row
            if (i % ROW_MAX == ROW_MAX - 1) {
                x = 0
                y += H + 30
            }
        }
    }

    companion object {
        private const val W = 264
        private const val H = 264
        private const val ROW_MAX = 4 // number of samples per row
        private val sModes: Array<Xfermode> = arrayOf<Xfermode>(
            PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            PorterDuffXfermode(PorterDuff.Mode.SRC),
            PorterDuffXfermode(PorterDuff.Mode.DST),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.XOR),
            PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            PorterDuffXfermode(PorterDuff.Mode.SCREEN)
        )
        private val sLabels = arrayOf(
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
        )
    }

    // create a bitmap with a circle, used for the "dst" image
    fun makeDst(w: Int, h: Int): Bitmap {
        val bm: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.setColor(-0x33bc)
        c.drawOval(RectF(0F, 0F, (w * 3 / 4).toFloat(), (h * 3 / 4).toFloat()), p)
        return bm
    }

    // create a bitmap with a rect, used for the "src" image
    fun makeSrc(w: Int, h: Int): Bitmap {
        val bm: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.setColor(-0x995501)
        c.drawRect((w / 3).toFloat(), (h / 3).toFloat(), (w * 19 / 20).toFloat(),
            (h * 19 / 20).toFloat(), p)
        return bm
    }
}