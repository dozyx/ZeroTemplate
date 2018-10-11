package com.dozeboy.android.sample.navigation.kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dozeboy.android.sample.navigation.R

/**
 * @author dozeboy
 * @date 2018/7/19
 */
class HomeFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_trade.setOnClickListener {
//            Navigation.createNavigateOnClickListener(R.id.amount_input_fragment)
            findNavController().navigate(R.id.trade_activity)
        }

    }

    companion object {
        fun newInstance() = HomeFragment
    }
}