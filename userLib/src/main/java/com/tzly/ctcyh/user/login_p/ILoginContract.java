package com.tzly.ctcyh.user.login_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public interface ILoginContract {

    interface ILoginView extends IBaseView<ILoginPresenter> {
        void userLoginError(String message);

        String getUserPhone();

        String getUserPassword();

        void userLoginSucceed(LoginResponse loginInfoBean);

        void registerSucceed();

        void registerError(String message);
    }

    interface ILoginPresenter extends IBasePresenter {
        void userLogin();

        String initLoginDTO();

        void register(LoginResponse loginResponse);

        RegisterDTO initRegisterDTO(LoginResponse loginResponse);

        String initLoginV004DTO(String filenum);

        void loginV004(String filenum);

        void initPhoneDeviceId();
    }

}
