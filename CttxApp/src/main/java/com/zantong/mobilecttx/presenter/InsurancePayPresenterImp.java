package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.model.InsurancePayModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import cn.qqtheme.framework.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.zantong.mobilecttx.cordova.hwidget.CTTXInsurancePay;

/**
 * Created by 王海洋 on 2016/5/5.
 */
public class InsurancePayPresenterImp implements SimplePresenter, OnLoadServiceBackUI{

    private CTTXInsurancePay mCTTXInsurancePay;
    private InsurancePayModelImp mInsurancePayModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    private OpenQueryBean.RspInfoBean mRspInfoBean;
//    private CTTXHttpQueryPOSTInterface cttxHttpQueryPOSTInterface;

    public InsurancePayPresenterImp(CTTXInsurancePay mCTTXInsurancePay) {
        this.mCTTXInsurancePay = mCTTXInsurancePay;
        mInsurancePayModelImp = new InsurancePayModelImp();
//        mRspInfoBean = (OpenQueryBean.RspInfoBean) UserInfoRememberCtrl.readObject(mConsummateInfo, PublicData.getInstance().CarLocaelFlag);
    }


    private void init(){
    }

    @Override
    public void loadView(int index) {
        mCTTXInsurancePay.showProgress();
        switch (index){
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.i006.01");
                masp = new JSONObject() ;
//
                try {
                    masp.put("origtranserlnum", mCTTXInsurancePay.mapData().get("origtranserlnum"));
                    masp.put("polcyprignum", mCTTXInsurancePay.mapData().get("polcyprignum"));
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
//        Log.e("why",msg);
        mInsurancePayModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mCTTXInsurancePay.hideProgress();
        if(PublicData.getInstance().success.equals(((CTTXInsurancePayBean) clazz).getSYS_HEAD().getReturnCode())){
            mCTTXInsurancePay.updateView(clazz, index);
        }else{
           ToastUtils.showShort(mCTTXInsurancePay.cordova.getActivity(), ((CTTXInsurancePayBean) clazz).getSYS_HEAD().getReturnMessage());
        }
//        OpenQueryBean.RspInfoBean.UserCarsInfoBean mUserCarsInfoBean = new OpenQueryBean.RspInfoBean.UserCarsInfoBean();
//        mUserCarsInfoBean.setCarmodel("");
//        mUserCarsInfoBean.setCarnumtype(mAddVehicle.mapData().get("carnumtype"));
//        mUserCarsInfoBean.setCarnum(mAddVehicle.mapData().get("carnum"));
//        mRspInfoBean.getUserCarsInfo().add(mUserCarsInfoBean);
    }

    @Override
    public void onFailed() {
        mCTTXInsurancePay.hideProgress();
        ToastUtils.showShort(mCTTXInsurancePay.cordova.getActivity(), Config.getErrMsg("1"));
    }

}
