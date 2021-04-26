package cn.dozyx.template.drawable

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * @author dozyx
 * @date 4/27/21
 */
class TooltipOverlay(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    init {
        setImageDrawable(TooltipOverlayDrawable())
    }
}