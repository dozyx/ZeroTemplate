package cn.dozyx.core.utli.edittext

import android.text.InputFilter
import android.text.Spanned

/**
 * Create by timon on 2019/5/31
 **/
class SpaceInputFilter : InputFilter {
    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        return source?.replace(Regex("\\s"), "")
    }
}