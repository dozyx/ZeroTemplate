package cn.dozyx.core.utli.util


import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author dozeboy
 * @date 2018/11/6
 */
object RxJavaUtil {
    private val schedulersTransformer = SingleTransformer<Any, Any> { upstream ->
        upstream.subscribeOn(
                Schedulers.io()
        ).observeOn(
                AndroidSchedulers.mainThread()
        )
    }

    private val schedulersTransformer2 = ObservableTransformer<Any, Any> { upstream ->
        upstream.subscribeOn(
                Schedulers.io()
        ).observeOn(
                AndroidSchedulers.mainThread()
        )
    }

    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return schedulersTransformer as SingleTransformer<T, T>
    }

    fun <T> applySchedulers2(): ObservableTransformer<T, T> {
        return schedulersTransformer2 as ObservableTransformer<T, T>
    }
}
