package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.user.bean.LoginInfoBean;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface LoginPhoneView {
    void showProgress();
    void addLoginInfo(LoginInfoBean mLoginInfoBean);
    void hideProgress();

}
