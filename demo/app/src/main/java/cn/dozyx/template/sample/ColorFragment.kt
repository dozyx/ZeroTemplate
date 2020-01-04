package cn.dozyx.template.sample

import android.os.Bundle
import android.view.View
import cn.dozyx.core.base.BaseFragment
import cn.dozyx.core.utli.util.ColorUtil
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.fragment_color.*

/**
 * 随机颜色背景 + 简单 text
 * @author dozyx
 * @date 2020-01-04
 */
class ColorFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fl_root.setBackgroundColor(ColorUtil.random())
        tv_content.text = arguments?.getString(ARG_TITLE)
    }

    override fun getLayoutId() = R.layout.fragment_color

    companion object {
        private const val ARG_TITLE = "title"
        fun newInstance(title: String): ColorFragment {
            val fragment = ColorFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }


}