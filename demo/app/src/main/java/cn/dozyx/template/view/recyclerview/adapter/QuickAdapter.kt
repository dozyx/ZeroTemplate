package cn.dozyx.template.view.recyclerview.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.util.*

/**
 * 万能适配器
 */
abstract class QuickAdapter<T> : RecyclerView.Adapter<QuickAdapter.VH> {

    private var datas: List<T>? = null



    constructor(datas: List<T>?) : super() {
        this.datas = datas
    }

    constructor() : super()

    fun setData(data:List<T>) {
        datas = data
        notifyDataSetChanged()
    }

    abstract fun getLayoutId(viewType: Int): Int

    abstract fun convert(holder: VH, data: T, position: Int);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.get(parent, getLayoutId(viewType))
    }

    override fun getItemCount(): Int = if (datas == null) 0 else datas!!.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        convert(holder, datas!![position], position)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        Timber.d("QuickAdapter.onBindViewHolder $position payloads $payloads")
    }

    class VH private constructor(private val convertView: View) : RecyclerView.ViewHolder(convertView) {

        private val views = SparseArray<View>()

        @Suppress("UNCHECKED_CAST")
        fun <T : View> getView(id: Int): T {
            var v = views.get(id)
            if (v == null) {
                v = convertView.findViewById(id)
                views.put(id, v)
            }
            return v as T
        }

        fun setText(id: Int, text: String) {
            val textView: TextView = getView(id)
            textView.text = text
        }

        companion object {
            fun get(parent: ViewGroup, layoutId: Int): VH {
                val convertView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
                return VH(convertView)
            }
        }

    }
}
