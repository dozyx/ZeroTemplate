package cn.dozyx.template.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class FragmentLifeCycleActivity : BaseTestActivity() {
    override fun initActions() {

    }

    private var topFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("FragmentLifeCycleActivity.onCreate ${supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)}")
        addButton("add", Runnable {
            addFragment(newFragmentInstant())
        })
        addButton("add with tag", Runnable {
            val fragment = newFragmentInstant()
            Timber.d("add with tag $fragment")
            addFragment(FRAGMENT_TAG, fragment, false)
        })

        addButton("replace", Runnable {
            replaceFragment(newFragmentInstant())
        })

        addButton("stack add", Runnable {
            addFragment(newFragmentInstant(), true)
        })

        addButton("stack replace", Runnable {
            replaceFragment(newFragmentInstant(), true)
        })

        addButton("hide", Runnable {
            hideFragment(topFragment!!)
        })

        addButton("show", Runnable {
            showFragment(topFragment!!)
        })

        addButton("remove", Runnable {
            removeFragment(topFragment!!)
        })
    }

    private fun newFragmentInstant(): Fragment {
        topFragment = LifeCycleFragment()
        return topFragment!!
    }

    companion object {
        private const val FRAGMENT_TAG = "fragment_tag"
    }


}
