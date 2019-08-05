package cn.dozyx.navigation.kotlin.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dozeboy.android.sample.navigation.R
import kotlinx.android.synthetic.main.fragment_trade_sign.*

/**
 * @author dozeboy
 * @date 2018/7/20
 */

class TradeSignFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trade_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_trade_sign_confirm.setOnClickListener {
            activity?.finish()
        }
    }
}