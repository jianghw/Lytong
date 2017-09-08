package com.zantong.mobilecttx.presenter;

import android.widget.Toast;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.model.LoginInfoModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.SmsBean;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import cn.qqtheme.framework.util.ToastUtils;


/**
 * Created by Administrator on 2016/5/5.
 */
public class LoginPhonePresenterImp implements SimplePresenter, OnLoadServiceBackUI {

    private LoginActivity mLoginActivity;
    private LoginInfoModelImp mLoginInfoModel;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private LoginInfoBean mLoginInfoBean;
    private JSONObject masp = null;
    private String onlyflag;

    public LoginPhonePresenterImp(LoginActivity mLoginActivity) {
        this.mLoginActivity = mLoginActivity;
        mLoginInfoModel = new LoginInfoModelImp();
    }

    @Override
    public void loadView(int index) {
        if (!ValidateUtils.isMobile(mLoginActivity.mapData().get("phoenum"))) {
            Toast.makeText(mLoginActivity, "请正确输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(PublicData.getInstance().imei)) {
            PublicData.getInstance().imei = "00000000";
        }
        mLoginActivity.showProgress();
        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.p001.01");
                masp = new JSONObject();
                try {
                    masp.put("phoenum", mLoginActivity.mapData().get("phoenum"));
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
                    String phone = RSAUtils.strByEncryption(mLoginActivity.mapData().get("phoenum"), true);
                    SHATools sha = new SHATools();
                    String pwd = RSAUtils.strByEncryption(
                            SHATools.hexString(sha.eccryptSHA1(mLoginActivity.mapData().get("pswd"))), true);

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
        mLoginActivity.hideProgress();
        if (index == 2 && !PublicData.getInstance().success.equals(((LoginInfoBean) mLoginInfoBean).getSYS_HEAD().getReturnCode())) {
            ToastUtils.showShort(mLoginActivity, ((LoginInfoBean) mLoginInfoBean).getSYS_HEAD().getReturnMessage());
            return;
        }
        switch (index) {
            case 1:
                mLoginActivity.setCodeTime();
                SmsBean smsBean = (SmsBean) mLoginInfoBean;
                onlyflag = smsBean.getRspInfo().getOnlyflag();
                break;
            case 2:

                this.mLoginInfoBean = (LoginInfoBean) mLoginInfoBean;

                this.mLoginInfoBean.getRspInfo().setFilenum(Des3.decode(this.mLoginInfoBean.getRspInfo().getFilenum()));
                this.mLoginInfoBean.getRspInfo().setPhoenum(Des3.decode(this.mLoginInfoBean.getRspInfo().getPhoenum()));
                this.mLoginInfoBean.getRspInfo().setCtfnum(Des3.decode(this.mLoginInfoBean.getRspInfo().getCtfnum()));
                this.mLoginInfoBean.getRspInfo().setRecdphoe(Des3.decode(this.mLoginInfoBean.getRspInfo().getRecdphoe()));

                UserInfoRememberCtrl.saveObject(UserInfoRememberCtrl.USERPD, mLoginActivity.mapData().get("pswd"));
                UserInfoRememberCtrl.saveObject(UserInfoRememberCtrl.USERDEVICE, PublicData.getInstance().imei);
                UserInfoRememberCtrl.saveObject(this.mLoginInfoBean.getRspInfo());

                PublicData.getInstance().mLoginInfoBean = this.mLoginInfoBean.getRspInfo();

                SPUtils.getInstance().setLoginInfoBean(PublicData.getInstance().mLoginInfoBean);

                PublicData.getInstance().userID = this.mLoginInfoBean.getRspInfo().getUsrid();
                PublicData.getInstance().filenum = this.mLoginInfoBean.getRspInfo().getFilenum();
                PublicData.getInstance().getdate = this.mLoginInfoBean.getRspInfo().getGetdate();

                PublicData.getInstance().loginFlag = true;
                mLoginActivity.addLoginInfo(this.mLoginInfoBean);
                break;
        }
    }

    @Override
    public void onFailed() {
        mLoginActivity.hideProgress();
        ToastUtils.showShort(mLoginActivity, Config.getErrMsg("1"));
    }
}
