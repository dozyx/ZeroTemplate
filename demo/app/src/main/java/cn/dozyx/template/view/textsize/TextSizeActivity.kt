package cn.dozyx.template.view.textsize

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.util.TypedValue
import android.widget.SeekBar
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.activity_text_size.*
import timber.log.Timber


class TextSizeActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_text_size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("TextSizeActivity.onCreate")
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                text1.textSize = progress.toFloat()
                text2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, progress.toFloat())
                text3.textSize = progress.toFloat()
                text4.textSize = progress.toFloat()
                text_current.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        button.setOnClickListener {
            Timber.d("TextSizeActivity.onCreate fontScale: ${resources.configuration.fontScale}")
            Timber.d("TextSizeActivity.onCreate scaledDensity: ${resources.displayMetrics.scaledDensity}")
            Timber.d("TextSizeActivity.onCreate density: ${resources.displayMetrics.density}")
            SizeUtils.sp2px(14f)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        Timber.d("TextSizeActivity.attachBaseContext ${newBase!!::class.java.name}")
        var newContext = newBase
        if (newBase != null) {
            val config = newBase.resources.configuration
            config.fontScale = 1.0f
            newContext = newBase.createConfigurationContext(config)// API17 加入
        }
        super.attachBaseContext(newContext)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Timber.d("TextSizeActivity.onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }

    /*override fun getResources(): Resources? {
        val resources = super.getResources()
        Timber.d("TextSizeActivity.getResources old fontScale: ${resources.configuration.fontScale}")
        Timber.d("TextSizeActivity.getResources old scaledDensity: ${resources.displayMetrics.scaledDensity}")
        Timber.d("TextSizeActivity.getResources old density: ${resources.displayMetrics.density}")
        // 小米8 Android10 fontScale值：最小0.86, 标准1.0，最大1.4
        // 小米8 Android10 scaledDensity：最小2.365, 标准2.75，最大3.85
        // 从上面的数值可以发现 fontScale * 2.75 刚好等于 scaledDensity，可以知道 fontScale = scaledDensity / density
        // 在 ResourcesImpl#updateConfiguration() 中，可以看到
        // mMetrics.scaledDensity = mMetrics.density * (mConfiguration.fontScale != 0 ? mConfiguration.fontScale : 1.0f)
        // 从中可以看出，fontScale 是随系统字体大小改变的，而 scaledDensity 随 fontScale 改变，之所有用 scaledDensity 而不是直接用 fontScale
        // 的原因与使用 dp 相同，即使不同 density 的屏幕显示效果一致。

        if (resources != null && resources.configuration.fontScale != 1.0f) {
            val configuration = resources.configuration
            configuration.fontScale = 1.0f
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return resources
    }*/
}

private class FontContextWrapper(val context: Context) : ContextWrapper(context) {

}