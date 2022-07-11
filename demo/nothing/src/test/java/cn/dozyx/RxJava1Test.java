package cn.dozyx;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static cn.dozyx.LogUtils.print;

public class RxJava1Test {

    @Test
    public void testFlapMap() {
        print("当前线程：" + Thread.currentThread());
        Observable.just(1, 2, 3)
                .flatMap((Func1<Integer, Observable<String>>) integer -> Observable.fromCallable(() -> {
                            sleep(1);
                            print("flapmap: " + integer + " & " + Thread.currentThread());
                            return integer + "哈哈";
                        }).subscribeOn(Schedulers.newThread())
                        .map(s -> integer + "map")
                )
//                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(s -> print("结果：" + s + " & " + Thread.currentThread()));
        sleepMillis(10); // 让 Observable 可以开始执行
        print("结束：" + Thread.currentThread());
    }

    @Test
    public void testHandleLast() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject/*.debounce(10L, TimeUnit.MILLISECONDS)*/
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io(), 1)
                .map(number -> {
                    print("map handle: " + Thread.currentThread() + " & " + number);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "this is " + number;
                }).observeOn(Schedulers.computation())
                .subscribe(str -> print(str));
        sleepMillis(10);
        subject.onNext(0);
        print("onNext 0");
        sleepMillis(10);
        subject.onNext(1);
        print("onNext 1");
        sleepMillis(10);
        subject.onNext(2);
        print("onNext 2");
        sleepMillis(10);
        subject.onNext(3);
        print("onNext 3");
        sleepMillis(10);
        subject.onNext(4);
        print("onNext 4");
        sleepMillis(100);
        subject.onNext(5);
        print("onNext 5");
        sleepMillis(10);
        sleep(3);
    }

    @Test
    public void testRefCount() {
//        Observable<Long> origin = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> origin = Observable.fromCallable(() -> {
            print("fromCallable start");
            sleep(2);
//            sleep(4);
            print("fromCallable end");
            return 100L;
        }).subscribeOn(Schedulers.io());
        Observable<Long> publish = origin.doOnUnsubscribe(() -> print("doOnUnsubscribe")).doOnNext(
                aLong -> print("doOnNext " + aLong)).share();
        print("subscribe1");
        Subscription subscribe1 = publish.subscribe(getObserver("1"));
        sleep(3);
        print("subscribe2");
        Subscription subscribe2 = publish.subscribe(getObserver("2"));// 第二个订阅者只能接收到后续的信息。
//        sleep(1);
//        subscribe2.unsubscribe();
        sleep(5);

        subscribe1.unsubscribe();
        subscribe2.unsubscribe(); // 当所有订阅者都取消了订阅时，将取消数据发送
        publish.subscribe(getObserver("3"));// 接收到的是从开始发送的数据
        sleep(3);
    }

    /**
     * source 还没有发送完就取消所有订阅会怎么样？
     * 线程会被中断，fromCallable 里的 sleep 抛出 InterruptedException
     */
    @Test
    public void testRefCountCase2() {
        Observable<Long> origin = Observable.fromCallable(() -> {
            print("fromCallable start");
//            sleep(2);
            Integer total = 0;
            long count = 1000000000L;
            for (int i = 0; i < count; i++) {
                total += i;
            }
//            sleep(4);
            print("fromCallable end");
            return 100L;
        }).subscribeOn(Schedulers.io());
        Observable<Long> publish = origin.doOnUnsubscribe(() -> print("doOnUnsubscribe")).doOnNext(
                aLong -> print("doOnNext " + aLong)).publish().refCount();
        print("subscribe1");
        Subscription subscribe1 = publish.subscribe(getObserver("1"));
        print("subscribe2");
        Subscription subscribe2 = publish.subscribe(getObserver("2"));// 第二个订阅者只能接收到后续的信息。

        subscribe1.unsubscribe();
        subscribe2.unsubscribe(); // 当所有订阅者都取消了订阅时，将取消数据发送
        sleep(1);
        publish.subscribe(getObserver("3"));// 接收到的是从开始发送的数据
        sleep(10);
    }

    /**
     * observer1 complete 之后，observe2 再订阅，observe1 会有反应吗
     */
    @Test
    public void testRefCountCase3() {
        Observable<Long> origin = Observable.fromCallable(() -> {
            print("fromCallable start");
//            sleep(2);
            Integer total = 0;
            long count = 1000000000L;
            for (int i = 0; i < count; i++) {
                total += i;
            }
//            sleep(4);
            print("fromCallable end");
            return 100L;
        }).subscribeOn(Schedulers.io());
        Observable<Long> publish = origin.doOnUnsubscribe(() -> print("doOnUnsubscribe")).doOnNext(
                aLong -> print("doOnNext " + aLong)).publish().refCount();
        print("subscribe1");
        Subscription subscribe1 = publish.subscribe(getObserver("1"));
        sleep(5);
        print("subscribe2");
        Subscription subscribe2 = publish.subscribe(getObserver("2"));// 第二个订阅者只能接收到后续的信息。

        sleep(10);
    }

    /**
     *
     */
    @Test
    public void testRefCountCase4() {
        Observable<Long> origin = Observable.just(1).flatMap((Func1<Integer, Observable<Long>>) integer -> {
            print("flatmap");
            return Observable.fromCallable(() -> {
                print("fromCallable start");
//            sleep(2);
                Integer total = 0;
                long count = 1000000000L;
                for (int i = 0; i < count; i++) {
                    total += i;
                }
//            sleep(4);
                print("fromCallable end");
                return 100L;
            });
        }).subscribeOn(Schedulers.io());
        Observable<Long> publish = origin.doOnUnsubscribe(() -> print("doOnUnsubscribe")).doOnNext(
                aLong -> print("doOnNext " + aLong)).publish().refCount();
        print("subscribe1");
        Subscription subscribe1 = publish.subscribe(getObserver("1"));
        print("subscribe2");
        Subscription subscribe2 = publish.subscribe(getObserver("2"));// 第二个订阅者只能接收到后续的信息。

        sleep(10);
    }

    @Test
    public void testPublishTakeOne() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        publishSubject.subscribe(getObserver("normal"));
        publishSubject.take(1).subscribe(getObserver("take one "));

        for (int i = 0; i < 3; i ++){
            sleep(1);
            publishSubject.onNext(i);
        }
        sleep(5);
    }

    private static <T> Observer<T> getObserver(String prefix) {
        final String formatPrefix = prefix + " - ";
        return new Observer<T>() {
            @Override
            public void onCompleted() {
                print(formatPrefix + "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                print(formatPrefix + "onError: " + e.getMessage());
            }

            @Override
            public void onNext(T t) {
                if (t != null) {
                    print(formatPrefix + "onNext: " + t.toString());
                } else {
                    print(formatPrefix + "onNext: " + null);
                }
            }
        };
    }

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
                print("onNext:" + unused);
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

    private void sleep(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
