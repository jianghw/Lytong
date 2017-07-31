package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;

/**
 * 驾校陪练订单页面
 */

public interface ISparringOrderContract {

    interface ISparringOrderView extends IMvpView<ISparringOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

    }

    interface ISparringOrderPresenter extends IMvpPresenter {

    }

}
