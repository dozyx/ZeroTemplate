package cn.dozyx.template.view.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.dozyx.constant.Shakespeare
import cn.dozyx.core.base.BaseSingleFragmentActivity
import cn.dozyx.core.utli.log.LogUtil
import cn.dozyx.template.R
import cn.dozyx.template.view.recyclerview.adapter.QuickAdapter
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_recycler_view_test.*
import java.util.*

class RecyclerViewTestActivity : BaseSingleFragmentActivity() {

    override fun getFragment(startIntent: Intent): Fragment {
        return RecyclerViewFragment()
    }

    class RecyclerViewFragment : Fragment() {
        private val randomStrings: MutableList<String>
            get() {
                val newDatas = ArrayList<String>(5)
                for (i in 0..100) {
                    newDatas.add(Shakespeare.TITLES[Random().nextInt(Shakespeare.TITLES.size)])
                }
                return newDatas
            }
        private val adapter = object :QuickAdapter<String>(){
            override fun getLayoutId(viewType: Int): Int {
                return android.R.layout.simple_list_item_1
            }

            override fun convert(holder: VH, data: String, position: Int) {
                holder.setText(android.R.id.text1,data)
                holder.itemView.setOnCreateContextMenuListener(this@RecyclerViewFragment)
                (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = 100
            }
        }

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
            LogUtil.d("onCreateView: ")
            return inflater.inflate(R.layout.activity_recycler_view_test, container, false)
        }

        override fun onContextItemSelected(item: MenuItem): Boolean {
            ToastUtils.showShort(item.title)
            return false
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            super.onCreateContextMenu(menu, v, menuInfo)
            val menuInflater = activity?.menuInflater
            menuInflater?.inflate(R.menu.context_menu, menu)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.addItemDecoration(CustomItemDecoration(context!!, CustomItemDecoration.VERTICAL))
            recycler_view.adapter = adapter
            adapter.notifyDataSetChanged()
            adapter.setData(randomStrings)
            val swipeHandler = object : SwipeToDeleteCallback(context!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = recycler_view.adapter
//                    datas?.removeAt(viewHolder.adapterPosition)
//                    adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }
            ItemTouchHelper(SwipeController(context!!)).attachToRecyclerView(recycler_view)

            btn_change_data.setOnClickListener {
                adapter.setData(randomStrings)
                (recycler_view.adapter as RecyclerView.Adapter<*>).notifyDataSetChanged()
            }
            btn_notify.setOnClickListener {
//                adapter.notifyItemChanged(2)
                adapter.notifyItemChanged(2,"1111")
                adapter.notifyItemChanged(2,"2222")
                adapter.notifyItemChanged(3,"2222")
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            LogUtil.d("onDestroyView: ")
        }

        override fun onDestroy() {
            super.onDestroy()
            LogUtil.d("onDestroy: ")
        }

        override fun onDetach() {
            super.onDetach()
            LogUtil.d("onDetach: ")
        }
    }

    companion object {
        private const val TAG = "RecyclerViewTestActivit"
    }


}
