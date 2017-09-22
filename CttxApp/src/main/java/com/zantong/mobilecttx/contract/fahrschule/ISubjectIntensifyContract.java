package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.tzly.annual.base.contract.bean.SubjectGoodsResult;

/**
 * 畅通页面
 */

public interface ISubjectIntensifyContract {

    interface ISubjectIntensifyView extends IMvpView<ISubjectIntensifyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getGoodsError(String message);

        void getGoodsSucceed(SubjectGoodsResult result);
    }

    interface ISubjectIntensifyPresenter extends IMvpPresenter {

        void getGoods();
    }

}
