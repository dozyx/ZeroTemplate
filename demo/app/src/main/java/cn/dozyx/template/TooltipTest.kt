package cn.dozyx.template

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.base.ListFragment
import cn.dozyx.core.utli.SampleUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.test_tooltip.*
import timber.log.Timber

/**
 * 方案一: 直接把引导放在 item 上，通过 clipChildren 避免遮挡。
 *        但是在 viewpager 中实现，如果引导超过 viewpager 顶部的话，不好处理，ViewPager2 的嵌套大概是
 *        ViewPager2 - RecyclerView - FrameLayout - Fragment view，需要遍历，并且 view add 的时机不好把握，
 *        容易出现 parent 为 null 的情况
 */
class TooltipTest : BaseActivity() {
    override fun getLayoutId() = R.layout.test_tooltip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vp_fragment.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return TooltipListFragment()
            }
        }
        vp_fragment.clipChildren = false
        (vp_fragment.getChildAt(0) as RecyclerView).clipChildren = false
//        ((vp_fragment.getChildAt(0) as RecyclerView).getChildAt(0) as ViewGroup).clipChildren = false
        (vp_fragment.parent as ViewGroup).clipChildren = false

        TabLayoutMediator(tb, vp_fragment) { tab, position ->
            tab.text = "tab $position"
        }.attach()
    }
}

class TooltipListFragment : ListFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.clipChildren = false
        setParentClipChildren(recyclerView, false)
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                Timber.d("TooltipListFragment.onScrolled ")
            }
        })
    }

    override fun <T : RecyclerView.Adapter<RecyclerView.ViewHolder>> provideAdapter(): T {
        return object : BaseQuickAdapter<String, BaseViewHolder>(
            R.layout.item_text_with_tooltip,
            SampleUtil.strings
        ) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tv_text, item)
                if (holder.adapterPosition == 0){
                    holder.itemView.viewTreeObserver.addOnGlobalLayoutListener {
                        Timber.d("TooltipListFragment item onGlobalLayout")
                    }
                    holder.itemView.viewTreeObserver.addOnPreDrawListener {
                        Timber.d("TooltipListFragment item onPreDraw")
                        return@addOnPreDrawListener true
                    }
                    holder.itemView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                        Timber.d("TooltipListFragment item onLayoutChange top: $top")
                    }
                }
            }
        } as T
    }

    fun setParentClipChildren(child: View, clip: Boolean) {
        var parent = child.parent
        while (parent != null && parent is ViewGroup) {
            val parentGroup = parent
            if (parentGroup.clipChildren != clip) {
                parentGroup.clipChildren = clip
            }
            parent = parent.parent
        }
    }
}