package com.zerofate.template.fragment

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zerofate.template.R
import com.zerofate.template.base.IBaseView
import kotlinx.android.synthetic.main.fragment_life_cycle.*

/**
 * A placeholder fragment containing a simple view.
 */
class LifeCycleFragment : Fragment() {

    private var baseView: IBaseView? = null
    private val localSymbol: Int

    init {
        symbol++
        localSymbol = symbol
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        baseView!!.appendResult("Fragment$localSymbol: onCreateView() -> ")
        return inflater!!.inflate(R.layout.fragment_life_cycle, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        baseView = context as IBaseView?
        baseView!!.appendResult("Fragment$localSymbol: onAttach() -> ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseView!!.appendResult("Fragment$localSymbol: onCreate() -> ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseView!!.appendResult("Fragment$localSymbol: onViewCreated() -> ")
        text.text = localSymbol.toString()
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseView!!.appendResult("Fragment$localSymbol: onActivityCreated() -> ")
    }

    override fun onStart() {
        super.onStart()
        baseView!!.appendResult("Fragment$localSymbol: onStart() -> ")
    }

    override fun onResume() {
        super.onResume()
        baseView!!.appendResult("Fragment$localSymbol: onResume() -> ")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        baseView!!.appendResult("Fragment$localSymbol: setUserVisibleHint() -> $isVisibleToUser")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        baseView!!.appendResult("Fragment$localSymbol: onHiddenChanged() -> $hidden")
    }

    override fun onPause() {
        super.onPause()
        baseView!!.appendResult("Fragment$localSymbol: onPause() -> ")
    }

    override fun onStop() {
        super.onStop()
        baseView!!.appendResult("Fragment$localSymbol: onStop() -> ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        baseView!!.appendResult("Fragment$localSymbol: onDestroyView() -> ")
    }

    override fun onDestroy() {
        super.onDestroy()
        baseView!!.appendResult("Fragment$localSymbol: onDestroy() -> ")
    }

    override fun onDetach() {
        super.onDetach()
        baseView!!.appendResult("Fragment$localSymbol: onDetach() -> ")
    }

    companion object {
        var symbol = -1
    }
}
