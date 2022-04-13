package cn.dozyx.template.view.recyclerview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import cn.dozyx.core.utli.SampleUtil
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.dialog_recycler_view.*

class RecyclerViewPopTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("wrap_content") {
            override fun run() {
                CustomDialog(this@RecyclerViewPopTest).show()
            }
        })
    }
}

private class CustomDialog(context: Context) :
    Dialog(context, R.style.Theme_AppCompat_Dialog_NoTitle) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_recycler_view)
        rv.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_simple_text) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tv_text, item)
            }
        }.apply {
            setNewInstance(SampleUtil.getStrings(5))
        }

        window?.attributes?.also {
            it.width = WindowManager.LayoutParams.WRAP_CONTENT
            it.height = WindowManager.LayoutParams.MATCH_PARENT
            window?.attributes = it
        }
    }

}