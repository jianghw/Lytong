package com.zantong.mobilecttx.cordova.getuser;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.eventbus.GetUserEvent;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.user.activity.LoginActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXGetUserMessage extends CordovaPlugin {

    private  final int resultCode = 1;
    private CallbackContext mcallbackContext;
    public CTTXGetUserMessage(){
        EventBus.getDefault().register(this);
    }
    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        Log.e("why",action);
        mcallbackContext = callbackContext;
        if("getUserMessage".equals(action)){
            if(PublicData.getInstance().loginFlag){
                sendData(callbackContext);
            }else{
                this.function();
                PluginResult mPlug = new PluginResult(PluginResult.Status.NO_RESULT);
                mPlug.setKeepCallback(true);
                callbackContext.sendPluginResult(mPlug);
//                callbackContext.success("success");
//                Intent intent = new Intent(cordova.getActivity(), LoginPhone.class);
////                cordova.setActivityResultCallback(CTTXGetUserMessage.this);
//                cordova.startActivityForResult((CordovaPlugin) this, intent, 1);

//                CTTXGetUserMessage.this.errorEcho("ERROR", callbackContext);
            }

            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginForMain (GetUserEvent event) {
//        mOpenPresenterImp.loadView(1);
        sendData(mcallbackContext);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void sendData(CallbackContext callbackContext){
        try {
            LoginInfoBean.RspInfoBean user = (LoginInfoBean.RspInfoBean) UserInfoRememberCtrl.readObject();
            if(user == null){
//                CTTXGetUserMessage.this.errorEcho("ERROR", callbackContext);
                callbackContext.error("ERROR");
            }else{
                Gson gson = new Gson();
                String dataStr = gson.toJson(user);
                JSONObject data = new JSONObject(dataStr);
                CTTXGetUserMessage.this.echo(data, callbackContext);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void function(){
        Intent intent = new Intent(cordova.getActivity(), LoginActivity.class);
        cordova.startActivityForResult((CordovaPlugin) this, intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode){
            case 1:
                sendData(mcallbackContext);
                break;
        }

    }

    private void echo(JSONObject message, CallbackContext callbackContext){

        if(message != null && message.length() > 0){
            callbackContext.success(message);
        }else{
            callbackContext.error("Expected one non-empty string argument.");
        }

    }
    private void errorEcho(String message, CallbackContext callbackContext){

        if(message != null && message.length() > 0){
            callbackContext.success(message);
        }else{
            callbackContext.error("Expected one non-empty string argument.");
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
