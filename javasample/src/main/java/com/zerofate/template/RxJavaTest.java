package com.zerofate.template;

import io.reactivex.Observable;

public class RxJavaTest {
    public static void main(String[] args) {
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("Alpha");
            emitter.onNext("Beta");
            emitter.onNext("Gamma");
            emitter.onNext("Delta");
            emitter.onNext("Epsilon");
            emitter.onComplete();
        });
        source.map(String::length).filter(integer -> integer > 5).subscribe(s -> System.out.println("RECEIVED: " + s),Throwable::printStackTrace);;
    }
}
