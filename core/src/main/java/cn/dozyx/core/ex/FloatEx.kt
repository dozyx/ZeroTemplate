package cn.dozyx.core.utli

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author dozyx
 * @date 4/27/21
 */

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )