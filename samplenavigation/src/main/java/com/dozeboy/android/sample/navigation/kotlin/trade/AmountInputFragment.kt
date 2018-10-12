package com.dozeboy.android.sample.navigation.kotlin.trade

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

class AmountInputFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_amount_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start_read_card.setOnClickListener{
            findNavController().navigate(R.id.action_amountInputFragment_to_readCardFragment)
        }
    }

}