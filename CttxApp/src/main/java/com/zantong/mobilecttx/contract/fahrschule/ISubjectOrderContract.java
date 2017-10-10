package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

/**
 * 科目强化订单页面
 */

public interface ISubjectOrderContract {

    interface ISubjectOrderView extends IMvpView<ISubjectOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void bankPayHtmlError(String message);

        void bankPayHtmlSucceed(PayOrderResponse result);
    }

    interface ISubjectOrderPresenter extends IMvpPresenter {
        void getBankPayHtml(String orderId, String orderPrice);
    }

}
