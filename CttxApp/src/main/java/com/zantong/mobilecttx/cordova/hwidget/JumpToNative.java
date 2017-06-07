package com.zantong.mobilecttx.cordova.hwidget;

import android.content.Intent;
import android.util.Log;

import com.zantong.mobilecttx.utils.jumptools.Act;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

/**
 * Created by Administrator on 2016/5/24.
 */
public class JumpToNative extends CordovaPlugin {
    private String temp = "{\"SYS_HEAD\":{\"ConsumerId\":\"04\",\"TransServiceCode\":\"cip.cfc.u001.01\",\"RequestDate\":\"20150504\",\"RequestTime\":\"173343\",\"ConsumerSeqNo\":\"0010230123010\"},\"RequestDTO\":{\"phoenum\":\"13521396353\",\"captcha\":\"123123\",\"onlyflag\":\"12334\",\"devicetoken\":\"2342342345s3\",\"pushswitch\":\"1\",\"pushmode\":\"1\"}}";

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        Log.e("why","222");
        if("JumpToNative".equals(action)){
            Class clazz = null;
            try {
                clazz = Class.forName("com.zantong.mobilecttx.view.activity."+args.getString(0));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Act.getInstance().lauchIntentToLogin(cordova.getActivity(), clazz);
            cordova.getActivity().finish();
//            this.echo(temp, callbackContext);

//            Log.e("why",args.getString(0)+"qweqweqwe");
//            AlertDialog.Builder builder = new AlertDialog.Builder(cordova.getActivity());
//            builder.setTitle("提示");
//            builder.setMessage(args.getString(0));
//            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    callbackContext.success("点击了确定");
//                }
//            });
//            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    callbackContext.error("点击了取消");
//                }
//            });
//            builder.create().show();

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
