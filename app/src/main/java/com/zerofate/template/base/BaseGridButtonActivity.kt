package com.zerofate.template.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.zerofate.template.R
import kotlinx.android.synthetic.main.main_grid_button.*

abstract class BaseGridButtonActivity : AppCompatActivity() {

    val actions = ArrayList<Action>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_grid_button)
        recycler_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recycler_view.adapter = object : RecyclerView.Adapter<ButtonViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ButtonViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_single_button,
                        null
                    )
                )

            override fun getItemCount() = actions.size

            override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
                holder.button?.text = actions[position].label
                holder.button?.setOnClickListener{
                    actions[position].task.run()
                }
            }
        }
    }

    protected fun addButton(text: String, task: Runnable) {
        actions.add(Action(text,task))
        recycler_view.adapter.notifyItemInserted(actions.size)
    }

    protected fun appendLog(log: String) {
        var previousLog = text_log!!.text as String
        if (TextUtils.isEmpty(previousLog)) {
            previousLog = ""
        }
        val builder = StringBuilder()
        builder.append(log).append("\n").append(previousLog)
        text_log!!.text = builder.toString()
    }

    class ButtonViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val button = itemView?.findViewById<TextView>(R.id.btn_simple)
    }

    class Action(val label: String, val task: Runnable)


}
