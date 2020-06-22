package cn.dozyx.core.base

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.zerofate.android.core.R
import kotlinx.android.synthetic.main.common_list.*

abstract class BaseListActivity : BaseActivity() {
    lateinit var listAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    override fun getLayoutId() = R.layout.common_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = createAdapter()
        rv_list.apply {
            layoutManager = createLayoutManager()
            adapter = listAdapter
        }
    }

    private fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    abstract fun createAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>
}