package com.tzly.ctcyh.pay.html_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description: 支付方式
 * Update by:
 * Update day:
 */

public interface IHtmlPayContract {

    interface IHtmlPayView extends IBaseView<IHtmlPayPresenter> {
    }

    interface IHtmlPayPresenter extends IBasePresenter {
        void orderDetail();
    }

}
