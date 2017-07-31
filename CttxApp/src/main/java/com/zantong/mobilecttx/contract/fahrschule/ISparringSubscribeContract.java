package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;

/**
 * 驾校陪练订单页面
 */

public interface ISparringSubscribeContract {

    interface ISparringSubscribeView extends IMvpView<ISparringSubscribePresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

    }

    interface ISparringSubscribePresenter extends IMvpPresenter {

    }

}
