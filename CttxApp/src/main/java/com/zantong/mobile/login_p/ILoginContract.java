package com.zantong.mobile.login_p;

import com.tzly.annual.base.bean.request.RegisterDTO;
import com.tzly.annual.base.bean.response.LoginResult;
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

        void userLoginSucceed();

        void registerSucceed();

        void registerError(String message);

        void loginError(String message);

        void loginSucceed(LoginResult result);
    }

    interface ILoginPresenter extends IMvpPresenter {
        void userLogin();

        String initLoginDTO();

        void register();

        RegisterDTO initRegisterDTO();

        String initLoginV004DTO();

        void loginV004();

        void innerUserLogin();
    }
}
