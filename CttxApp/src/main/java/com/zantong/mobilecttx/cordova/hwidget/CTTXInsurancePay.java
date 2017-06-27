package com.zantong.mobilecttx.cordova.hwidget;

import android.content.Intent;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;
import com.zantong.mobilecttx.presenter.InsurancePayPresenterImp;
import com.zantong.mobilecttx.utils.NetUtils;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.weizhang.activity.PayWebActivity;
import com.zantong.mobilecttx.interf.ModelView;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXInsurancePay extends CordovaPlugin implements ModelView{
    private String payUrl = "";
    private final int insurancePay = 1;
    private InsurancePayPresenterImp mInsurancePayPresenterImp;
    HashMap<String, String>  mHashMap = new HashMap<>();
    private CallbackContext callbackContext;


    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if("insurancePay".equals(action)){
            String merCustomIp = NetUtils.getPhontIP(cordova.getActivity());
            this.callbackContext = callbackContext;
            payUrl = args.getString(0);
            payUrl += "&clientIP="+merCustomIp+"&userName="+PublicData.getInstance().userID;
            mapData().put("origtranserlnum", args.getString(1));
            mapData().put("polcyprignum", args.getString(2));
            PublicData.getInstance().mHashMap.put("PayWebActivity", payUrl);
            Intent intent = new Intent(this.cordova.getActivity(), PayWebActivity.class);
            this.cordova.startActivityForResult(this, intent, 1);
            return true;
        }
        return super.execute(action, args, callbackContext);
    }


    public HashMap<String, String> mapData(){
        return mHashMap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == insurancePay){
            if(mInsurancePayPresenterImp == null){
                mInsurancePayPresenterImp = new InsurancePayPresenterImp(this);
            }
            mInsurancePayPresenterImp.loadView(1);
        }
    }

    private void echo(String message, CallbackContext callbackContext){

        if(message != null && message.length() > 0){
            callbackContext.success(message);
        }else{
            callbackContext.error("ERROR");
        }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void updateView(Object object, int index) {
        CTTXInsurancePayBean mCTTXInsurancePayBean = (CTTXInsurancePayBean) object;
        switchJump(mCTTXInsurancePayBean.getRspInfo().getPymtste());
    }

    @Override
    public void hideProgress() {

    }

    public void switchJump(String code){
        int codeNumber = Integer.parseInt(code);
        String msg = "";
        switch (codeNumber){
            case 0:
                msg = "订单尚未支付，请继续！";
                break;
            case 1:
                echo("SUCCESS", callbackContext);
                break;
            case 2:
                msg = "该订单已被撤销！";
                break;
            case 3:
                msg = "该订单正在支付中，请稍后继续！";
                break;
            case 4:
                msg = "该订单为可以订单，请和投保公司联系确认！";
                break;
            case 5:
                msg = "该订单支付失败，请继续支付";
                break;
            case 6:
                msg = "该订单已被撤销";
                break;
            case 7:
                msg = "该订单正在退款中，请耐心等待";
                break;

        }

        if(!"".equals(msg)){
            ToastUtils.showShort(cordova.getActivity(), msg);
        }
    }

    @Override
    public Boolean shouldAllowRequest(String url) {
        return true;
    }

    @Override
    public Boolean shouldAllowBridgeAccess(String url) {
        return true;
    }

}
