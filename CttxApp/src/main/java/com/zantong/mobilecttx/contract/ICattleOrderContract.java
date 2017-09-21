package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;

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
