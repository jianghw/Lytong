package com.zantong.mobilecttx.cordova.hwidget;

import android.content.Intent;

import com.zantong.mobilecttx.utils.AccountRememberCtrl;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

/**
 * Created by Administrator on 2016/5/24.
 */
public class HGBRegisterBackPlugin extends CordovaPlugin {
    private String temp = "{\"SYS_HEAD\":{\"ConsumerId\":\"04\",\"TransServiceCode\":\"cip.cfc.u001.01\",\"RequestDate\":\"20150504\",\"RequestTime\":\"173343\",\"ConsumerSeqNo\":\"0010230123010\"},\"RequestDTO\":{\"phoenum\":\"13521396353\",\"captcha\":\"123123\",\"onlyflag\":\"12334\",\"devicetoken\":\"2342342345s3\",\"pushswitch\":\"1\",\"pushmode\":\"1\"}}";

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
//        Log.e("why","222");
        if("backRegisterStatus".equals(action)){
            AccountRememberCtrl.saveChangTongFlag(cordova.getActivity(), "1");

            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void echo(String message, CallbackContext callbackContext){

        if(message != null && message.length() > 0){
            callbackContext.success(message);
        }else{
            callbackContext.error("ERROR");
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
