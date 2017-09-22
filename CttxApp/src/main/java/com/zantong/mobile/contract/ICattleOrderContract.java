package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;

/**
 * 订单页面
 */

public interface ICattleOrderContract {

    interface ICattleOrderView extends IMvpView<ICattleOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void queryOrderListError(String message);
    }

    interface ICattleOrderPresenter extends IMvpPresenter {

        void queryOrderList();
    }
}
