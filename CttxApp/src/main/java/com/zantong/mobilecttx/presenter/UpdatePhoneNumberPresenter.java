package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.user.bean.SmsBean;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.model.UpdatePhoneNumberModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.user.activity.UpdatePhoneNumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
//
                try {
                    masp.put("phoenum", mUpdatePhoneNumber.mapData().get("phoenum"));
                    masp.put("smsscene", "002");
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u003.01");
                masp = new JSONObject();
//
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
                    String phone = RSAUtils.strByEncryption(mUpdatePhoneNumber, mUpdatePhoneNumber.mapData().get("phoenum"), true);
                    masp.put("phoenum", phone);
                    masp.put("devicetoken", PublicData.getInstance().imei);
                    masp.put("pushswitch", 0);
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.p002.01");
                masp = new JSONObject();
//
                try {
                    masp.put("phoenum", mUpdatePhoneNumber.mapData().get("phoenum"));
                    masp.put("smsscene", "002");
                    masp.put("captcha", mUpdatePhoneNumber.mapData().get("captcha"));
                    masp.put("onlyflag", onlyflag);
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
//        Log.e("why",msg);
        mUpdatePhoneNumberModelImp.loadUpdate(this, msg, index);

    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mUpdatePhoneNumber.hideDialogLoading();
        switch (index){
            case 1:
                SmsBean smsBean = (SmsBean) clazz;
                if(PublicData.getInstance().success.equals(smsBean.getSYS_HEAD().getReturnCode())){
                    onlyflag = smsBean.getRspInfo().getOnlyflag();
                    mUpdatePhoneNumber.updateView(clazz, index);
                }else{
                    ToastUtils.showShort(mUpdatePhoneNumber, smsBean.getSYS_HEAD().getReturnMessage());
                }
                break;
            case 2:
                this.mUpdateInfo = (UpdateInfo) clazz;
                if(PublicData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())){
                    mUpdatePhoneNumber.updateView(mUpdateInfo, index);
                }else{
                    ToastUtils.showShort(mUpdatePhoneNumber, mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
            case 3:
                this.mUpdateInfo = (UpdateInfo) clazz;
                if(PublicData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())){
                    loadView(2);
                }else{
                    ToastUtils.showShort(mUpdatePhoneNumber, mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
//                Gson gson = new Gson();
//                String json = gson.toJson(mLoginInfoBean);
//                UserInfoRememberCtrl.saveObject(mLoginPhone, this.mLoginInfoBean.getRspInfo());
//                mLoginPhone.addLoginInfo(this.mLoginInfoBean);
                break;
        }
    }

    @Override
    public void onFailed() {
        mUpdatePhoneNumber.hideDialogLoading();
        ToastUtils.showShort(mUpdatePhoneNumber, Config.getErrMsg("1"));
    }
}
