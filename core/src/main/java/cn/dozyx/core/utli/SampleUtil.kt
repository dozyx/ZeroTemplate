package cn.dozyx.core.utli

import java.util.ArrayList

/**
 * @author dozyx
 * @date 2019-10-30
 */
object SampleUtil {

    val strings: List<String>
        get() = getStrings(50)

    @JvmOverloads
    fun getStrings(count: Int, tag: String = ""): List<String> {
        val datas = ArrayList<String>()
        for (i in 0 until count) {
            datas.add("sample $tag: $i ")
        }
        return datas
    }
}
