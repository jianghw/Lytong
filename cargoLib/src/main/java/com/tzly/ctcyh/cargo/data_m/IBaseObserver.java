package com.tzly.ctcyh.cargo.data_m;

/**
 * Created by jianghw on 2017/5/15.
 */

public interface IBaseObserver<T> {

    void doCompleted();

    void doError(Throwable e);

    void doNext(T t);
}
