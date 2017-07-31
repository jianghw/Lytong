package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;

/**
 * 畅通页面
 */

public interface ISubjectIntensifyContract {

    interface ISubjectIntensifyView extends IMvpView<ISubjectIntensifyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

    }

    interface ISubjectIntensifyPresenter extends IMvpPresenter {

    }

}
