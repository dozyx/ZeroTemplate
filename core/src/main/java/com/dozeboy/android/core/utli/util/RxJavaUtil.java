package com.dozeboy.android.core.utli.util;


import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dozeboy
 * @date 2018/11/6
 */
public class RxJavaUtil {
    private static final SingleTransformer schedulersTransformer = upstream -> upstream.subscribeOn(
            Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> SingleTransformer<T, T> applySchedulers() {
        return (SingleTransformer<T, T>) schedulersTransformer;
    }
}
