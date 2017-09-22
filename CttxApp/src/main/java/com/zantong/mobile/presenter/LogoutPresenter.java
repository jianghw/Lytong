package com.zantong.mobile.presenter;

import android.content.Context;

import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.UserApiClient;
import com.zantong.mobile.base.BasePresenter;
import com.zantong.mobile.contract.ILoginView;
import com.zantong.mobile.user.bean.LoginResult;
import com.zantong.mobile.user.dto.LoginDTO;


/**
 * Created by zhengyingbing on 16/6/1.
 */
public class LogoutPresenter extends BasePresenter<ILoginView>{

    public void login(Context context) {
        LoginDTO dto = new LoginDTO();
        dto.setPhoenum(mView.getPhoenum());
        dto.setCaptcha(mView.getCaptcha());
        dto.setOnlyflag("165878");
        dto.setDevicetoken("45667890");
        dto.setPushswitch("1");
        dto.setPushmode("1");
        dto.setChkflg("0");

        UserApiClient.login(context, dto, new CallBack<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                String usrid = result.getRspInfo().getUsrid();

            }
        });
    }


}
