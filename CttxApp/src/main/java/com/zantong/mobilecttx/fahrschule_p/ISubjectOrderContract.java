package com.zantong.mobilecttx.fahrschule_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

/**
 * 科目强化订单页面
 */

public interface ISubjectOrderContract {

    interface ISubjectOrderView extends IBaseView<ISubjectOrderPresenter> {
        void bankPayHtmlError(String message);

        void bankPayHtmlSucceed(PayOrderResponse result);
    }

    interface ISubjectOrderPresenter extends IBasePresenter {
        void getBankPayHtml(String orderId, String orderPrice);
    }

}
