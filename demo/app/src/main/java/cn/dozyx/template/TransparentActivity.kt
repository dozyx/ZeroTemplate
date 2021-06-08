package cn.dozyx.template

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils

/**
 * @author dozyx
 * @date 2019-12-25
 */
class TransparentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
    }
}