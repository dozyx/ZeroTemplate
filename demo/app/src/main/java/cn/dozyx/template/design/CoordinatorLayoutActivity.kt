package cn.dozyx.template.design

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.core.debug.MockDataUtils
import cn.dozyx.core.utli.SampleUtil
import cn.dozyx.template.R
import cn.dozyx.template.util.MockUtils
import kotlinx.android.synthetic.main.activity_coordinator_layout.*

class CoordinatorLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)
        MockUtils.setupList(rv, SampleUtil.getStrings(100))
    }
}