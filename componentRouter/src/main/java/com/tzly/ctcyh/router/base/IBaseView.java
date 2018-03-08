package com.tzly.ctcyh.router.base;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public interface IBaseView<P extends IBasePresenter> {

    void showLoading();

    void dismissLoading();

    void setPresenter(P presenter);
}