package com.zerofate.template.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.dozeboy.android.core.utli.log.ZLog
import com.zerofate.android.data.Shakespeare
import com.zerofate.androidsdk.base.BaseSingleFragmentActivity
import com.zerofate.template.R
import kotlinx.android.synthetic.main.activity_list_view.*
import java.util.*
import kotlin.collections.ArrayList

class ListViewActivity : BaseSingleFragmentActivity() {

    override fun onDestroy() {
        super.onDestroy()
        ZLog.d("onDestroy: ")
    }

    override fun onDetachedFromWindow() {
        ZLog.d("onDetachedFromWindow: ")
        super.onDetachedFromWindow()
    }

    override fun getFragment(startIntent: Intent?): Fragment {
        return SimpleFragemnt()
    }

    class SimpleFragemnt : Fragment() {
        lateinit var adapter: ArrayAdapter<String>
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.activity_list_view, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val data = ArrayList<String>()
            for (i in 1..20) {
                for (title in Shakespeare.TITLES) {
                    data.add(title)
                }
            }
            adapter = object :
                ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, data) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    ZLog.d("getView: $position")
                    val viewHolder: ViewHolder
                    if (convertView != null) {
                        viewHolder = convertView.tag as ViewHolder
                    } else {
                        viewHolder = ViewHolder()
                        viewHolder.textView = LayoutInflater.from(activity)
                            .inflate(android.R.layout.simple_list_item_1, null)
                            .findViewById<TextView>(android.R.id.text1)
                        viewHolder.textView.tag = viewHolder
                    }
                    viewHolder.textView.text = resources.getString(R.string.network_api)
                    return viewHolder.textView
                }

                inner class ViewHolder {
                    lateinit var textView: TextView
                }
            }
            (btn_list as ListView).adapter = adapter
        }

        override fun onDestroyView() {
            ZLog.d("onDestroyView: ")
            super.onDestroyView()
        }

        override fun onDestroy() {
            ZLog.d("onDestroy: ")
            super.onDestroy()
        }

        override fun onDetach() {
            ZLog.d("onDetach: ")
            super.onDetach()
        }

        fun onBtnChangeData(view: View) {
            adapter.add(Random().nextInt().toString());
        }
    }
}
