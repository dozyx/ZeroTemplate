package cn.dozyx.template.exception

import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber
import java.lang.RuntimeException

/**
 * Create by dozyx on 2019/7/1
 */
class ExceptionTest : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("runtime", Runnable {
            try {
                throw RuntimeException("11111")
            } catch (e: Exception) {
                Timber.d("ExceptionTest.onCreate: $e")
            }
        })

        addButton("thread", Runnable {
            try {
                Thread(Runnable {
                    Thread.sleep(3000)
                    try {
                        throw RuntimeException("22222")
                    } catch (e: Exception) {
                        Timber.d("ExceptionTest.onCreate: $e")
                    }
                }).start()
            } catch (e: Exception) {
                Timber.d("ExceptionTest.onCreate: $e")
            }
        })
    }
}
