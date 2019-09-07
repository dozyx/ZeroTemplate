package cn.dozyx.template.view.practice.qijian

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.dozyx.template.view.practice.qijian.view.*

import com.blankj.utilcode.util.LogUtils

import timber.log.Timber

import java.lang.reflect.InvocationTargetException
import java.util.ArrayList

/**
 * 《Android自定义控件开发入门与实战》练习
 * Create by timon on 2019/5/11
 */
class BookPracticeActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager = object : ViewPager(this) {
            override fun onTouchEvent(ev: MotionEvent): Boolean {
                Timber.d("BookPracticeActivity.onTouchEvent")
                return super.onTouchEvent(ev)
            }

            override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
                val intercepted = super.onInterceptTouchEvent(ev);
                Timber.d("BookPracticeActivity.onInterceptTouchEvent ${ev?.action?.let { MotionEvent.actionToString(it) }} $intercepted")
                return intercepted
            }
        }
        prepareData()
        setupViewPager(viewPager!!)
        setContentView(viewPager)
    }

    private fun prepareData() {
        views.add(AvatarView::class.java)
        views.add(TelescopeView::class.java)
        views.add(BitmapShaderView::class.java)
        views.add(BitmapShadowView::class.java)
        views.add(BlurMaskFilterView::class.java)
        views.add(ShadowLayerView::class.java)
        views.add(AnimWaveView::class.java)
//        views.add(GestureTrackView::class.java) // 会滑动冲突
        views.add(BazierTest::class.java)
        views.add(DrawTextView::class.java)
        views.add(CustomView6::class.java)
        views.add(CustomView5::class.java)
        views.add(CustomView4::class.java)
        views.add(CustomView3::class.java)
        views.add(CustomView2::class.java)
        views.add(CustomView1::class.java)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPager.adapter = object : PagerAdapter() {

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                LogUtils.d(container)
                var pageView: View? = null
                try {
                    pageView = views[position].getDeclaredConstructor(Context::class.java).newInstance(
                            this@BookPracticeActivity) as View
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                }

                container.addView(pageView)
                return pageView!!
            }

            override fun getCount(): Int {
                return views.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
    }

    companion object {
        private val views = ArrayList<Class<*>>()
    }
}
