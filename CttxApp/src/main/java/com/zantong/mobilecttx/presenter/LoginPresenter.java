package com.zantong.mobilecttx.presenter;

import android.content.Context;

import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.user.bean.LoginResult;
import com.zantong.mobilecttx.user.dto.LoginDTO;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.interf.ILoginView;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class LoginPresenter extends BasePresenter<ILoginView>{

    public void login(final Context context) {
        LoginDTO dto = new LoginDTO();
        dto.setPhoenum(mView.getPhoenum());
        dto.setCaptcha(mView.getCaptcha());
        UserApiClient.login(context, dto, new CallBack<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                String usrid = result.getRspInfo().getUsrid();
                LogUtils.i("RspInfo:"+result.getRspInfo().toString());
                LogUtils.i("usrid:"+usrid);
//                PrefUtils.getInstance(context).setToken(usrid);
//                PrefUtils.getInstance(context).setIsLogin(true);
//                PrefUtils.getInstance(context).setFilenum(result.getRspInfo().getFilenum());
            }

            @Override
            public void onError(String errorCode, String msg) {
                LogUtils.i("errorCode"+errorCode);
            }
        });
    }


}
