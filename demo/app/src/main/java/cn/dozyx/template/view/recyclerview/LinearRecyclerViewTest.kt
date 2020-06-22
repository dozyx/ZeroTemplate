package cn.dozyx.template.view.recyclerview

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.core.base.BaseListActivity
import cn.dozyx.template.base.BaseTestActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class LinearRecyclerViewTest : BaseListActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter
    }


}