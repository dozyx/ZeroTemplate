package cn.dozyx.template.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.utli.SampleUtil
import cn.dozyx.template.R
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.common_list.*
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * PagedList：继承于 AbstractList，管理数据，通过 DataSource 加载数据
 */
class PagingTest : BaseActivity() {
    val executor = Executors.newFixedThreadPool(5)
    private val pagedAdapter = PagedAdapter()
    private var pagedList: PagedList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(10).setPageSize(20).build()
        rv_common.adapter = pagedAdapter
        executor.execute {
            pagedList = PagedList.Builder<Long, String>(StringDataSource(), config).setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor()).setFetchExecutor(executor).setBoundaryCallback(object : PagedList.BoundaryCallback<String>() {

            }).build()
        }
        pagedAdapter.submitList(pagedList)
    }

    override fun getLayoutId(): Int {
        return R.layout.common_list
    }

    fun newData(size: Int): List<String> {
        val datas = ArrayList<String>()
        for (index in 0 until size) {
            datas.add((pagedAdapter.itemCount + index).toString())
        }
        return datas
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

class StringDataSource : PageKeyedDataSource<Long, String>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, String>) {
        // 加载第一页
        Timber.d("CustomDataSource.loadInitial ${params.placeholdersEnabled} ${params.requestedLoadSize}")
        var datas = SampleUtil.getStrings(params.requestedLoadSize)
        callback.onResult(datas, 0, datas.size, 0, 1)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, String>) {
        Timber.d("CustomDataSource.loadAfter")
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, String>) {
        Timber.d("CustomDataSource.loadBefore")
    }
}