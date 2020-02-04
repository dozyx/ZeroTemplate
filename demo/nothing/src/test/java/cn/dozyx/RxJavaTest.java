package cn.dozyx;

import static cn.dozyx.LogUtils.print;

import android.annotation.SuppressLint;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import io.reactivex.subjects.UnicastSubject;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RxJavaTest {

    @Test
    public void testAmb() {
        Observable.ambArray(Observable.just(1, 2, 3)/*.delay(100,TimeUnit.MILLISECONDS)*/,
                Observable.empty()).subscribe(sObserver);
        sleep(2);
    }

    @Test
    public void testScheduler() {
        RxJavaPlugins.setOnObservableSubscribe(new BiFunction<Observable, Observer, Observer>() {
            @Override
            public Observer apply(Observable observable, Observer observer) throws Exception {
                print("hook setOnObservableSubscribe -> " + Thread.currentThread() + " "
                        + observable);
                return observer;
            }
        });
        getCreateObservable().flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return getCreateObservable();
            }
        })/*.observeOn(Schedulers.io())*/.subscribeOn(Schedulers.io()).subscribeOn(
                Schedulers.computation()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                print("onSubscribe -> " + Thread.currentThread());
            }

            @Override
            public void onNext(String s) {
                print("onNext -> " + Thread.currentThread());
            }

            @Override
            public void onError(Throwable e) {
                print("onError -> " + Thread.currentThread());
            }

            @Override
            public void onComplete() {
                print("onComplete -> " + Thread.currentThread());
            }
        });
        sleep(1);
    }

    @Test
    public void testRxJavaPlugin() {
        // hook 函数
        RxJavaPlugins.setOnObservableAssembly(observable -> {
            print("onAssembly " + observable.getClass());
            return observable;
        });
        RxJavaPlugins.onAssembly(new Observable<Integer>() {
            @Override
            protected void subscribeActual(io.reactivex.Observer<? super Integer> observer) {
                print("subscribeActual: ");
            }
        });
        testRetry();
    }

    @Test
    public void testRetry() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    if (i == 3) {
                        emitter.onError(null);
                    } else {
                        emitter.onNext(randomInt());
                    }
                }
                emitter.onComplete();
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                print("doOnSubscribe");
            }
        }).retry(3).subscribe(sObserver);
    }

    @Test
    public void testCache() {
        Observable<Integer> cache = Observable.just(6, 2, 5, 6, 1, 4, 9, 8, 3).scan(0,
                (total, next) -> total + next).cache();
        cache.subscribe(System.out::println);
    }

    @Test
    public void testReplay() {
        Observable<Long> seconds = Observable.interval(1,
                TimeUnit.SECONDS).replay().autoConnect();
        seconds.subscribe(l -> System.out.println("Observer 1: " + l));
        sleep(3);
        seconds.subscribe(l -> System.out.println("Observer 2: " + l));
        sleep(3);
    }

    @Test
    public void testGroupBy() {
        getStringObservable().groupBy(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                // 为每个 item 提供分组的 key
                // 相同 key 的 item 会放到同一个 observable 中
                return s.length();
            }
        }).flatMapSingle(Observable::toList).subscribe(sObserver);
    }

    @Test
    public void testReduce() {
        getStringObservable().reduce(new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s + s2;
            }
        }).subscribe(sMaybeObserver);
    }

    private Observer sObserver = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {
            print("onSubscribe: " + d);
        }

        @Override
        public void onNext(Object o) {
            print("onNext： " + o);
        }

        @Override
        public void onError(Throwable e) {
            print("onError： " + e);
        }

        @Override
        public void onComplete() {
            print("onComplete");
        }
    };
    private MaybeObserver sMaybeObserver = new MaybeObserver<Object>() {
        @Override
        public void onSubscribe(Disposable d) {
            print("onSubscribe: " + d);
        }

        @Override
        public void onSuccess(Object o) {
            print("onSuccess： " + o);
        }

        @Override
        public void onError(Throwable e) {
            print("onError： " + e);
        }

        @Override
        public void onComplete() {
            print("onComplete");
        }
    };

    @Test
    public void testFlatMap() {
        Observable<Integer> integerObservable = Observable.just(2, 3, 10, 7);
        integerObservable.flatMap(i -> Observable.interval(i, TimeUnit.SECONDS).map(
                i2 -> i + "s interval: " + ((i2 + 1) * i) + " seconds elapsed")).subscribe(
                sObserver);
        sleep(12);
    }

    @Test
    public void testSwitchMap() {
        Observable<Integer> integerObservable = Observable.just(2, 3, 10, 7);
        // 只会订阅最后一个 Observable，会取消旧的 Observable 的订阅
        integerObservable.switchMap(i -> Observable.interval(i, TimeUnit.SECONDS).map(
                i2 -> i + "s interval: " + ((i2 + 1) * i) + " seconds elapsed")).subscribe(
                sObserver);
        sleep(12);
    }

    @Test
    public void testConcatMap() {
        Observable<Integer> integerObservable = Observable.just(2, 3, 10, 7);
        // 当前面的 Observable 完成时，才会开始下一个 Observable，因为这里一直 repeat，所以只有第一个 Observable 在发送数据
        integerObservable.concatMap(i -> Observable.interval(i, TimeUnit.SECONDS).map(
                i2 -> i + "s interval: " + ((i2 + 1) * i) + " seconds elapsed")).subscribe(
                sObserver);
        sleep(12);
    }

    @Test
    public void testConcat() {
        // concat：拼接
        // 按顺序发射多个 observable 的 item
        Observable.concat(Observable.just("a", "b", "c").subscribeOn(Schedulers.io()),
                Observable.just("d", "f")).subscribeOn(Schedulers.io()).subscribe(sObserver);
        sleep(5);
    }


    public void testMerge() {
        // 将多个 observable 的 item 合并之后发送。
        // merge 操作符会同时订阅到指定的 observable，但是，如果这些 observable 是 cold 的并且在同一线程上，那么发射顺序可能会相同
        Observable.merge(Observable.just("a", "b", "c").subscribeOn(Schedulers.io()),
                Observable.just("d", "f")).subscribeOn(Schedulers.io()).subscribe(sObserver);
        sleep(5);
    }

    /**
     * 实现一个轮询功能，第一次调用直接无延迟，后续每 2s 查询一次
     * todo: 还没有实现
     */
    public void testPolling() {
        print("查询开始");
        Observable<Integer> payObservable = Observable.just(100);
        Observable<Long> queryObservable = Observable.just(101L);
        int flag = 0;
        payObservable.doOnNext(integer -> {
            print("发起支付成功");
        }).flatMap((Function<Integer, ObservableSource<Boolean>>) integer -> {
            print("支付结果" + integer);
            return Observable.just(false).flatMap(
                    new Function<Boolean, ObservableSource<Boolean>>() {
                        @Override
                        public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                return Observable.empty();
                            }
                            return Observable.just(true).delay(2, TimeUnit.SECONDS).repeat(30);
                        }
                    });
        }).doOnNext(b -> print("查询支付结果成功：" + b)).repeat(2).subscribe(sObserver);
        /*// 使用 interval 可能出现因为查询为异步，导致多次查询同时进行的问题
        payObservable.flatMap((Function<Integer, ObservableSource<Long>>) integer -> {
            print("支付结果" + integer);
            return Observable.intervalRange(1, 5, 0, 2, TimeUnit.SECONDS);
        }).flatMap((Function<Long, ObservableSource<Long>>) aLong -> {
            print("触发查询： " + aLong);
            return Observable.just(
                    101 + aLong).delay(3, TimeUnit.SECONDS);
        }).subscribe(sObserver);*/
        sleep(12);
        print("查询结束");
    }

    public void testRepeat() {
        // repeat 的实现原理为 upstream 触发 onComplete 时，重新 subscribe，这样的话，chain 中 repeat 之前所有的
        // observable 都会重新触发
        // repeatWhen 第二个参数的 observable 每调用一次 onNext 都会触发一次 repeat
        getCreateObservable().repeatWhen(
                new Function<Observable<Object>, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Observable<Object> objectObservable)
                            throws Exception {
                        System.out.println("objectObservable = [" + objectObservable + "]");
                        return Observable.empty();
                    }
                }).subscribe(sObserver);

        /*getStringObservable().retryWhen(new Function<Observable<Throwable>,
        ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws
            Exception {
                return Observable.just("1");
            }
        }).subscribe(sObserver);*/
    }

    @SuppressLint("CheckResult")
    public void testCallable() {
        testDelay();
    }

    public void testDelay() {
        // delay：经过一段延迟后再发射，但 error 不会延迟，有重载方法配置延迟error
        Observable.range(1, 5).delay(3, TimeUnit.SECONDS).subscribe(sObserver);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testTake() {
        // takeWhile：发射满足条件的 item，一旦出现不满足条件的 item 就停止。并不会检查不满足条件item之后的item，这点与 filter 不同
        Observable.range(1, 100).takeWhile(i -> i == 5).subscribe(sObserver);
        // takeUtil：满足某个条件时停止
//        Observable.range(1, 100).takeUntil(i -> i <= 5).subscribe(sObserver);
    }

    @Test
    public void testError() {
        // 不会回调 onError 的例子
//        Observable.just(1 / 0).subscribe(integer -> System.out.println("Received: " + integer),
//                throwable -> System.out.println("Error: " + throwable));

//        Observable.fromCallable(() -> 1 / 0).subscribe(integer -> System.out.println("Received:
//        " + integer),
//                throwable -> System.out.println("Error: " + throwable));

        // 使用 onErrorReturnItem 在发生错误时产生默认值，但错误仍会导致序列停止
        // 如果将 onErrorReturnItem 放在 map 前面，错误将不会被捕获
        Observable.just(5, 2, 4, 0, 3, 2, 8).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return 10 / integer;
            }
        }).onErrorReturnItem(-1).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer - 1;
            }
        }).subscribe(sObserver);


        Observable.just(1).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                String str = null;
                str.length();// 异常被 catch
                return 2;
            }
        }).subscribe(observer);
    }

    public int start = 1;
    public int count = 5;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public void testDefer() {
        Observable<Integer> source = Observable.defer(() -> Observable.range(start, count));
        source.subscribe(integer -> System.out.println("observer 1: " + integer));
        count = 10;
        source.subscribe(integer -> System.out.println("observer 2: " + integer));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Test
    public void testConnectableObservable() {
        // ConnectableObservable 将 source 的 emission 强制变为 hot
        // 这种为多个 observer 提供单个 stream 的做法称为 multicasting
        ConnectableObservable<String> publish = getStringObservable().publish();
        publish.subscribe(s -> System.out.println("Observer 1: " + s));
        publish.map(String::length).subscribe(i -> System.out.println("Observer 2: " + i));
        publish.connect();
    }

    @Test
    public void testMulticasting() {
        Observable<Integer> threeRandoms = Observable.range(1, 3).map(i -> randomInt());
        // threeRandoms 会产生两个独立的 emission 生成器
        threeRandoms.subscribe(i -> print("threeRandoms Observer 1: " + i));
        threeRandoms.subscribe(i -> print("threeRandoms Observer 2: " + i));


        ConnectableObservable<Integer> threeInts = Observable.range(1, 3).publish();
        threeRandoms = threeInts.map(i -> randomInt());
        // multicast 在 map 之前，所以每个 observer 在 map 时还是得到了单独的 stream。如果需要防止产生两个 stream，需要将 publish
        // 放在 map 之后
        threeRandoms.subscribe(i -> print("threeInts Observer 1: " + i));
        threeRandoms.subscribe(i -> print("threeInts Observer 2: " + i));
        threeInts.connect();


    }

    private int randomInt() {
        return ThreadLocalRandom.current().nextInt(100000);
    }

    public Observable<String> getStringObservable() {
        return Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
    }

    public Observable<String> getCreateObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String item = "Alpha" + new Random().nextInt();
//                System.out.println("emitter = [" + emitter + "] " + item);
                print("create -> " + Thread.currentThread());
                emitter.onNext(item);
            }
        });
    }


    @Test
    public void testCreate() {
        Observable<String> source = Observable.create(emitter -> {
            try {
                emitter.onNext("Alpha");
                emitter.onNext("Beta");
                emitter.onNext("Gamma");
                emitter.onNext("Delta");
//                emitter.onNext(null);
//                emitter.onError(new NullPointerException());
                // onError 之后会 dispose，所有后续的 onNext 和 onComplete 会无效
                emitter.onNext("Epsilon");
                emitter.onComplete();
            } catch (Throwable e) {
                System.out.println("into catch");
                emitter.onError(e);
            }
        });
//        source.map(s -> s.length());
        source.map(s -> s.length()).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("doOnNext integer = [" + integer + "]");
            }
        }).subscribe(s -> System.out.println("RECEIVED: " + s), Throwable::printStackTrace);
    }

    public void testInterval() {
        Observable<Long> secondIntervals = Observable.interval(1, TimeUnit.SECONDS);
        secondIntervals.subscribe(s -> {
            print(s.toString() + Thread.currentThread());
        });
        /* Hold main thread for 5 seconds so Observable above has chance to fire */
        print("sleep start" + Thread.currentThread());
        sleep(5);
        print("sleep end" + Thread.currentThread());
    }

    public void sleep(int timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testBlockingFirst() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("start subscribe: " + Thread.currentThread());
//                emitter.onNext("0");
                Thread.sleep(3000);
                emitter.onNext("1");
                Thread.sleep(3000);
                emitter.onNext("2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
        final Disposable[] disposable = new Disposable[1];
        Observable<String> interval = Observable.interval(3, TimeUnit.SECONDS).map(
                new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return String.valueOf(aLong);
                    }
                });
        try {
            Observable.just(observable.blockingFirst()).subscribe(
                    new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            System.out.println("d = [" + d + "]");
                            disposable[0] = d;
                        }

                        @Override
                        public void onNext(String s) {
                            System.out.println("onNext s = [" + s + "]");
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("onError e = [" + e + "]");
                        }

                        @Override
                        public void onComplete() {
                            System.out.println("onComplete");
                        }
                    });
        } catch (Exception e) {
            System.out.println();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (disposable[0] != null) {
            System.out.println("dispose..." + Thread.currentThread());
            disposable[0].dispose();
        }
    }

    public void testConditionChain() {
        Observable<String> stringObservable = Observable.just("1");
        Observable<Boolean> booleanObservable = Observable.just(true);
        stringObservable.flatMap(new Function<String, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(String s) throws Exception {
                if ("2".equals(s)) {
                    return Observable.empty();
                }
                return booleanObservable;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("d = [" + d + "]");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                System.out.println("aBoolean = [" + aBoolean + "]");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    @Test
    public void testPublishSubject() {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS).map(
                l -> (l + 1) + "seconds");
        Observable<String> source2 = Observable.interval(300, TimeUnit.MILLISECONDS).map(
                l -> ((l + 1) * 300 + " milliseconds"));
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(System.out::println);

        source1.subscribe(subject);
        source2.subscribe(subject);
        sleep(3);

    }

    @Test
    public void testBehaviorSubject() {
        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.subscribe(s -> System.out.println("Observer 1: " + s));
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");

        subject.subscribe(s -> System.out.println("Observer 2: " + s));
    }

    @Test
    public void testReplaySubject() {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(s -> System.out.println("Observer 1: " + s));
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");

        subject.subscribe(s -> System.out.println("Observer 2: " + s));
    }

    @Test
    public void testAsyncSubject() {
        // 只发送最后一个值
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.subscribe(s -> System.out.println("Observer 1: " + s), Throwable::printStackTrace,
                () -> System.out.println("Observer 1 done"));
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onComplete();

        subject.subscribe(s -> System.out.println("Observer 2: " + s), Throwable::printStackTrace,
                () -> System.out.println("Observer 2 done"));
    }

    @Test
    public void testUnicastSubject() {
        // 先缓冲所有的 emission知道有 observer 订阅，接着释放所有的 emission 并清除缓存
        // 只能有一个 Observer
        Subject<String> subject = UnicastSubject.create();

        Observable.interval(300, TimeUnit.MILLISECONDS).map(
                l -> (l + 1) * 300 + " milliseconds").subscribe(subject);
        sleep(2);
        subject.subscribe(s -> System.out.println("Observer 1:" + s));
        sleep(2);
    }

    public void testDelayZip() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Thread.sleep(4000);
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Thread.sleep(5000);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        System.out.println(System.currentTimeMillis());
        Observable.zip(observable, observable2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(observer);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZip() {
        Observable<Integer> observable1 = getObservable(1, 2, 3);
        Observable<Integer> observable2 = getObservable(4, 5, 6, 7).delay(2, TimeUnit.SECONDS);
        Observable.zip(observable1, observable2,
                (integer, integer2) -> integer + integer2).subscribe(observer);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testZipArray() {
        Observable<Integer> observable1 = getObservable(1, 2, 3);
        Observable<Integer> observable2 = getObservable(4, 5, 6, 7).delay(2, TimeUnit.SECONDS);
        Observable.zipArray(objects -> (Integer) objects[0] + (Integer) objects[1], false, 2,
                observable1, observable2).subscribe(observer);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            print("onSubscribe d = [" + d + "]");
        }

        @Override
        public void onNext(Integer integer) {
            print("onNext integer = [" + integer + "]");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError e = [" + e + "]");
        }

        @Override
        public void onComplete() {
            print("onComplete");
        }
    };

    public Observable<Integer> getObservable(Integer... datas) {
        return Observable.fromArray(datas);
    }

    public void testSubject() {
        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        ((BehaviorSubject) subject.hide()).getValue();
        subject.onNext(11);
        subject.onNext(31);
        Disposable disposable = subject.hide().subscribe(
                integer -> System.out.println("integer = [" + integer + "]"));
        subject.onNext(1);
        subject.onNext(3);
        subject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("d = [" + d + "]");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("integer = [" + integer + "]");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
        subject.onNext(2);
        disposable.dispose();
        subject.onNext(2);
        subject.onComplete();
    }

    public void testFlowable() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter)
                    throws Exception {
                int count = 0;
                while (true) {
                    count++;
                    emitter.onNext(count + "\n");
                }
            }
        }, BackpressureStrategy.DROP)
                .observeOn(Schedulers.newThread(), false, 3)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String value) {
                        try {
                            Thread.sleep(1000);
                            System.out.println(value);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public class RxJava {
        public void doSomeWork() {
        }

        public Observable<String> getObservable() {
            return Observable.just("Cricket", "Football");
        }

        public Observer<String> getObserver() {
            return new Observer<String>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String value) {
                    System.out.println(value);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    System.out.println("onComplete");
                }
            };
        }
    }

}
