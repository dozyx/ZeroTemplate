package cn.dozyx.template.justfortest

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_meaningless.*
import timber.log.Timber


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
//        edit.requestFocus()
        text.setOnClickListener {
            ToastUtils.showShort("点击")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        val oldConfig = resources.configuration
        Timber.d("MeaninglessActivity.getResources ${oldConfig.fontScale}")
        oldConfig.fontScale = 1f
//        resources.displayMetrics.scaledDensity = resources.displayMetrics.density
        val newConfig = Configuration(oldConfig)
        if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            createConfigurationContext(newConfig)
            // 这个没有效果
        }
        Timber.d("MeaninglessActivity.getResources ${resources.displayMetrics.scaledDensity } ${resources.displayMetrics.density}")
        resources.updateConfiguration(newConfig,resources.displayMetrics)
        return resources
    }

}
