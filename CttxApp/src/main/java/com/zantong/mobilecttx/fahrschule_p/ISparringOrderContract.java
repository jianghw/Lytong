package com.zantong.mobilecttx.fahrschule_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

/**
 * 驾校陪练订单页面
 */

public interface ISparringOrderContract {

    interface ISparringOrderView extends IBaseView<ISparringOrderPresenter> {

        void bankPayHtmlError(String message);

        void bankPayHtmlSucceed(PayOrderResponse result);
    }

    interface ISparringOrderPresenter extends IBasePresenter {

        void getBankPayHtml(String s, String stringMoney);
    }

}
