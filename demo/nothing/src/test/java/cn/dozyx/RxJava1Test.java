package cn.dozyx;

import org.junit.Test;

import java.util.concurrent.Callable;

import io.reactivex.functions.BiFunction;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static cn.dozyx.LogUtils.print;

public class RxJava1Test {

    @Test
    public void testReduce() {
        Observable<String> observable;
//        observable = Observable.just("1111"); // 直接输出
        observable = Observable.empty(); // NoSuchElementException 异常
        observable.reduce(new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s + s2;
            }
        }).subscribe(s -> print(s));
    }

    @Test
    public void testFlatmapSubscribeOn() throws InterruptedException {
        Observable.just(1).observeOn(Schedulers.newThread()).flatMap(integer -> {
            print("flatmap");
            return Observable.just(2);
        }).subscribeOn(Schedulers.io())
                .subscribe(integer -> print("onNext"));
        Thread.sleep(100);
    }

    @Test
    public void testNull() {
        Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return null;
            }
        }).subscribe(new Action1<Void>() {
            @Override
            public void call(Void unused) {
                print("onNext");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                print("onError");
            }
        });
    }

    @Test
    public void testException() throws InterruptedException {
        Observable.just(1).map((Func1<Integer, String>) integer -> {
            throw new NullPointerException();
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread())
                .subscribe(s -> print("onNext"), throwable -> {
                    print("onError");
                    print(throwable);
                    throw new IllegalArgumentException();
                });
        Thread.sleep(100);
    }
}
