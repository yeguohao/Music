package com.yeguohao.music.common.rx;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class RxArrays {

    public static <T, R> Observable<R> fill(Observable<T> source, Function<? super T, ? extends R> mapper) {
        return new ArraysObservable<>(source, mapper);
    }

}
