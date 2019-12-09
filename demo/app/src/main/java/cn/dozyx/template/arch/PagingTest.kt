package cn.dozyx.template.arch

import android.os.Bundle
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * PagedList：继承于 AbstractList，管理数据，通过 DataSource 加载数据
 */
class PagingTest : BaseActivity() {
    val executor = Executors.newFixedThreadPool(5)
    lateinit var pagedList: PagedList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(10).setPageSize(20).build()

    }

    override fun getLayoutId(): Int {
        return R.layout.common_list
    }
}

class CustomDataSource : PageKeyedDataSource<Long, String>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, String>) {
        // 加载第一页
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, String>) {

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, String>) {

    }
}