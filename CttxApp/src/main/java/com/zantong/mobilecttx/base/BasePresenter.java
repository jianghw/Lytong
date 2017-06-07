package com.zantong.mobilecttx.base;

/**
 * presenter基类
 * @author Sandy
 * create at 16/6/2 下午2:14
 */
public abstract class BasePresenter<T> {

    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void dettach(){
        mView = null;
    }
}
