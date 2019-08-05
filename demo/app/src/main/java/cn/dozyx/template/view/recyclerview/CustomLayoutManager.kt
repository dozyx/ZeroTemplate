package cn.dozyx.template.view.recyclerview

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * Create by timon on 2019/8/1
 */
class CustomLayoutManager : RecyclerView.LayoutManager() {
    private val DIRECTION_NONE: Int = 0
    private var firstVisiblePosition: Int = 0
    private var decoratedChildWidth: Int = 0
    private var decoratedChildHeight: Int = 0

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        // adapter 数据改变或 adapter 被替换时调用
        Timber.d("CustomLayoutManager.onLayoutChildren")

    }

    private fun fillGrid(direction: Int, childLeft: Int, childTop: Int, recycler: RecyclerView.Recycler) {

    }

    private fun updateWindowSizing() {

    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        // 为子 view 创建默认的 LayoutParams
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }
}
