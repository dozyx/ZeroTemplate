package com.zerofate.template.purejava;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class RxJavaTest {
    public static void main(String[] args) {
        // 间隔打印
        Observable<Long> secondIntervals = Observable.interval(1, TimeUnit.SECONDS);
        secondIntervals.subscribe(s -> System.out.println(s));
        try {
            Thread.sleep(5000);// 确保主进程持续到发送完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Observable<String> strings = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        // 依次打印每个字符串的长度
        strings.map(s -> s.length()).subscribe(length -> System.out.println(length));
        // 依次打印每个字符串
        strings.subscribe(s -> System.out.println(s));
    }
}
