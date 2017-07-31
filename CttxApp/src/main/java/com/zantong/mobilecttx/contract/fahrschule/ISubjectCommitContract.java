package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;

/**
 * 科目强化订单页面
 */

public interface ISubjectCommitContract {

    interface ISubjectCommitView extends IMvpView<ISubjectCommitPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();
    }

    interface ISubjectCommitPresenter extends IMvpPresenter {

    }

}
