package com.zantong.mobilecttx.presenter;

import android.content.Context;

import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.interf.ILoginView;
import com.zantong.mobilecttx.user.bean.LoginResult;
import com.zantong.mobilecttx.user.dto.LoginDTO;

import cn.qqtheme.framework.util.LogUtils;

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
                LogUtils.i("RspInfo:"+result.getRspInfo().toString());
                LogUtils.i("usrid:"+usrid);
            }
        });
    }


}
