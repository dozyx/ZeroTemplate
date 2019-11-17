package cn.dozyx.template.bug

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_called_from_wrong_thread.*

/**
 * @author dozyx
 * @date 2019-11-13
 */
class CalledFromWrongThreadTest : BaseActivity() {
    override fun getLayoutId() = R.layout.test_called_from_wrong_thread
    private val handlerThread = HandlerThread("test")
    private val handler: Handler

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_change_text.setOnClickListener {
            //            Handler().postDelayed({ text.text = "哈哈哈" }, 3000)
//            handler.post { text.text = "哈哈哈" }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Handler().postDelayed({ text.text = "哈哈哈" }, 1000)
    }
}