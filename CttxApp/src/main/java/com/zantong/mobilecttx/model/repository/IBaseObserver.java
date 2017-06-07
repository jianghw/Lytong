package com.zantong.mobilecttx.model.repository;

/**
 * Created by jianghw on 2017/5/15.
 */

public interface IBaseObserver<T> {

    void doCompleted();

    void doError(Throwable e);

    void doNext(T t);
}
