package cn.dozyx.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * @author dozeboy
 * @date 2018/7/19
 */

class DashboardFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return TextView(context).apply { text = "Dashboard" }
    }

    companion object {
        fun newInstance() = DashboardFragment
    }
}