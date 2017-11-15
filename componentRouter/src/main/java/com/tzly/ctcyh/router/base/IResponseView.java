package com.tzly.ctcyh.router.base;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public interface IResponseView {

    void responseError(String message);

    void responseSucceed(Object response);
}
