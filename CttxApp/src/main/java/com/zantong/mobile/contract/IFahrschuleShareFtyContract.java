package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.fahrschule.bean.RecordCountResult;

/**
 * 驾校报名分享页面
 */

public interface IFahrschuleShareFtyContract {

    interface IFahrschuleShareFtyView extends IMvpView<IFahrschuleShareFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        String getType();

        void getRecordCountError(String message);

        void getRecordCountSucceed(RecordCountResult result);
    }

    interface IFahrschuleShareFtyPresenter extends IMvpPresenter {

        void getRecordCount();

        String getType();

        String getPhone();
    }

}
