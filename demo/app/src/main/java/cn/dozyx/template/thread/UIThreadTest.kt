package cn.dozyx.template.thread

import android.os.Looper
import android.widget.Toast
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ToastUtils
import timber.log.Timber

class UIThreadTest : BaseTestActivity() {
    override fun initActions() {
        addAction("toast") {
            val toast = Toast.makeText(this, "hahaha111", Toast.LENGTH_SHORT);
            Thread {
//                toast.show()
//                Toast.makeText(this, "hahaha111", Toast.LENGTH_SHORT).show() // looper 为 null 导致 crash
//                ToastUtils.showShort("hahaha")// 不要用 ToastUtils，因为工具类里自动切到了主线程
            }.start()

            Thread {
                Looper.prepare()
                Toast.makeText(this, "hahaha000", Toast.LENGTH_SHORT).show()// 正常显示
                Looper.loop()
                // loop() 之后进入死循环，下面的语句不会被执行
                Timber.d("UIThreadTest.initActions after loop")
                Toast.makeText(this, "hahaha111", Toast.LENGTH_SHORT).show()
            }.start()

        }
    }

}