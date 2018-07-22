package com.dozeboy.android.sample.navigation.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dozeboy.android.sample.navigation.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author dozeboy
 * @date 2018/7/19
 */
class HomeFragment : Fragment() {

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