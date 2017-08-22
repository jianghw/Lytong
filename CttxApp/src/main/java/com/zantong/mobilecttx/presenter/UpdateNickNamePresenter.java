package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.model.UpdateNickNameModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.user.activity.UpdateNickName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.ToastUtils;

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
