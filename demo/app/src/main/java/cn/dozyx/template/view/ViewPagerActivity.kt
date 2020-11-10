package cn.dozyx.template.view

import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.dozyx.template.R
import cn.dozyx.template.sample.ColorFragment
import com.blankj.utilcode.util.SizeUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_pager_with_top_tab.*
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.max

/**
 * tab 文字大小随 pager 滑动改变。这里采用的是利用 OnPageChangeListener 的方式，网上有另一种方式是利用 PageTransformer，但获取 page 对应的位置时有点麻烦。
 * 自定义 tab indicator 宽度
 */
class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_with_top_tab)

        vp_fragment.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Timber.d("ViewPagerActivity.onPageScrollStateChanged $state")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 注意理解这个方法参数。position 表示的是第一个显示的 page 的位置。比如，左滑，position 是当前显示的 page；右滑，position 是左侧即将进入的 page
                // positionOffset 表示 position 对应的 page 的偏移量，区间[0, 1]，0 表示处于当前屏幕位置，1 表示完全偏出
//                Timber.d("ViewPagerActivity.onPageScrolled $position $positionOffset $positionOffsetPixels current ${tl_top.selectedTabPosition}")

                // 滑动过程中，字体大小可能变化的 tab 为：当前 tab及其两侧的 tab。但实际滑动时，只有两个 tab 会变化，当前 tab 缩小，移入 tab 变大
                // position 并不一定表示当前 tab，右滑时为左侧要移入的 tab 的位置
                // 左滑时，position 移出屏幕，字体变小，position + 1 移入屏幕，字体变大。右滑时，position 移入屏幕，position +1 移出屏幕。
                // 即，无论左滑还是右滑，变化的两个 tab 位置都是 position 和 position + 1
                setTabTextSize(
                    position,
                    MAX_TAB_TEXT_SIZE_IN_SP - (MAX_TAB_TEXT_SIZE_IN_SP - MIN_TAB_TEXT_SIZE_IN_SP) * positionOffset
                )
                // position + 1 字体大小变化与 position 相反
                setTabTextSize(
                    position + 1,
                    MAX_TAB_TEXT_SIZE_IN_SP - (MAX_TAB_TEXT_SIZE_IN_SP - MIN_TAB_TEXT_SIZE_IN_SP) * (1 - positionOffset)
                )
            }

            override fun onPageSelected(position: Int) {
                Timber.d("ViewPagerActivity.onPageSelected")
                // 并不是在完全停止滚动之后才触发
                // setAdapter 并不会导致这个回调
//                setTabTextSize(position, MAX_TAB_TEXT_SIZE_IN_SP)
            }
        })
        /*vp_fragment.setPageTransformer(false, object : ViewPager.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                // -1 0 1
                // 如何确定这个 page 处于哪个 position？重写 PagerAdapter 的 getItemPosition() 方法
                val itemPosition = (vp_fragment.adapter as FragmentPagerAdapter).getItemPosition(page)
                // -1 到  1 变化

            }
        })*/
        vp_fragment.adapter = object :
                FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                return ColorFragment.newInstance("$position")
            }

            override fun getCount(): Int {
                return 3
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return "标题$position"
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val obj = super.instantiateItem(container, position)
                var arguments = (obj as Fragment).arguments
                if (arguments == null) {
                    arguments = Bundle()
                }
                arguments.putInt("position", position)
                return obj
            }

            override fun getItemPosition(`object`: Any): Int {

                return (`object` as Fragment).arguments?.getInt("position") ?: 0
            }
        }
        tl_top.setupWithViewPager(vp_fragment)
        tl_top.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
//                setTabTextSize(tab.position, MIN_TAB_TEXT_SIZE_IN_SP)
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
            }
        })

        for (i in 0 until tl_top.tabCount) {
            tl_top.getTabAt(i)?.setCustomView(R.layout.tab_custom_view)
        }
        setTabTextSize(0, MAX_TAB_TEXT_SIZE_IN_SP)
    }

    private fun setTabTextSize(tabPosition: Int, newTextSize: Float) {
        val textView =
            tl_top.getTabAt(tabPosition)?.customView?.findViewById<TextView>(android.R.id.text1)
//        Timber.d("ViewPagerActivity.setTabTextSize $tabPosition $newTextSize")
        textView?.let {
            val newTextSizeInPx = SizeUtils.sp2px(newTextSize)
            if (abs(it.textSize - newTextSizeInPx) >= 1) {
                it.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSizeInPx.toFloat())
            }
        }
    }

    companion object {
        private const val MIN_TAB_TEXT_SIZE_IN_SP = 14f
        private const val MAX_TAB_TEXT_SIZE_IN_SP = 24f
    }
}
