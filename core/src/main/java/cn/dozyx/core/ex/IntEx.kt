package cn.dozyx.core.ex

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author dozyx
 * @date 4/27/21
 */
val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )