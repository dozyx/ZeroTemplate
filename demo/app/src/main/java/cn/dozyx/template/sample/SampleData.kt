package cn.dozyx.template.sample

import java.util.ArrayList

/**
 * Create by timon on 2019/10/28
 */
fun getStrings(count: Int): List<String> {
    val datas = ArrayList<String>()
    for (i in 0 until count) {
        datas.add("data$i")
    }
    return datas
}
