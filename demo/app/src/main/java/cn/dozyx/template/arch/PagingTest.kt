package cn.dozyx.template.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.utli.SampleUtil
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_paging.*
import timber.log.Timber
import java.util.concurrent.Executors

/**
 * PagedList：继承于 AbstractList，管理数据，通过 DataSource 加载数据
 */
class PagingTest : BaseActivity() {
    private val executor = Executors.newFixedThreadPool(5) { r -> Thread(r, "paging") }
    private val pagedAdapter = PagedAdapter()
    private var pagedList: PagedList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(10).setPageSize(30).build()
        rv_paging.adapter = pagedAdapter
        srl_paging.setOnRefreshListener { initPaging(config) }
    }

    private fun initPaging(config: PagedList.Config) {
        executor.execute {
            pagedList = PagedList.Builder<Long, String>(StringDataSource(srl_paging), config).setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor()).setFetchExecutor(executor).setBoundaryCallback(object : PagedList.BoundaryCallback<String>() {

            }).build()
            pagedAdapter.submitList(pagedList)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_paging
    }

}


class PagedAdapter : PagedListAdapter<String, TextViewHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.textView.text = getItem(position)
    }
}


class TextViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

private class StringDataSource(val refreshLayout: SwipeRefreshLayout) : PageKeyedDataSource<Long, String>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, String>) {
        // 加载第一页
        Timber.d("CustomDataSource.loadInitial ${params.placeholdersEnabled} ${params.requestedLoadSize} ${Thread.currentThread()}")
        var datas = SampleUtil.getStrings(params.requestedLoadSize, "init")
        callback.onResult(datas, 0, 1)
        refreshLayout.isRefreshing = false
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, String>) {
        Timber.d("CustomDataSource.loadAfter ${params.key} ${params.requestedLoadSize} ${Thread.currentThread()}")
        callback.onResult(SampleUtil.getStrings(params.requestedLoadSize, "after"), params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, String>) {
        Timber.d("CustomDataSource.loadBefore ${params.key} ${params.requestedLoadSize} ${Thread.currentThread()}")
//        callback.onResult(SampleUtil.getStrings(params.requestedLoadSize, "before"), params.key)
    }
}