package com.zantong.mobile.presenter;

import android.widget.Toast;

import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.base.MessageFormat;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.login_v.OldLoginActivity;
import com.zantong.mobile.model.LoginInfoModelImp;
import com.zantong.mobile.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.user.bean.SmsBean;
import com.zantong.mobile.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobile.utils.SPUtils;
import com.zantong.mobile.utils.ValidateUtils;
import com.zantong.mobile.utils.rsa.Des3;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.utils.xmlparser.SHATools;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;


/**
 * Created by Administrator on 2016/5/5.
 */
public class LoginPhonePresenterImp implements SimplePresenter, OnLoadServiceBackUI {

    private OldLoginActivity mOldLoginActivity;
    private LoginInfoModelImp mLoginInfoModel;
    private String msg = "";
    private LoginInfoBean mLoginInfoBean;
    private JSONObject masp = null;
    private String onlyflag;

    public LoginPhonePresenterImp(OldLoginActivity mOldLoginActivity) {
        this.mOldLoginActivity = mOldLoginActivity;
        mLoginInfoModel = new LoginInfoModelImp();
    }

    @Override
    public void


    loadView(int index) {
        if (!ValidateUtils.isMobile(mOldLoginActivity.mapData().get("phoenum"))) {
            Toast.makeText(mOldLoginActivity, "请正确输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(PublicData.getInstance().imei)) {
            PublicData.getInstance().imei = "00000000";
        }
        mOldLoginActivity.showProgress();

        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.p001.01");
                masp = new JSONObject();
                try {
                    masp.put("phoenum", mOldLoginActivity.mapData().get("phoenum"));
                    masp.put("smsscene", "001");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u011.01");
                masp = new JSONObject();
                try {
                    String phone = RSAUtils.strByEncryption(mOldLoginActivity.mapData().get("phoenum"), true);
                    SHATools sha = new SHATools();
                    String pwd = RSAUtils.strByEncryption(
                            SHATools.hexString(sha.eccryptSHA1(mOldLoginActivity.mapData().get("pswd"))), true);

                    masp.put("phoenum", phone);
                    masp.put("pswd", pwd);
                    masp.put("devicetoken", PublicData.getInstance().imei);
                    masp.put("pushswitch", "0");
                    masp.put("pushmode", "2");
                    String token = RSAUtils.strByEncryption(PublicData.getInstance().deviceId, true);
                    masp.put("token", token);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mLoginInfoModel.loadUpdate(this, msg, index);
    }


    @Override
    public void onSuccess(Object mLoginInfoBean, int index) {
        mOldLoginActivity.hideProgress();
        if (index == 2 && !PublicData.getInstance().success.equals(((LoginInfoBean) mLoginInfoBean).getSYS_HEAD().getReturnCode())) {
            ToastUtils.showShort(mOldLoginActivity, ((LoginInfoBean) mLoginInfoBean).getSYS_HEAD().getReturnMessage());
            return;
        }
        switch (index) {
            case 1:
                mOldLoginActivity.setCodeTime();
                SmsBean smsBean = (SmsBean) mLoginInfoBean;
                onlyflag = smsBean.getRspInfo().getOnlyflag();
                break;
            case 2:

                this.mLoginInfoBean = (LoginInfoBean) mLoginInfoBean;

                this.mLoginInfoBean.getRspInfo().setFilenum(Des3.decode(this.mLoginInfoBean.getRspInfo().getFilenum()));
                this.mLoginInfoBean.getRspInfo().setPhoenum(Des3.decode(this.mLoginInfoBean.getRspInfo().getPhoenum()));
                this.mLoginInfoBean.getRspInfo().setCtfnum(Des3.decode(this.mLoginInfoBean.getRspInfo().getCtfnum()));
                this.mLoginInfoBean.getRspInfo().setRecdphoe(Des3.decode(this.mLoginInfoBean.getRspInfo().getRecdphoe()));

                UserInfoRememberCtrl.saveObject(UserInfoRememberCtrl.USERPD, mOldLoginActivity.mapData().get("pswd"));
                UserInfoRememberCtrl.saveObject(UserInfoRememberCtrl.USERDEVICE, PublicData.getInstance().imei);
                UserInfoRememberCtrl.saveObject(this.mLoginInfoBean.getRspInfo());

                PublicData.getInstance().mLoginInfoBean = this.mLoginInfoBean.getRspInfo();

                SPUtils.getInstance().setLoginInfoBean(PublicData.getInstance().mLoginInfoBean);

                PublicData.getInstance().userID = this.mLoginInfoBean.getRspInfo().getUsrid();
                PublicData.getInstance().filenum = this.mLoginInfoBean.getRspInfo().getFilenum();
                PublicData.getInstance().getdate = this.mLoginInfoBean.getRspInfo().getGetdate();

                PublicData.getInstance().loginFlag = true;
                mOldLoginActivity.addLoginInfo(this.mLoginInfoBean);
                break;
        }
    }

    @Override
    public void onFailed() {
        mOldLoginActivity.hideProgress();
        ToastUtils.showShort(mOldLoginActivity, Config.getErrMsg("1"));
    }
}
