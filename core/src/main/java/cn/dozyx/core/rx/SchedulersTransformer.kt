package cn.dozyx.core.rx

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author dozyx
 * @date 2020/2/27
 */
class SchedulersTransformer<T> : ObservableTransformer<T, T>, SingleTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> =
        upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun apply(upstream: Single<T>): SingleSource<T> =
        upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    companion object {
        fun <T> get(): SchedulersTransformer<T> = SchedulersTransformer()
    }
}