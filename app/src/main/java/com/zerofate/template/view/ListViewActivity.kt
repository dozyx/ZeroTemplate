package com.zerofate.template.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.zerofate.andoroid.data.Shakespeare
import com.zerofate.androidsdk.base.BaseSingleFragmentActivity
import com.zerofate.template.R
import com.zerofate.template.databinding.ActivityListViewBinding
import kotlinx.android.synthetic.main.activity_list_view.*
import java.util.*
import kotlin.collections.ArrayList

class ListViewActivity : BaseSingleFragmentActivity() {
    override fun getFragment(): Fragment {
        return SimpleFragemnt()
    }

    override fun onDestroy() {
        Log.d("test","onDestroy activity")
        super.onDestroy()
    }

    class SimpleFragemnt : Fragment() {
        lateinit var adapter: ArrayAdapter<String>
        val str: String = "123"
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
//            adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, data)
            adapter = object :
                ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, data) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    Log.d("test", "$position")
                    val textView = LayoutInflater.from(activity)
                        .inflate(android.R.layout.simple_list_item_1, null)
                        .findViewById<TextView>(android.R.id.text1)
                    textView.text = resources.getString(R.string.network_api)
                    return textView
                }
            }
            btn_list.adapter = adapter
        }

        override fun onDestroyView() {
            Log.d("test", "onDestroyView")
            super.onDestroyView()
        }

        override fun onDestroy() {
            Log.d("test", "onDestroy")
            super.onDestroy()
        }

        fun onBtnChangeData(view: View) {
            adapter.add(Random().nextInt().toString());
        }
    }
}
