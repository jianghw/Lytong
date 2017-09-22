package com.zantong.mobile.contract.fahrschule;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.tzly.annual.base.bean.response.SubjectGoodsResult;

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
