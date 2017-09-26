package com.zerofate.template.rxjava;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.zerofate.template.R;
import com.zerofate.template.base.BaseShowResultActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxJavaSimpleExam extends BaseShowResultActivity {
    private static final String TAG = "RxJavaSimpleExam";

    private static final Observable<Long> sIntervalObservableSecond_1 = Observable.interval(1,
            TimeUnit.SECONDS);
    private static final Observable<Long> sIntervalObservableSecond_2 = Observable.interval(2,
            TimeUnit.SECONDS);
    private static final Observable<Long> sIntervalRangeObservableSecond_1 =
            Observable.intervalRange(100, 20, 0, 1, TimeUnit.SECONDS);
    private static final Observable<Long> sIntervalRangeObservableSecond_2 =
            Observable.intervalRange(200, 20, 0, 2, TimeUnit.SECONDS);
    private static final Observable<Long> sIntervalRangeObservableWithDelaySecond_2_ =
            Observable.intervalRange(200, 30, 3, 2, TimeUnit.SECONDS);
    private static final Observable<Integer> sRangeObservable = Observable.range(10, 10);

    @Override
    protected String[] getButtonText() {
        return new String[]{"打印字符串数组", "显示图片", "操作符测试"};
    }

    @Override
    public void onButton1() {
        printStrings(new String[]{"你好", "大傻逼", "我不好", "再见"});
¬¬}

    @Override
    public void onButton2() {
        displayImage();
    }

    @Override
    public void onButton3() {
//        testOperatorCreateInterval(); // interval(long period, TimeUnit unit)
//        testOperatorCreateIntervalRange();
//        testOperatorTransformBuffer1();
//        testOperatorTransformBuffer2();
//        testOperatorTransformBuffer3(); // buffer(Callable<? extends ObservableSource<B>>)
//        testOperatorTransformBuffer4();
        testOperatorTransformFlatMap();
    }

    private void printStrings(@NonNull String[] strings) {
        Observable.fromArray(strings).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                appendText(s);
            }
        });

    }

    private void displayImage() {
        final int drawableRes = R.drawable.bg_0;
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Drawable> e)
                    throws Exception {
                Drawable drawable = getResources().getDrawable(drawableRes);
                e.onNext(drawable);
                e.onComplete();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                appendText("onSubscribe ->");
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull Drawable drawable) {
                appendText("onNext ->");
                setImage(drawable);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                appendText("onError ->");
                Toast.makeText(RxJavaSimpleExam.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                appendText("onComplete ->");
            }
        });
    }


    // interval(long period, TimeUnit unit)
    private void testOperatorCreateInterval() {
        sIntervalObservableSecond_1.subscribe(new Consumer<Long>() {
            @Override
            public void accept(final Long aLong) throws Exception {
                Log.d(TAG, "accept: interval operator thread == " + Thread.currentThread());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        appendText(aLong.toString());
                    }
                });
            }
        });
    }

    //    intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit)
    private void testOperatorCreateIntervalRange() {
        sIntervalRangeObservableSecond_1.subscribe(new Consumer<Long>() {
            @Override
            public void accept(final Long aLong) throws Exception {
                Log.d(TAG, "accept: testOperatorCreateIntervalRange thread == "
                        + Thread.currentThread());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        appendText(aLong.toString());
                    }
                });
            }
        });
    }

    // buffer(count)
    private void testOperatorTransformBuffer1() {
        Observable.range(9, 10).buffer(3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                appendText("List size == " + integers.size());
                for (Integer integer : integers) {
                    appendText(integer + "");
                }
            }
        });
    }

    // buffer(count, skip)
    private void testOperatorTransformBuffer2() {
        Observable.range(9, 10).buffer(3, 4).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                appendText("List size == " + integers.size());
                for (Integer integer : integers) {
                    appendText(integer + "");
                }
            }
        });
    }

    // buffer(Callable<? extends ObservableSource<B>>)
    // 使用该 buffer 订阅后，开始将原始 Observable 发送的 item 收集到 List 中，然后使用 buffer 参数的方法生成
    // 第二个 Observable，当第二个 Observable 发送对象时，buffer 会将当前的 List 发送出去，然后开始重复该过程：
    // 创建一个新的 List，创建另一个用于监听的 Observable。。。
    private void testOperatorTransformBuffer3() {
        sIntervalRangeObservableSecond_1.buffer(new Callable<Observable<Long>>() {
            @Override
            public Observable<Long> call() throws Exception {
//                return sIntervalRangeObservableSecond_2;//这个会产生错误，因为第一次发送没有延迟，将即时发送大量数据导致出错。
                return sIntervalRangeObservableWithDelaySecond_2_;
            }
        }).subscribe(new Consumer<List<Long>>() {
            @Override
            public void accept(final List<Long> longs) throws Exception {
                Log.d(TAG, "accept: testOperatorTransformBuffer3 buffer thread == "
                        + Thread.currentThread());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        appendText("List size == " + longs.size() + " & time == "
                                + new SimpleDateFormat("HH:mm:ss").format(
                                new Date(System.currentTimeMillis())));
                        for (Long i : longs) {
                            appendText(i.toString());
                        }
                    }
                });
            }
        });


    }

    // buffer(ObservableSource<B> boundary)
    private void testOperatorTransformBuffer4() {
        sIntervalObservableSecond_1.buffer(sIntervalObservableSecond_2).subscribe(
                new Consumer<List<Long>>() {
                    @Override
                    public void accept(final List<Long> longs) throws Exception {
                        Log.d(TAG, "accept: testOperatorTransformBuffer4 buffer thread == "
                                + Thread.currentThread());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                appendText("List size == " + longs.size());
                                for (Long i : longs) {
                                    appendText(i.toString());
                                }
                            }
                        });
                    }
                });
    }

    //flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper)
    private void testOperatorTransformFlatMap() {
        String[] strings1 = new String[]{"你好", "大傻逼", "我不好", "再见"};
        String[] strings2 = new String[]{"你好2", "大傻逼2", "我不好2", "再见2"};
        Observable.just(strings1, strings2).flatMap(
                new Function<String[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(
                            @io.reactivex.annotations.NonNull String[] strings)
                            throws Exception {
                        return Observable.fromArray(strings);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                appendText(s);
            }
        });
    }

}
