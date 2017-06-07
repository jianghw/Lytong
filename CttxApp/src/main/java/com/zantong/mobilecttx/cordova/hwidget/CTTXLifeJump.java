package com.zantong.mobilecttx.cordova.hwidget;

import android.content.Intent;
import android.util.Log;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.LifeJumpMain;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXLifeJump extends CordovaPlugin {
    private String temp = "{\"SYS_HEAD\":{\"ConsumerId\":\"04\",\"TransServiceCode\":\"cip.cfc.u001.01\",\"RequestDate\":\"20150504\",\"RequestTime\":\"173343\",\"ConsumerSeqNo\":\"0010230123010\"},\"RequestDTO\":{\"phoenum\":\"13521396353\",\"captcha\":\"123123\",\"onlyflag\":\"12334\",\"devicetoken\":\"2342342345s3\",\"pushswitch\":\"1\",\"pushmode\":\"1\"}}";
    private Timer timer = new Timer();
    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        Log.e("why",action);
        if("lifeJump".equals(action)){
            PublicData.getInstance().TitleFlag = true;
            EventBus.getDefault().post(new LifeJumpMain(true));
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
        }else if("backlife".equals(action)){
            PublicData.getInstance().TitleFlag = false;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new LifeJumpMain(false));
                }
            };

            timer.schedule(task, 200);
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
