package cn.dozyx.template.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Create by timon on 2019/10/28
 */
class ListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recyclerView = RecyclerView(context!!)
        recyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        return recyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = object : BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1) {
            override fun convert(helper: BaseViewHolder, item: String?) {
                helper.setText(android.R.id.text1, item)
            }
        }
        recyclerView.adapter = adapter
        adapter.setNewData(getStrings(30))
    }
}
