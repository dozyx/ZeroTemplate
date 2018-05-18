package com.zerofate.template.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.zerofate.template.R
import com.zerofate.template.databinding.ActivityListViewBinding
import java.util.*

class ListViewActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityListViewBinding
    var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_view)
        dataBinding.btnList.adapter = adapter
    }

    fun onBtnChangeData(view: View) {
        adapter.add(Random().nextInt().toString());
    }
}
