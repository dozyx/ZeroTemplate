package cn.dozyx

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import org.junit.Test

internal class RxJava3Test {
    @Test
    fun learnHenCoder() {
        val single = Single.just(1)

        single.subscribe(object : SingleObserver<Int?> {
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onSuccess(t: Int?) {
                print(t)
            }

            override fun onError(e: Throwable?) {
            }
        })
    }
}