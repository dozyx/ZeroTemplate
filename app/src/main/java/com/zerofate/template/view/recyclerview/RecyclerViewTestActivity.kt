package com.zerofate.template.view.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import com.dozeboy.android.core.utli.log.ZLog
import com.zerofate.android.data.Shakespeare
import com.zerofate.androidsdk.base.BaseSingleFragmentActivity
import com.zerofate.template.R
import kotlinx.android.synthetic.main.activity_recycler_view_test.*
import java.util.*

class RecyclerViewTestActivity : BaseSingleFragmentActivity() {


    override fun getFragment(startIntent: Intent?): androidx.fragment.app.Fragment {
        return RecyclerViewFragment()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    private class CustomViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.text)
    }

    class RecyclerViewFragment : androidx.fragment.app.Fragment() {
        private var datas: List<String>? = null
        private val randomStrings: List<String>
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
            ZLog.d("onCreateView: ")
            return inflater.inflate(R.layout.activity_recycler_view_test, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
            datas = randomStrings
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(context?.resources?.getDrawable(R.drawable.abc_list_divider_material)!!)
            recycler_view.addItemDecoration(itemDecoration)
            recycler_view.adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<CustomViewHolder>() {
                @SuppressLint("InflateParams")
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): CustomViewHolder {
                    return CustomViewHolder(
                        LayoutInflater.from(activity).inflate(
                            R.layout.item_text,
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
                }

                override fun getItemCount(): Int {
                    return datas!!.size
                }
            }

            btn_change_data.setOnClickListener {
                datas = randomStrings
                (recycler_view.adapter as RecyclerView.Adapter<*>).notifyDataSetChanged()
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            ZLog.d("onDestroyView: ")
        }

        override fun onDestroy() {
            super.onDestroy()
            ZLog.d("onDestroy: ")
        }

        override fun onDetach() {
            super.onDetach()
            ZLog.d("onDetach: ")
        }
    }

    companion object {
        private const val TAG = "RecyclerViewTestActivit"
    }


}
