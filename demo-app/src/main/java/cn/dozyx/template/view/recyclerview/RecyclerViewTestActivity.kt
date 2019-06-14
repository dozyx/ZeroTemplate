package cn.dozyx.template.view.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.blankj.utilcode.util.ToastUtils
import cn.dozyx.core.base.BaseSingleFragmentActivity
import cn.dozyx.core.utli.log.LogUtil
import cn.dozyx.constant.Shakespeare
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_recycler_view_test.*
import java.util.*

class RecyclerViewTestActivity : BaseSingleFragmentActivity() {

    override fun getFragment(startIntent: Intent): Fragment {
        return RecyclerViewFragment()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    private class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(android.R.id.text1)
    }

    class RecyclerViewFragment : Fragment() {
        private var datas: MutableList<String>? = null
        private val randomStrings: MutableList<String>
            get() {
                val newDatas = ArrayList<String>(5)
                for (i in 0..100) {
                    newDatas.add(Shakespeare.TITLES[Random().nextInt(Shakespeare.TITLES.size)])
                }
                return newDatas
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
            recycler_view.layoutManager = LinearLayoutManager(activity)
            datas = randomStrings
//            recycler_view.addItemDecoration(DividerItemDecoration(context!!, CustomItemDecoration.HORIZONTAL))
            recycler_view.addItemDecoration(DividerItemDecoration(context!!, CustomItemDecoration.VERTICAL))
            recycler_view.adapter = object : RecyclerView.Adapter<CustomViewHolder>() {

                @SuppressLint("InflateParams")
                override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                ): CustomViewHolder {
                    return CustomViewHolder(
                            LayoutInflater.from(activity).inflate(
                                    android.R.layout.simple_list_item_1,
                                    null
                            )
                    )
                }

                override fun onBindViewHolder(
                        holder: CustomViewHolder,
                        position: Int
                ) {
                    Log.d(TAG, "onBindViewHolder: $position")
                    holder.textView.text = resources.getString(R.string.network_api)
                    holder.itemView.setOnCreateContextMenuListener(this@RecyclerViewFragment)
                }

                override fun getItemCount(): Int {
                    return datas!!.size
                }
            }

            val swipeHandler = object : SwipeToDeleteCallback(context!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = recycler_view.adapter
//                    datas?.removeAt(viewHolder.adapterPosition)
//                    adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }
            ItemTouchHelper(SwipeController(context!!)).attachToRecyclerView(recycler_view)

            btn_change_data.setOnClickListener {
                datas = randomStrings
                (recycler_view.adapter as RecyclerView.Adapter<*>).notifyDataSetChanged()
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
