package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;

/**
 * 科目强化订单页面
 */

public interface ISubjectOrderContract {

    interface ISubjectOrderView extends IMvpView<ISubjectOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

    }

    interface ISubjectOrderPresenter extends IMvpPresenter {

    }

}
