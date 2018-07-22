package com.dozeboy.android.sample.navigation.kotlin.trade

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dozeboy.android.sample.navigation.R
import kotlinx.android.synthetic.main.fragment_amount_input.*

/**
 * @author dozeboy
 * @date 2018/7/19
 */

class AmountInputFragment : Fragment() {

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