package com.zantong.mobile.presenter;

import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.base.BasePresenter;
import com.zantong.mobile.base.MessageFormat;
import com.zantong.mobile.base.interf.IBaseView;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.model.UpdateNickNameModelImp;
import com.zantong.mobile.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobile.user.activity.UpdateNickName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.tzly.annual.base.util.ToastUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class UpdateNickNamePresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    UpdateNickName mUpdateNickName;
    UpdateNickNameModelImp mUpdateNickNameModelImpc;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    public UpdateNickNamePresenter(UpdateNickName mUpdateNickName) {
        this.mUpdateNickName = mUpdateNickName;
        mUpdateNickNameModelImpc = new UpdateNickNameModelImp();
    }

    @Override
    public void loadView(int index) {
        mUpdateNickName.showDialogLoading();
        switch (index){
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u003.01");
                masp = new JSONObject() ;
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
                    masp.put("nickname", mUpdateNickName.mapData().get("nickname"));
                    masp.put("devicetoken", PublicData.getInstance().imei);
                    masp.put("pushswitch", 0);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mUpdateNickNameModelImpc.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mUpdateNickName.hideDialogLoading();
        switch (index){
            case 1:
                UpdateInfo mUpdateInfo = (UpdateInfo) clazz;
                if(PublicData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())){
                    mUpdateNickName.updateView(clazz, index);
                }else{
                    ToastUtils.toastShort(mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mUpdateNickName.hideDialogLoading();
        ToastUtils.showShort(mUpdateNickName, Config.getErrMsg("1"));
    }
}
