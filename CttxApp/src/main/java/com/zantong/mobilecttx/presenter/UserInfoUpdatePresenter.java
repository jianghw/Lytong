package com.zantong.mobilecttx.presenter;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.service.IUserService;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.contract.UserInfoUpdateView;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.model.UserInfoUpdateModel;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.activity.UserInfoUpdate;

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
                    masp.put("usrid", MainRouter.getUserID());
                    masp.put("portrait", mUserInfoUpdate.mapData().get("portrait"));
                    masp.put("devicetoken", MainRouter.getPhoneDeviceId());
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
                if (LoginData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())) {
                    mUserInfoUpdate.updateView(clazz, index);

                    //更新头像
                    ServiceRouter serviceRouter = ServiceRouter.getInstance();
                    if (serviceRouter.getService(IUserService.class.getSimpleName()) != null) {
                        IUserService service = (IUserService) serviceRouter
                                .getService(IUserService.class.getSimpleName());
                        service.saveUserPortrait(mUserInfoUpdate.mapData().get("portrait"));
                    } else {
                        //注册机开始工作
                        ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
                    }
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
