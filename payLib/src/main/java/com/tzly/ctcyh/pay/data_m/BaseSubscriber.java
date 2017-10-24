package com.tzly.ctcyh.pay.data_m;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by jianghw on 2017/5/15.
 * 统一封装处理类
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> implements IBaseObserver<T> {

    @Override
    public void onCompleted() {
        doCompleted();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            doError(new Throwable("网络链接超时，请检查您的网络状态，稍后重试！"));
        } else if (e instanceof ConnectException) {
            doError(new Throwable("网络链接异常，请检查您的网络状态"));
        } else if (e instanceof UnknownHostException) {
            doError(new Throwable("网络异常，请检查您的网络状态"));
        } else {
            doError(e);
        }
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        doNext(t);
    }
}
