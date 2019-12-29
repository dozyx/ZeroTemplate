package cn.dozyx.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * @author dozeboy
 * @date 2018/11/21
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = getLayoutView()
        if (contentView == null) {
            if (getLayoutId() != 0) {
                setContentView(getLayoutId())
            }
        } else {
            setContentView(contentView)
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun getLayoutView(): View? = null
}