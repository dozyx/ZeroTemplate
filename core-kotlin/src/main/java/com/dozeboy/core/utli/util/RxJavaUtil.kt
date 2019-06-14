package com.dozeboy.core.utli.util


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

    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return schedulersTransformer as SingleTransformer<T, T>
    }
}
