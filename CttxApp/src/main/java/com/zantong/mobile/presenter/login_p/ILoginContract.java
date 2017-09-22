package com.zantong.mobile.presenter.login_p;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;

/**
 * 登录页面
 */

public interface ILoginContract {

    interface ILoginView extends IMvpView<ILoginPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void userLoginError(String message);

        String getUserPhone();

        String getUserPassword();
    }

    interface ILoginPresenter extends IMvpPresenter {
        void userLogin();

        String initLoginDTO();
    }
}
