package com.zantong.mobilecttx.presenter;

import android.text.TextUtils;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.CarInfoEvent;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.model.IllegalViolationModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.VehicleTypeTools;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.QueryResultActivity;
import com.zantong.mobilecttx.weizhang.bean.AddVehicleBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.qqtheme.framework.util.LogUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class IllegalViolationPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    QueryResultActivity mQueryResultActivity;
    IllegalViolationModelImp mIllegalViolationModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    private List<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean> ViolationInfo;
    private OpenQueryBean.RspInfoBean mRspInfoBean;
    private String carNumber;
    private String carNumberType;
    private String engineNumber;
    private String defaultflag;
    public IllegalViolationPresenter(QueryResultActivity mQueryResultActivity) {
        this.mQueryResultActivity = mQueryResultActivity;
        mIllegalViolationModelImp = new IllegalViolationModelImp();


    }


    @Override
    public void loadView(int index) {
        mQueryResultActivity.showProgress();
        switch (index){
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.v002.01");
                masp = new JSONObject() ;
//
                try {
                    if(TextUtils.isEmpty(PublicData.getInstance().imei)){
                        masp.put("token", RSAUtils.strByEncryption(mQueryResultActivity, "00000000", true));
                    }else{
                        masp.put("token", RSAUtils.strByEncryption(mQueryResultActivity,PublicData.getInstance().imei, true));
                    }
                    masp.put("carnumtype", mQueryResultActivity.mapData().get("carnumtype"));
                    masp.put("carnum", RSAUtils.strByEncryption(mQueryResultActivity, mQueryResultActivity.mapData().get("carnum"), true));
                    masp.put("enginenum", RSAUtils.strByEncryption(mQueryResultActivity, mQueryResultActivity.mapData().get("enginenum"), true));
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                mQueryResultActivity.showProgress();
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c001.01");
                masp = new JSONObject() ;
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
                    masp.put("opertype", "1");
                    masp.put("carnumtype", mQueryResultActivity.mapData().get("carnumtype"));
                    masp.put("carnum", RSAUtils.strByEncryption(mQueryResultActivity, mQueryResultActivity.mapData().get("carnum").toString(), true));
                    masp.put("enginenum", RSAUtils.strByEncryption(mQueryResultActivity, mQueryResultActivity.mapData().get("enginenum").toString(), true));
//                    masp.put("carmodel","");
                    masp.put("ispaycar", "0");
//                    if(PublicData.getInstance().isPayCar < 2){
//                    }else{
//                        masp.put("ispaycar", "1");
//                    }
                    if(PublicData.getInstance().defaultCar){
                        masp.put("defaultflag", "0");
                    }else{
                        masp.put("defaultflag", "1");
                    }
                    masp.put("inspectflag", "0");
                    masp.put("violationflag", "0");
                    carNumberType = VehicleTypeTools.switchVehicleCode(mQueryResultActivity.mapData().get("carnumtype"));
                    carNumber  = mQueryResultActivity.mapData().get("carnum").toString();
                    engineNumber = mQueryResultActivity.mapData().get("enginenum").toString();
                    defaultflag = masp.get("defaultflag").toString();
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
//        Log.e("why",msg);
        mIllegalViolationModelImp.loadUpdate(this, msg, index);

    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mQueryResultActivity.hideProgress();
        switch (index){
            case 1:
                LogUtils.i("1");
                AddVehicleBean mAddVehicleBean = (AddVehicleBean) clazz;
                ViolationInfo = mAddVehicleBean.getRspInfo().getViolationInfo();
                if(PublicData.getInstance().success.equals(mAddVehicleBean.getSYS_HEAD().getReturnCode())){
                    mQueryResultActivity.updateView(clazz, index);

                }else{
                    LogUtils.i("2");
                    ToastUtils.showShort(mQueryResultActivity, mAddVehicleBean.getSYS_HEAD().getReturnMessage());
                }
                break;
            case 2:
                mQueryResultActivity.hideProgress();
                UpdateInfo mUpdateInfo = (UpdateInfo) clazz;
                if(PublicData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())){
                    if(!PublicData.getInstance().defaultCar){
                        PublicData.getInstance().defaultCar = true;
                        PublicData.getInstance().defaultCarNumber = carNumber;
                        AccountRememberCtrl.saveDefaultNumber(mQueryResultActivity, carNumber);
                    }
                    EventBus.getDefault().post(new CarInfoEvent(true));
                    mRspInfoBean = (OpenQueryBean.RspInfoBean) UserInfoRememberCtrl.readObject(mQueryResultActivity, PublicData.getInstance().CarLocalFlag);
                    OpenQueryBean.RspInfoBean.UserCarsInfoBean mUserCarsInfoBean = new OpenQueryBean.RspInfoBean.UserCarsInfoBean();
                    mUserCarsInfoBean.setCarnum(carNumber);
                    mUserCarsInfoBean.setCarnumtype(carNumberType);
                    mUserCarsInfoBean.setEnginenum(engineNumber);
                    mUserCarsInfoBean.setDefaultflag(defaultflag);
                    mUserCarsInfoBean.setIspaycar("0");
                    mUserCarsInfoBean.setInspectflag("0");
                    mUserCarsInfoBean.setViolationflag("0");
                    mUserCarsInfoBean.setViolationInfo(ViolationInfo);
                    mRspInfoBean.getUserCarsInfo().add(mUserCarsInfoBean);
                    UserInfoRememberCtrl.saveObject(mQueryResultActivity, PublicData.getInstance().CarLocalFlag, mRspInfoBean);

                    mQueryResultActivity.updateView(clazz, index);
                }else{
                    ToastUtils.showShort(mQueryResultActivity, mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        LogUtils.i("3");
        mQueryResultActivity.hideProgress();
//        ToastUtils.showShort(mQueryResultActivity, Config.getErrMsg("1"));
    }
}
