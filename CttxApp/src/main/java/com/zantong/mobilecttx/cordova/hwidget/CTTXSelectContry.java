package com.zantong.mobilecttx.cordova.hwidget;

import android.content.Intent;
import android.view.Window;

import com.zantong.mobilecttx.utils.dialog.NetLocationDialog;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import cn.qqtheme.framework.util.log.LogUtils;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXSelectContry extends CordovaPlugin {
    private final int insurancePay = 1;
    private String netLocation;
    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if("selectContry".equals(action)){
//            Log.e("why","getUser");
            LogUtils.i("选择领卡网点");
//            netLocation = args.getString(0);
//            PublicData.getInstance().mHashMap.put("NetLocationActivity", netLocation);
//            Act.getInstance().lauchIntentForResult(cordova.getActivity(), NetLocationActivity.class, insurancePay);

//            this.echo(temp, callbackContext);

            NetLocationDialog dialog = new NetLocationDialog(cordova.getActivity(),null,new NetLocationDialog.OnChooseDialogListener() {

                @Override
                public void back(String[] data) {
//                card_get_time.setText(name);
//                        UpdateFlag=true;
                    JSONArray array = new JSONArray();
                    array.put(data[0]);
                    array.put(data[1]);
                    array.put(data[2]);
                    array.put(data[3]);
                    array.put(data[4]);
//                    array.put("虹口区");
//                    array.put("02265");
//                    array.put("江湾支行");
//                    array.put("逸仙路1038号");
//                    array.put("65427566");
                    CTTXSelectContry.this.echo(array, callbackContext);
                    LogUtils.i(data[0]+data[1]+data[2]);

                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();


            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void echo(JSONArray message, CallbackContext callbackContext){

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
