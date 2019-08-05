package cn.dozyx.template.justfortest;

import androidx.test.espresso.core.internal.deps.guava.collect.Lists;

import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.TestScheduler;

/**
 * Create by timon on 2019/5/21
 **/

public class RxJavaTest {
    private final List<String> items = Lists.newArrayList("a", "b", "c", "d", "e", "f");
    private static final String SOURCE = "abcdef";


    @Test
    public void flatMap() throws Exception {

        final TestScheduler scheduler = new TestScheduler();
        Observable.fromIterable(items).flatMap(s -> {
            final int delay = new Random().nextInt(10);
            return Observable.just(s + "x" + delay).delay(delay, TimeUnit.SECONDS, scheduler);
        }).toList().doOnSuccess(System.out::println).subscribe();
        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);

    }

    @Test
    public void switchMap() throws Exception {

        final TestScheduler scheduler = new TestScheduler();
        Observable.fromIterable(items).switchMap(s -> {
            final int delay = new Random().nextInt(10);
            Observable<String> observable = Observable.just(s + "x" + delay);
            if ("f".equals(s)) {
                observable = observable.delay(delay, TimeUnit.SECONDS, scheduler);
            }
            System.out.println("apply: " + s + delay);
            return observable;
//            return Observable.just(s + "x" + delay).delay(delay, TimeUnit.SECONDS, scheduler);
        }).toList().doOnSuccess(System.out::println).subscribe();
        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);

    }

    @Test
    public void concatMap() throws Exception {

        final TestScheduler scheduler = new TestScheduler();
        Observable.fromIterable(items).concatMap(s -> {
            final int delay = new Random().nextInt(10);
            System.out.println("apply: " + s + delay);
            return Observable.just(s + "x" + delay).delay(delay, TimeUnit.SECONDS, scheduler);
        }).toList().doOnSuccess(System.out::println).subscribe();
        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);

    }

    @Test
    public void flatMap2() {
        Observable.fromArray(SOURCE.toCharArray()).flatMap(new Function<char[], ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(char[] chars) throws Exception {
                return null;
            }
        });
    }

    @Test
    public void map() {
        Observable.fromIterable(items).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return (int) s.toCharArray()[0];
            }
        }).doOnNext(System.out::println).subscribe();
    }
}
