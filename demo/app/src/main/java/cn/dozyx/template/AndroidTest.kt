package cn.dozyx.template

import android.net.Uri
import android.os.Looper
import android.widget.Toast
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber


/**
 * @author dozyx
 * @date 2019-12-22
 */
class AndroidTest : BaseTestActivity() {
    override fun initActions() {
        testUri()
    }

    private fun testUri() {
        Timber.d("AndroidTest.initActions ${Uri.encode("key")} ${Uri.encode("=key%")}")
    }

    private fun testLooper() {
        Thread(Runnable {
            Timber.d("step 0 ")
            Looper.prepare()
            Toast.makeText(this@AndroidTest, "run on Thread", Toast.LENGTH_SHORT).show()
            Timber.d("step 1 ")
            Looper.loop()
            Timber.d("step 2 ")
        }).start()
    }
}
