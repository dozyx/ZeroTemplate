package com.dozeboy.core.utli.util

import java.util.*

/**
 * @author dozeboy
 * @date 2018/11/21
 */

class ColorUtil {
    companion object {
        fun random(): Int = 0xff000000.toInt() or Random().nextInt(0xffffff)
    }
}