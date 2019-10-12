package cn.dozyx.core.utli.edittext

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils

/**
 * Create by dozyx on 2019/5/31
 **/
class SpaceInputFilter : InputFilter {
    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        return if (TextUtils.isEmpty(source) || !source.toString().contains(" ")) {
            null
        } else source.toString().replace("\\s".toRegex(), "")
    }
}