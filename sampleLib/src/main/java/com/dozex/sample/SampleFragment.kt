package com.dozex.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_sample.*

/**
 * @author dozeboy
 * @date 2019-10-01
 */
class SampleFragment : Fragment() {
    private var title: String? = "Default"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            title = getString(ARG_TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_title.text = title
    }

    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(title: String): SampleFragment {

            val args = Bundle()
            args.putString(ARG_TITLE, title)
            val fragment = SampleFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
