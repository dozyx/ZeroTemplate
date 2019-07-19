package cn.dozyx.zerofate.java;

import android.annotation.SuppressLint;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RxJavaTest {
    private static Observer sObserver = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {
            print("d = [" + d + "]");
        }

        @Override
        public void onNext(Object o) {
            print("o = [" + o + "]");
        }

        @Override
        public void onError(Throwable e) {
            print("e = [" + e + "]");
        }

        @Override
        public void onComplete() {
            print("");
        }
    };

    public static void main(String[] args) {
        testDelay();
    }

    private static void print(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        System.out.println(format.format(Calendar.getInstance().getTime()) + " -> " + stackTrace[3].getMethodName() + " " + msg);
    }

    @SuppressLint("CheckResult")
    private static void testCallable() {
        testDelay();
    }

    private static void testDelay() {
        // delay：经过一段延迟后再发射，但 error 不会延迟，有重载方法配置延迟error
        Observable.range(1, 5).delay(3, TimeUnit.SECONDS).subscribe(sObserver);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testTake() {
        // takeWhile：发射满足条件的 item，一旦出现不满足条件的 item 就停止。并不会检查不满足条件item之后的item，这点与 filter 不同
        Observable.range(1, 100).takeWhile(i -> i == 5).subscribe(sObserver);
        // takeUtil：满足某个条件时停止
//        Observable.range(1, 100).takeUntil(i -> i <= 5).subscribe(sObserver);
    }

    private static void testError() {
        // 不会回调 onError 的例子
//        Observable.just(1 / 0).subscribe(integer -> System.out.println("Received: " + integer),
//                throwable -> System.out.println("Error: " + throwable));
        Observable.fromCallable(() -> 1 / 0).subscribe(integer -> System.out.println("Received: " + integer),
                throwable -> System.out.println("Error: " + throwable));
    }

    private static int start = 1;
    private static int count = 5;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private static void testDefer() {
        Observable<Integer> source = Observable.defer(() -> Observable.range(start, count));
        source.subscribe(integer -> System.out.println("observer 1: " + integer));
        count = 10;
        source.subscribe(integer -> System.out.println("observer 2: " + integer));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private static void testConnectableObservable() {
        ConnectableObservable<String> publish = getWordObservable().publish();
        publish.subscribe(s -> System.out.println("Observer 1: " + s));
        publish.map(String::length).subscribe(i -> System.out.println("Observer 2: " + i));
        publish.connect();
    }

    private static Observable<String> getWordObservable() {
        return Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
    }


    private static void testCreate() {
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

    private static void testInterval() {
        Observable<Long> secondIntervals = Observable.interval(1, TimeUnit.SECONDS);
        secondIntervals.subscribe(s -> {
            System.out.println(s.toString() + Thread.currentThread());
        });
        /* Hold main thread for 5 seconds so Observable above has chance to fire */
        System.out.println("sleep start" + Thread.currentThread());
        sleep(5000);
        System.out.println("sleep end" + Thread.currentThread());
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testBlockingFirst() {
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
        Observable<String> interval = Observable.interval(3, TimeUnit.SECONDS).map(new Function<Long, String>() {
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

    private static void testConditionChain() {
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

    private static void testPublishSubject() {
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

    private static void testDelayZip() {
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

    private static void testZip() {
        Observable<Integer> observable1 = getObservable(1, 2, 3);
        Observable<Integer> observable2 = getObservable(4, 5, 6, 7).delay(2, TimeUnit.SECONDS);
        Observable.zip(observable1, observable2, (integer, integer2) -> integer + integer2).subscribe(observer);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static Observer<Integer> observer = new Observer<Integer>() {
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

    private static Observable<Integer> getObservable(Integer... datas) {
        return Observable.fromArray(datas);
    }

    private static void testSubject() {
        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        ((BehaviorSubject) subject.hide()).getValue();
        subject.onNext(11);
        subject.onNext(31);
        Disposable disposable = subject.hide().subscribe(integer -> System.out.println("integer = [" + integer + "]"));
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

    private static void testFlowable() {
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

    public static class RxJava {
        private void doSomeWork() {
        }

        private Observable<String> getObservable() {
            return Observable.just("Cricket", "Football");
        }

        private Observer<String> getObserver() {
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
