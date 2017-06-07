package com.zantong.mobilecttx.presenter;

import com.google.gson.Gson;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.model.SplashModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;
import com.zantong.mobilecttx.home.activity.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class SplashPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    SplashActivity mSplashActivity;
    SplashModelImp mSplashModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    private LoginInfoBean mLoginInfoBean;
    private String pwd;
    public SplashPresenter(SplashActivity mSplashActivity) {
        this.mSplashActivity = mSplashActivity;
        mSplashModelImp = new SplashModelImp();


    }

    private String getStartTime(){
        String nowMonth = Tools.getYearDate().substring(4,6);
        String oldMonth = PublicData.getInstance().mLoginInfoBean.getGetdate().substring(4,6);
        String startTime = "";
        if(Integer.parseInt(nowMonth) > Integer.parseInt(oldMonth)){
            startTime = Tools.getYearDate().substring(0, 4) + PublicData.getInstance().mLoginInfoBean.getGetdate().substring(4);
        }else{
            startTime = PublicData.getInstance().mLoginInfoBean.getGetdate();
        }
        return startTime;
    }

    @Override
    public void loadView(int index) {
        mSplashActivity.showProgress();
        switch (index){
            case 1:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u011.01");
                masp = new JSONObject();
                try {
                    pwd = (String) UserInfoRememberCtrl.readObject(mSplashActivity, UserInfoRememberCtrl.USERPD);
                    if(Tools.isStrEmpty(pwd)){
                        return;
                    }
                    masp.put("phoenum", PublicData.getInstance().mLoginInfoBean.getPhoenum());
                    SHATools sha = new SHATools();
                    masp.put("pswd", SHATools.hexString(sha.eccryptSHA1(pwd)));
                    masp.put("devicetoken", PublicData.getInstance().imei);
                    masp.put("pushswitch", "0");
                    masp.put("pushmode", "1");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
        }

        msg = MessageFormat.getInstance().getMessageFormat();
        mSplashModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mSplashActivity.hideProgress();
        switch (index){
            case 1:
                this.mLoginInfoBean = (LoginInfoBean) clazz;
                if(PublicData.getInstance().success.equals(mLoginInfoBean.getSYS_HEAD().getReturnCode())){
                    Gson gson = new Gson();
                    String json = gson.toJson(mLoginInfoBean);
                    UserInfoRememberCtrl.saveObject(mSplashActivity, UserInfoRememberCtrl.USERPD, pwd);
                    UserInfoRememberCtrl.saveObject(mSplashActivity, UserInfoRememberCtrl.USERDEVICE, PublicData.getInstance().imei);
                    UserInfoRememberCtrl.saveObject(mSplashActivity, this.mLoginInfoBean.getRspInfo());
                    LogUtils.i("usrid:" + this.mLoginInfoBean.getRspInfo().getUsrid());
                    PublicData.getInstance().mLoginInfoBean = this.mLoginInfoBean.getRspInfo();
                    PublicData.getInstance().userID = this.mLoginInfoBean.getRspInfo().getUsrid();
                    PublicData.getInstance().filenum = this.mLoginInfoBean.getRspInfo().getFilenum();
                    mSplashActivity.updateView(clazz, index);
                }
                break;
        }
    }

    @Override
    public void onFailed() {
//        ToastUtils.showShort(mSplashActivity, Config.getErrMsg("1"));
//        mSplashActivity.faildProgress();
    }
}
