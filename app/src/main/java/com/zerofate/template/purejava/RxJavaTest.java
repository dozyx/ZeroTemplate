package com.zerofate.template.purejava;
import io.reactivex.Observable;

public class RxJavaTest {
    public static void main(String[] args) {
        Observable<String> strings = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        strings.subscribe(s -> System.out.println(s));
    }
}
