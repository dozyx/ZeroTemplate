package cn.dozyx.template.performance

import android.os.Trace
import androidx.core.os.TraceCompat
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import java.util.*

/**
 * @author dozyx
 * @date 2020/3/1
 */
class GcTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("内存抖动") {
            override fun run() {
                imPrettySureSortingIsFree()
            }
        })
    }

    /**
     * 排序后打印二维数组，一行行打印
     * https://juejin.im/post/5a7ff867f265da4e865a6b5b
     */
    fun imPrettySureSortingIsFree() {
        val dimension = 300
        val lotsOfInts = Array(dimension) { IntArray(dimension) }// 300*300的数组
        val randomGenerator = Random()
        // 初始化二维数组
        for (i in lotsOfInts.indices) {
            for (j in lotsOfInts[i].indices) {
                lotsOfInts[i][j] = randomGenerator.nextInt()
            }
        }
        for (i in lotsOfInts.indices) {
            var rowAsStr = ""
            //排序
            val sorted = getSorted(lotsOfInts[i])
            //拼接打印
            for (j in lotsOfInts[i].indices) {
                rowAsStr += sorted[j]
                if (j < lotsOfInts[i].size - 1) {
                    rowAsStr += ", "
                }
            }
        }
    }

    private fun getSorted(input: IntArray): IntArray {
        val clone = input.clone()
        Arrays.sort(clone)
        return clone
    }
}