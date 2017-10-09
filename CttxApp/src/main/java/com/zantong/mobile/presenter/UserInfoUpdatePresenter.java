package com.zantong.mobile.presenter;

import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.base.BasePresenter;
import com.zantong.mobile.base.MessageFormat;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.contract.UserInfoUpdateView;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.login_v.LoginUserSPreference;
import com.zantong.mobile.model.UserInfoUpdateModel;
import com.zantong.mobile.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobile.user.activity.UserInfoUpdate;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：王海洋
 * 时间：2016/6/3 11:10
 */
public class UserInfoUpdatePresenter extends BasePresenter<UserInfoUpdateView> implements SimplePresenter, OnLoadServiceBackUI {

    private UserInfoUpdate mUserInfoUpdate;
    private UserInfoUpdateModel mUserInfoUpdateModel;
    private String msg = "";
    private JSONObject masp = null;

    public UserInfoUpdatePresenter(UserInfoUpdate mUserInfoUpdate) {
        this.mUserInfoUpdate = mUserInfoUpdate;
        mUserInfoUpdateModel = new UserInfoUpdateModel();
    }

    @Override
    public void loadView(int index) {
        mUserInfoUpdate.showDialogLoading();
        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u003.01");
                masp = new JSONObject();
                try {
                    masp.put("usrid", MemoryData.getInstance().userID);
                    masp.put("portrait", mUserInfoUpdate.mapData().get("portrait"));
                    masp.put("devicetoken", MemoryData.getInstance().imei);
                    masp.put("pushswitch", 0);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mUserInfoUpdateModel.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mUserInfoUpdate.hideDialogLoading();
        UpdateInfo mUpdateInfo = (UpdateInfo) clazz;
        switch (index) {
            case 1:
                if (MemoryData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())) {
                    mUserInfoUpdate.updateView(clazz, index);
                    MemoryData.getInstance().mLoginInfoBean.setPortrait(mUserInfoUpdate.mapData().get("portrait"));
                    LoginUserSPreference.saveObject(MemoryData.getInstance().mLoginInfoBean);
                } else {
                    mUserInfoUpdate.setUserHeadImage();
                    ToastUtils.toastShort(mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mUserInfoUpdate.hideDialogLoading();
        mUserInfoUpdate.setUserHeadImage();
        ToastUtils.toastShort(Config.getErrMsg("1"));
    }

}
