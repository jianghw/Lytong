package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.interf.UserInfoUpdateView;
import com.zantong.mobilecttx.model.UserInfoUpdateModel;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.user.activity.UserInfoUpdate;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import cn.qqtheme.framework.util.ToastUtils;

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
                    masp.put("usrid", PublicData.getInstance().userID);
                    masp.put("portrait", mUserInfoUpdate.mapData().get("portrait"));
                    masp.put("devicetoken", PublicData.getInstance().imei);
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
                if (PublicData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())) {
                    mUserInfoUpdate.updateView(clazz, index);
                    PublicData.getInstance().mLoginInfoBean.setPortrait(mUserInfoUpdate.mapData().get("portrait"));
                    UserInfoRememberCtrl.saveObject(PublicData.getInstance().mLoginInfoBean);
                } else {
                    mUserInfoUpdate.setUserHeadImage();
                    ToastUtils.showShort(mUserInfoUpdate, mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mUserInfoUpdate.hideDialogLoading();
        mUserInfoUpdate.setUserHeadImage();
        ToastUtils.showShort(mUserInfoUpdate, Config.getErrMsg("1"));
    }

}
