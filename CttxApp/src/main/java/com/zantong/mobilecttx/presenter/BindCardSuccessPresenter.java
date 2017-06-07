package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.model.UpdateNickNameModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.card.activity.BindCardSuccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class BindCardSuccessPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    BindCardSuccess mBindCardSuccess;
    UpdateNickNameModelImp mUpdateNickNameModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    public BindCardSuccessPresenter(BindCardSuccess mBindCardSuccess) {
        this.mBindCardSuccess = mBindCardSuccess;
        mUpdateNickNameModelImp = new UpdateNickNameModelImp();


    }


    @Override
    public void loadView(int index) {
        mBindCardSuccess.showDialogLoading();
        switch (index){
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u003.01");
                masp = new JSONObject() ;
//
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
                    masp.put("getdate", PublicData.getInstance().mHashMap.get("getdate"));
                    masp.put("devicetoken", PublicData.getInstance().imei);
                    masp.put("phoenum", PublicData.getInstance().mLoginInfoBean.getPhoenum());
                    masp.put("filenum", PublicData.getInstance().mHashMap.get("filenum"));
                    masp.put("ctfnum", PublicData.getInstance().mHashMap.get("ctfnum"));
                    masp.put("pushswitch", 0);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
//        Log.e("why",msg);
        mUpdateNickNameModelImp.loadUpdate(this, msg, index);

    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mBindCardSuccess.hideDialogLoading();
        switch (index){
            case 1:
                UpdateInfo mUpdateInfo = (UpdateInfo) clazz;
                if(PublicData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())){
                    mBindCardSuccess.updateView(clazz, index);
                }else{
                    ToastUtils.showShort(mBindCardSuccess, mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mBindCardSuccess.hideDialogLoading();
        ToastUtils.showShort(mBindCardSuccess, Config.getErrMsg("1"));
    }
}
