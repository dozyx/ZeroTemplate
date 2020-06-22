package cn.dozyx.core.utli

import java.util.ArrayList

/**
 * @author dozyx
 * @date 2019-10-30
 */
object SampleUtil {

    val strings: MutableList<String>
        get() = getStrings(50)

    @JvmOverloads
    fun getStrings(count: Int, tag: String = ""): MutableList<String> {
        val datas = MutableList<String>(count) {
            "sample $tag: $it "
        }
        return datas
    }
}
