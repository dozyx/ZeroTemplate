package cn.dozyx.template.justfortest

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.core.context.CustomContextWrapper
import cn.dozyx.template.DelayTextWatcher
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_meaningless.*
import timber.log.Timber


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    var time = 0
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
//        edit.requestFocus()
        text.setOnClickListener {
            ToastUtils.showShort("点击")
        }

        val task = Runnable {
            Timber.d("MeaninglessActivity.onCreate")
            if (time == 0) {
                Thread.sleep(5 * 1000);
            }
            time++
        }
        for (i in 0..5) {
            Handler().postDelayed(task, 1000)
        }

        edit.addTextChangedListener(DelayTextWatcher(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timber.d("MeaninglessActivity.afterTextChanged $s")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }))

    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CustomContextWrapper.wrap(newBase, 1.0f))
    }

    /*override fun getResources(): Resources {
        val resources = super.getResources()
        val oldConfig = resources.configuration
        Timber.d("MeaninglessActivity.getResources ${oldConfig.fontScale}")
        oldConfig.fontScale = 2f
//        resources.displayMetrics.scaledDensity = resources.displayMetrics.density
        val newConfig = Configuration(oldConfig)
        if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            createConfigurationContext(newConfig)
            // 这个没有效果
        }
        Timber.d("MeaninglessActivity.getResources ${resources.displayMetrics.scaledDensity } ${resources.displayMetrics.density}")
        resources.updateConfiguration(newConfig,resources.displayMetrics)
        return resources
    }*/

}
