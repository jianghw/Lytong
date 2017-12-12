package com.tzly.ctcyh.user.register_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public interface IRegisterContract {

    interface IRegisterView extends IBaseView<IRegisterPresenter> {

        String getUserPhone();

        String getUserPassword();

        void v_u001_01Error(String message);

        void v_u001_01Succeed(LoginResponse response);
    }

    interface IRegisterPresenter extends IBasePresenter {

        void v_u001_01();

        void initPhoneDeviceId();
    }

}
