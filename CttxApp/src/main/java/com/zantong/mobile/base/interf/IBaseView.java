package com.zantong.mobile.base.interf;

/**
 * 其他view层接口继承该接口,不必重复写显示和隐藏loading的方法
 * @author Sandy
 * create at 16/6/2 下午2:14
 */
public interface IBaseView {

    void showLoading();
    void hideLoading();

}
