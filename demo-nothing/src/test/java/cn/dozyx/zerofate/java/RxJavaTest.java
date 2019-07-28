package cn.dozyx.zerofate.java;

import android.annotation.SuppressLint;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
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
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RxJavaTest {

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

    public void print(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        System.out.println(
                format.format(Calendar.getInstance().getTime())
                        + " -> " /*+ stackTrace[3].getMethodName()*/ + " "
                        + msg);
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
    public void testConnectableObservable() {
        ConnectableObservable<String> publish = getStringObservable().publish();
        publish.subscribe(s -> System.out.println("Observer 1: " + s));
        publish.map(String::length).subscribe(i -> System.out.println("Observer 2: " + i));
        publish.connect();
    }

    public Observable<String> getStringObservable() {
        return Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
    }

    public Observable<String> getCreateObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String item = "Alpha" + new Random().nextInt();
                System.out.println("emitter = [" + emitter + "] " + item);
                emitter.onNext(item);
            }
        });
    }


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

    public void testPublishSubject() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("doOnError = [" + throwable + "]" + throwable.getMessage());
            }
        }).onErrorReturnItem(999).subscribe(new Observer<Integer>() {
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
                System.out.println("e = [" + e + "]" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
        subject.onNext(2);
        subject.onError(new Throwable("1111"));
        subject.onComplete();
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

    Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("onSubscribe d = [" + d + "]");
        }

        @Override
        public void onNext(Integer integer) {
            System.out.println("onNext integer = [" + integer + "]");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError e = [" + e + "]");
        }

        @Override
        public void onComplete() {
            System.out.println(System.currentTimeMillis());
            System.out.println("onComplete");
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
