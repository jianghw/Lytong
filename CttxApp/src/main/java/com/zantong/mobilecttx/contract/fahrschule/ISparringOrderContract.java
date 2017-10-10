package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

/**
 * 驾校陪练订单页面
 */

public interface ISparringOrderContract {

    interface ISparringOrderView extends IMvpView<ISparringOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void bankPayHtmlError(String message);

        void bankPayHtmlSucceed(PayOrderResponse result);
    }

    interface ISparringOrderPresenter extends IMvpPresenter {

        void getBankPayHtml(String s, String stringMoney);
    }

}
