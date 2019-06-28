package cn.dozyx.zerofate.java;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RxJavaTest {

    public static void main(String[] args) {
        testBlockingFirst();
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
        try{
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
        } catch (Exception e){
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
