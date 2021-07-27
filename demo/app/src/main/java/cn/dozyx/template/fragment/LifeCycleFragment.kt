package cn.dozyx.template.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import cn.dozyx.template.R
import cn.dozyx.template.base.IBaseView
import kotlinx.android.synthetic.main.fragment_life_cycle.*
import timber.log.Timber

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
        Timber.d("LifeCycleFragment.onCreateView Fragment$localSymbol")
        return inflater!!.inflate(R.layout.fragment_life_cycle, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseView = context as IBaseView?
        baseView!!.appendResult("Fragment$localSymbol: onAttach() -> ")
        Timber.d("LifeCycleFragment.onAttach Fragment$localSymbol")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseView!!.appendResult("Fragment$localSymbol: onCreate() -> ")
        Timber.d("LifeCycleFragment.onCreate Fragment$localSymbol")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseView!!.appendResult("Fragment$localSymbol: onViewCreated() -> ")
        text.text = localSymbol.toString()
        Timber.d("LifeCycleFragment.onViewCreated Fragment$localSymbol")
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseView!!.appendResult("Fragment$localSymbol: onActivityCreated() -> ")
        Timber.d("LifeCycleFragment.onActivityCreated Fragment$localSymbol")
    }

    override fun onStart() {
        super.onStart()
        baseView!!.appendResult("Fragment$localSymbol: onStart() -> ")
        Timber.d("LifeCycleFragment.onStart Fragment$localSymbol")
    }

    override fun onResume() {
        super.onResume()
        baseView!!.appendResult("Fragment$localSymbol: onResume() -> ")
        Timber.d("LifeCycleFragment.onResume Fragment$localSymbol")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        baseView!!.appendResult("Fragment$localSymbol: setUserVisibleHint() -> $isVisibleToUser")
        Timber.d("LifeCycleFragment.setUserVisibleHint Fragment$localSymbol")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        baseView!!.appendResult("Fragment$localSymbol: onHiddenChanged() -> $hidden")
        Timber.d("LifeCycleFragment.onHiddenChanged Fragment$localSymbol")
    }

    override fun onPause() {
        super.onPause()
        baseView!!.appendResult("Fragment$localSymbol: onPause() -> ")
        Timber.d("LifeCycleFragment.onPause Fragment$localSymbol")
    }

    override fun onStop() {
        super.onStop()
        baseView!!.appendResult("Fragment$localSymbol: onStop() -> ")
        Timber.d("LifeCycleFragment.onStop Fragment$localSymbol")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        baseView!!.appendResult("Fragment$localSymbol: onDestroyView() -> ")
        Timber.d("LifeCycleFragment.onDestroyView Fragment$localSymbol")
    }

    override fun onDestroy() {
        super.onDestroy()
        baseView!!.appendResult("Fragment$localSymbol: onDestroy() -> ")
        Timber.d("LifeCycleFragment.onDestroy Fragment$localSymbol")
    }

    override fun onDetach() {
        super.onDetach()
        baseView!!.appendResult("Fragment$localSymbol: onDetach() -> ")
        Timber.d("LifeCycleFragment.onDetach Fragment$localSymbol")
    }

    companion object {
        var symbol = -1
    }
}
