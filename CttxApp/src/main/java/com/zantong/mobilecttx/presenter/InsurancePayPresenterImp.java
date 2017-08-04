package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.cordova.hwidget.CTTXInsurancePay;
import com.zantong.mobilecttx.model.InsurancePayModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.ToastUtils;

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

        mInsurancePayModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mCTTXInsurancePay.hideProgress();
        if(PublicData.getInstance().success.equals(((CTTXInsurancePayBean) clazz).getSYS_HEAD().getReturnCode())){
            mCTTXInsurancePay.updateView(clazz, index);
        }else{
           ToastUtils.toastShort(((CTTXInsurancePayBean) clazz).getSYS_HEAD().getReturnMessage());
        }
    }

    @Override
    public void onFailed() {
        mCTTXInsurancePay.hideProgress();
        ToastUtils.toastShort(Config.getErrMsg("1"));
    }

}
