package cn.dozyx.core.ex

import android.content.Intent

fun Intent.flagToHex(): String {
    return Integer.toHexString(flags)
}