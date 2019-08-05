package cn.dozyx.navigation.kotlin.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dozeboy.android.sample.navigation.R
import kotlinx.android.synthetic.main.fragment_read_card.*

/**
 * @author dozeboy
 * @date 2018/7/20
 */

class ReadCardFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_read_card_finish.setOnClickListener {
            findNavController().navigate(R.id.action_readCardFragment_to_tradeSignFragment)
        }
    }
}