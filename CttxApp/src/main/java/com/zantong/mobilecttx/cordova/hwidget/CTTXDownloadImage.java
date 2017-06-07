package com.zantong.mobilecttx.cordova.hwidget;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;
import com.zantong.mobilecttx.presenter.InsurancePayPresenterImp;
import com.zantong.mobilecttx.utils.ImageTools;
import com.zantong.mobilecttx.utils.NetUtils;
import com.zantong.mobilecttx.interf.ModelView;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXDownloadImage extends CordovaPlugin implements ModelView{
    private String payUrl = "";
    private final int insurancePay = 1;
    private InsurancePayPresenterImp mInsurancePayPresenterImp;
    HashMap<String, String>  mHashMap = new HashMap<>();
    private CallbackContext callbackContext;
    Bitmap myBitmap;

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if("downloadImageWithUrl".equals(action)){
            String merCustomIp = NetUtils.getPhontIP(cordova.getActivity());
            this.callbackContext = callbackContext;
            payUrl = args.getString(0);
            payUrl += "&merCustomIp="+merCustomIp;

            try {
                myBitmap = Glide.with(cordova.getActivity())
                        .load(args.getString(0))
                        .asBitmap()
                        .centerCrop()
                        .into(500, 500)
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if(myBitmap != null){
                String bitmapBase64 = ImageTools.bitmapToBase64(myBitmap);
                echo(bitmapBase64, callbackContext);
            }else{
                echo("", callbackContext);
            }


//            Log.e("why", myBitmap.toString());
            return true;
        }
        return super.execute(action, args, callbackContext);
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
    }

    @Override
    public void hideProgress() {

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
