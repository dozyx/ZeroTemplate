package cn.dozyx.template.service

import android.app.IntentService
import android.content.Intent
import com.blankj.utilcode.util.ThreadUtils
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Executors

class MyIntentService : IntentService("my") {

    override fun onHandleIntent(intent: Intent?) {
        Timber.d("MyIntentService.onHandleIntent")
        Thread {
            Timber.d("MyIntentService.onHandleIntent thread started")
            Thread.sleep(2000)
            Timber.d("MyIntentService.onHandleIntent thread finish")
        }.start()
        Executors.newCachedThreadPool().execute{
            Timber.d("MyIntentService.onHandleIntent newCachedThreadPool started")
            Thread.sleep(2000)
            Timber.d("MyIntentService.onHandleIntent newCachedThreadPool finish")
        }
        Single.create(SingleOnSubscribe<Int> {
            Thread.sleep(1000)
            Timber.d("MyIntentService.SingleOnSubscribe ${Thread.currentThread()} ${ThreadUtils.isMainThread()}")
            it.onSuccess(2)
        }).subscribeOn(Schedulers.io()).subscribe(object : SingleObserver<Int> {
            override fun onSuccess(t: Int) {
                Timber.d("MyIntentService.onSuccess $t")
            }

            override fun onSubscribe(d: Disposable) {
                Timber.d("MyIntentService.onSubscribe $d")
            }

            override fun onError(e: Throwable) {
                Timber.d("MyIntentService.onError")
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        Timber.d("MyIntentService.onDestroy")
    }
}
