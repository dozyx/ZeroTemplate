package cn.dozyx.template.pager

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.utli.util.ColorUtil
import cn.dozyx.template.R
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.common_pager.*
import timber.log.Timber
import kotlin.math.floor
import kotlin.math.min

/**
 * @author dozyx
 * @date 2019-12-29
 */
class PagerTransformTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager.adapter = object : PagerAdapter() {

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val textView = TextView(container.context)
                textView.text = "$position"
                textView.textSize = 48f
                textView.gravity = Gravity.CENTER
                container.addView(textView)
                textView.setBackgroundColor(ColorUtil.random())
                return textView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return 10
            }
        }

        pager.offscreenPageLimit = 3
        pager.setPageTransformer(false) { page, position ->
            // 术语基准参考点：当前 item 的中点为 0，左侧 item 中点为 -1，右侧 item 中点为 1
            // 参考点的位置是固定的，滑动过程中 position 表示的是 page 在基准线上的位置
            Timber.d("PageTransformer $position")
            val offscreenPageLimit = pager.offscreenPageLimit // 显示卡片的数量
            val pagerWidth: Int = pager.width
            val CENTER_PAGE_SCALE = 0.8f // 缩放比例

            // 计算出下面的 item 偏出的距离
            val horizontalOffsetBase: Float = (pagerWidth - pagerWidth * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + SizeUtils.dp2px(15f)

            // 控制显示的 item。超出 limit，或者是左侧的 item 不显示
            // 在这个例子中，position <= -1 好像是多余的，因为每个 item 都占了整个屏幕，所以 -1 位置的 item 不会被展示
            if (position >= offscreenPageLimit || position <= -1) {
                page.visibility = View.GONE
            } else {
                page.visibility = View.VISIBLE
            }

            if (position >= 0) {
                // 将 item 平移到当前 item 位置，并设置适当的偏移
                val translationX = (horizontalOffsetBase - page.width) * position
                page.translationX = translationX
            }
            if (position > -1 && position < 0) {
                // 将要移除的 item 设置旋转和透明度
                val rotation = position * 30
                page.rotation = rotation
                page.alpha = (position * position * position + 1)
            } else if (position > offscreenPageLimit - 1) {
                // 新加入底部的 item 设置透明度
                page.alpha = ((1 - position + floor(position.toDouble())).toFloat())
            } else {
                // 已显示在底部 item
                page.rotation = 0f
                page.alpha = 1f
            }

            // 缩放
            if (position == 0f) {
                page.scaleX = CENTER_PAGE_SCALE
                page.scaleY = CENTER_PAGE_SCALE
            } else {
                val scaleFactor = min(CENTER_PAGE_SCALE - position * 0.1f, CENTER_PAGE_SCALE)
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }

            ViewCompat.setElevation(page, (offscreenPageLimit - position) * 5)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_pager
    }
}