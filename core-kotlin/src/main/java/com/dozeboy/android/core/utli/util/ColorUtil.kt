package com.dozeboy.android.core.utli.util

import android.graphics.Color
import java.util.*

/**
 * @author timon
 * @date 2018/11/21
 */

class ColorUtil {
    companion object {
        fun random(): Int = 0xff000000.toInt() or Random().nextInt(0xffffff)
    }
}