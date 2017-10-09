package com.zantong.mobile.presenter;

import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.base.BasePresenter;
import com.zantong.mobile.base.MessageFormat;
import com.zantong.mobile.base.interf.IBaseView;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.model.UpdatePhoneNumberModelImp;
import com.zantong.mobile.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobile.user.activity.UpdatePhoneNumber;
import com.zantong.mobile.user.bean.SmsBean;
import com.zantong.mobile.utils.rsa.RSAUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.tzly.annual.base.util.ToastUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class UpdatePhoneNumberPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    UpdatePhoneNumber mUpdatePhoneNumber;
    UpdatePhoneNumberModelImp mUpdatePhoneNumberModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    private String onlyflag;
    private UpdateInfo mUpdateInfo;
    private SmsBean mSmsBean;

    public UpdatePhoneNumberPresenter(UpdatePhoneNumber mUpdatePhoneNumber) {
        this.mUpdatePhoneNumber = mUpdatePhoneNumber;
        mUpdatePhoneNumberModelImp = new UpdatePhoneNumberModelImp();
    }


    @Override
    public void loadView(int index) {
        mUpdatePhoneNumber.showDialogLoading();
        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u014.01");
                masp = new JSONObject();

                try {
                    masp.put("phoenum", mUpdatePhoneNumber.mapData().get("phoenum"));
                    masp.put("smsscene", "002");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u003.01");
                masp = new JSONObject();
                try {
                    masp.put("usrid", MemoryData.getInstance().userID);
                    String phone = RSAUtils.strByEncryption(mUpdatePhoneNumber.mapData().get("phoenum"), true);
                    masp.put("phoenum", phone);
                    masp.put("devicetoken", MemoryData.getInstance().imei);
                    masp.put("pushswitch", 0);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.p002.01");
                masp = new JSONObject();
                try {
                    masp.put("phoenum", mUpdatePhoneNumber.mapData().get("phoenum"));
                    masp.put("smsscene", "002");
                    masp.put("captcha", mUpdatePhoneNumber.mapData().get("captcha"));
                    masp.put("onlyflag", onlyflag);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mUpdatePhoneNumberModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mUpdatePhoneNumber.hideDialogLoading();
        switch (index){
            case 1:
                SmsBean smsBean = (SmsBean) clazz;
                if(MemoryData.getInstance().success.equals(smsBean.getSYS_HEAD().getReturnCode())){
                    onlyflag = smsBean.getRspInfo().getOnlyflag();
                    mUpdatePhoneNumber.updateView(clazz, index);
                }else{
                    ToastUtils.toastShort(smsBean.getSYS_HEAD().getReturnMessage());
                }
                break;
            case 2:
                this.mUpdateInfo = (UpdateInfo) clazz;
                if(MemoryData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())){
                    mUpdatePhoneNumber.updateView(mUpdateInfo, index);
                }else{
                    ToastUtils.toastShort(mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
            case 3:
                this.mUpdateInfo = (UpdateInfo) clazz;
                if(MemoryData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())){
                    loadView(2);
                }else{
                    ToastUtils.toastShort(mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mUpdatePhoneNumber.hideDialogLoading();
        ToastUtils.toastShort(Config.getErrMsg("1"));
    }
}
