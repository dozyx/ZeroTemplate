package cn.dozyx.template.view.recyclerview

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * 思考：RecyclerView 并不会一次性显示全部的数据，只是显示了当前屏幕的数据，这就需要计算哪些数据需要
 * 进行显示并添加 view，并且当屏幕发生滚动时，需要检测是否需要需要显示新数据以及对不再显示数据的view的
 * 处理
 */
class CustomLayoutManager : RecyclerView.LayoutManager() {


    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        // adapter 数据改变或 adapter 被替换时调用
        Timber.d("CustomLayoutManager.onLayoutChildren")

    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        // 为子 view 创建默认的 LayoutParams
        // 该方法是  LayoutManager 的唯一抽象方法
        Timber.d("CustomLayoutManager.generateDefaultLayoutParams")
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }
}
