package cn.dozyx.template.view.touch.conflict

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.utli.SampleUtil
import cn.dozyx.template.R
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author dozyx
 * @date 2019-10-29
 */
class TouchConflictActivity : BaseActivity() {
    private lateinit var listContainer: HorizontalScrollViewEx
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        listContainer = findViewById(R.id.list_container)
        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()
        for (i in 0 until 3) {
            val layout = LayoutInflater.from(this).inflate(R.layout.sample_list, listContainer, false) as ViewGroup
            layout.layoutParams.width = screenWidth
            layout.layoutParams.height = screenHeight
            val textTitle = layout.findViewById<TextView>(R.id.title)
            textTitle.text = "Page: $i"
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0))
            listContainer.addView(layout)
            createList(layout)
        }

    }

    private fun createList(layout: ViewGroup) {
        val recyclerView = layout.findViewById<RecyclerView>(R.id.list)
        var adapter = object : BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1) {
            override fun convert(helper: BaseViewHolder, item: String) {
                helper.setText(android.R.id.text1, item)
            }
        }
        recyclerView.adapter = adapter
        adapter.setNewInstance(SampleUtil.strings)
    }


    override fun getLayoutId() = R.layout.activity_touch_conflict
}