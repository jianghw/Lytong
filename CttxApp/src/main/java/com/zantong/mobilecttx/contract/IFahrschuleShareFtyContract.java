package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;

/**
 * 驾校报名分享页面
 */

public interface IFahrschuleShareFtyContract {

    interface IFahrschuleShareFtyView extends IMvpView<IFahrschuleShareFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        String getType();

        void getRecordCountError(String message);

        void getRecordCountSucceed(RecordCountResponse result);
    }

    interface IFahrschuleShareFtyPresenter extends IMvpPresenter {

        void getRecordCount();

        String getType();

        String getPhone();
    }

}
