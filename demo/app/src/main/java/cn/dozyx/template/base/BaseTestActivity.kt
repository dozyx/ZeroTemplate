package cn.dozyx.template.base

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cn.dozyx.template.databinding.ActivityBaseTestBinding
import timber.log.Timber

/**
 * 添加按钮及其点击事件，显示日志，添加一个 fragment 页
 */
abstract class BaseTestActivity : AppCompatActivity(), IBaseView {
    private val views = HashMap<String, View>()
    protected val binding by lazy { ActivityBaseTestBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActions()
    }

    abstract fun initActions()

    protected fun addAction(action: Action) {
        val button = addButton(action.name, action)
        views[action.name] = button
    }

    protected fun addAction(name: String,block: () -> Unit) {
        val button = addButton(name, block)
        views[name] = button
    }

    protected fun addButton(text: String, task: Runnable): Button {
        val button = Button(this)
        button.text = text
        button.textSize = 12f
        button.setOnClickListener {
            Timber.d("Action execute: $text")
            task.run()
        }
        binding.flexBox.addView(button)
        return button
    }

    protected fun addButton(text: String, block: () -> Unit): Button {
        val button = Button(this)
        button.text = text
        button.textSize = 12f
        button.setOnClickListener {
            Timber.d("Action execute: $text")
            block.invoke()
        }
        binding.flexBox.addView(button)
        return button
    }

    fun findViewByActionName(name: String): View? = views[name]

    protected fun setButtonOnClickListener(view: View, task: Runnable) {
        view.setOnClickListener { task.run() }
    }

    override fun appendResult(log: String?) {
        if (log == null) {
            return
        }
        runOnUiThread {
            var previousLog = binding.textLog.text as String
            if (TextUtils.isEmpty(previousLog)) {
                previousLog = ""
            }
            val builder = StringBuilder()
            builder.append(log).append("\n").append(previousLog)
            binding.textLog.text = builder.toString()
        }
    }

    override fun showResult(text: String?) {
        binding.textLog.text = text
    }

    protected fun addFragment(fragment: Fragment) {
        addFragment(fragment, false)
    }

    protected fun replaceFragment(fragment: Fragment) {
        replaceFragment(fragment, false)
    }

    protected fun addFragment(fragment: Fragment, addToStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction().add(binding.frameContainer.id, fragment)
        if (addToStack) {
            fragmentTransaction.addToBackStack("").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        }
        fragmentTransaction.commit()
    }

    protected fun addFragment(tag: String, fragment: Fragment, addToStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction().add(binding.frameContainer.id, fragment, tag)
        if (addToStack) {
            fragmentTransaction.addToBackStack("").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        }
        fragmentTransaction.commit()
    }

    protected fun replaceFragment(fragment: Fragment, addToStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction().replace(binding.frameContainer.id, fragment)
        if (addToStack) {
            fragmentTransaction.addToBackStack("")
        }
        fragmentTransaction.commit()
    }

    protected fun hideFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().hide(fragment).commit()
    }

    protected fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().show(fragment).commit()
    }

    protected fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    override fun clearResult() {
        binding.textLog.text = ""
    }

    fun addView(view: View) {
        binding.linearLayoutInScroll.addView(view)
    }

    fun showImage(drawable: Drawable) {
        binding.imageView.setImageDrawable(drawable)
    }

    fun showImage(bitmap: Bitmap) {
        Timber.d("BaseTestActivity.showImage")
        binding.imageView.setImageBitmap(bitmap)
    }

}
