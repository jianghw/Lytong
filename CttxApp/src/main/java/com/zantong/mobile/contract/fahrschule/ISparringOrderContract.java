package com.zantong.mobile.contract.fahrschule;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

/**
 * 驾校陪练订单页面
 */

public interface ISparringOrderContract {

    interface ISparringOrderView extends IMvpView<ISparringOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void bankPayHtmlError(String message);

        void bankPayHtmlSucceed(PayOrderResult result);
    }

    interface ISparringOrderPresenter extends IMvpPresenter {

        void getBankPayHtml(String s, String stringMoney);
    }

}
