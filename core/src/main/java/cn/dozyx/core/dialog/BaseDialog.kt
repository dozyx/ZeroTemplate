package cn.dozyx.core.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

abstract class BaseDialog(context: Context) : Dialog(context) {
    @LayoutRes
    abstract fun getLayoutId(): Int

    class BaseElement(@IdRes id: Int)
    class TextElement(@StringRes textRes: Int, text: CharSequence = "")
    class ImageElement(@DrawableRes imageRes: Int, imageDrawable: Drawable? = null)
}
