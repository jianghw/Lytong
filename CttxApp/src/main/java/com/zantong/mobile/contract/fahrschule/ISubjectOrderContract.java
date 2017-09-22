package com.zantong.mobile.contract.fahrschule;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

/**
 * 科目强化订单页面
 */

public interface ISubjectOrderContract {

    interface ISubjectOrderView extends IMvpView<ISubjectOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void bankPayHtmlError(String message);

        void bankPayHtmlSucceed(PayOrderResult result);
    }

    interface ISubjectOrderPresenter extends IMvpPresenter {
        void getBankPayHtml(String orderId, String orderPrice);
    }

}
