package com.dozeboy.android.sample.navigation.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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