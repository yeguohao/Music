package com.yeguohao.music.common.rx;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class ArraysObservable<T, R> extends Observable<R> {

    private Observable<T> source;
    private Function<? super T, ? extends R> mapper;

    private Disposable d;

    public ArraysObservable(Observable<T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        source.subscribe(t -> {
                    if (d.isDisposed()) return;
                    R apply;
                    try {
                        apply = mapper.apply(t);
                    } catch (Exception e) {
                        d.dispose();
                        observer.onError(e);
                        return;
                    }
                    observer.onNext(apply);
                },
                observer::onError,
                observer::onComplete,
                disposable -> {
                    d = disposable;
                    observer.onSubscribe(disposable);
                });
    }
}
