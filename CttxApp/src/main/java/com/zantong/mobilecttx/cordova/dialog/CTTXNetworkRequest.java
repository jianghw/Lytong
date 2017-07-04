package com.zantong.mobilecttx.cordova.dialog;

import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import cn.qqtheme.framework.util.ToastUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXNetworkRequest extends CordovaPlugin {
    private Dialog mDialog;

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        Log.i("tag", "1111");
        if ("networkRequest".equals(action)) {
            mDialog = DialogUtils.showLoading(cordova.getActivity());
            UserApiClient.html(cordova.getActivity(), args.getString(0),
                    mapToStr(args.getString(0), args.getString(1)), new CallBack<Result>() {
                        @Override
                        public void onSuccess(Result result) {
                            if (mDialog != null) {
                                mDialog.dismiss();
                                mDialog = null;
                            }
                            if (PublicData.getInstance().success.equals(result.getSYS_HEAD().getReturnCode()) || "不可以快捷办卡".equals(result.getSYS_HEAD().getReturnMessage())) {
                            } else {
                                ToastUtils.showShort(cordova.getActivity(), result.getSYS_HEAD().getReturnMessage());
                            }

                            try {
                                CTTXNetworkRequest.this.echo(new JSONObject(PublicData.getInstance().mHashMap.get("htmlResponse").toString()), callbackContext);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(String errorCode, String msg) {
                            if (mDialog != null) {
                                mDialog.dismiss();
                                mDialog = null;
                            }
                            Toast.makeText(cordova.getActivity(), Config.getErrMsg("1"), Toast.LENGTH_SHORT).show();
//                    ToastUtils.showShort(cordova.getActivity(), );
                            CTTXNetworkRequest.this.echo("", callbackContext);
                        }
                    });
            Log.e("why", args.getString(0));


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

    private void echo(JSONObject message, CallbackContext callbackContext) {

        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("ERROR");
        }

    }

    private void echo(String message, CallbackContext callbackContext) {

        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("ERROR");
        }

    }

    private String transMapToString(Map map) {
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    private Map transStringToMap(String mapString) {
        Map outMap = new HashMap();
        try {
            JSONObject jsonObj = new JSONObject(mapString);
            Iterator<String> nameItr = jsonObj.keys();
            String name;
            while (nameItr.hasNext()) {
                name = nameItr.next();
                outMap.put(name, jsonObj.get(name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return outMap;
    }

    private String mapToStr(String strCode, String mapString) {
        String mapToString = mapString;
        if (strCode.contains("u006") || strCode.contains("u007") || strCode.contains("u010")
                || strCode.contains("i003") || strCode.contains("i005")) {
            try {
                Map map = transStringToMap(mapString);
                java.util.Map.Entry entry;
                for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
                    entry = (java.util.Map.Entry) iterator.next();
                    if (entry.getKey().equals("ctfnum")) {
                        map.put(entry.getKey(), RSAUtils.strByEncryption(entry.getValue().toString(), true));
                    }
                    if (entry.getKey().equals("filenum")) {
                        map.put(entry.getKey(), RSAUtils.strByEncryption(entry.getValue().toString(), true));
                    }
                    if (entry.getKey().equals("phoenum")) {
                        map.put(entry.getKey(), RSAUtils.strByEncryption(entry.getValue().toString(), true));
                    }
                    if (entry.getKey().equals("appsctfnum")) {
                        map.put(entry.getKey(), RSAUtils.strByEncryption(entry.getValue().toString(), true));
                    }
                    if (entry.getKey().equals("appsmblphoe")) {
                        map.put(entry.getKey(), RSAUtils.strByEncryption(entry.getValue().toString(), true));
                    }
                    if (entry.getKey().equals("insdctfnum")) {
                        map.put(entry.getKey(), RSAUtils.strByEncryption(entry.getValue().toString(), true));
                    }
                    if (entry.getKey().equals("insdmblphoe")) {
                        map.put(entry.getKey(), RSAUtils.strByEncryption(entry.getValue().toString(), true));
                    }
                }
                mapToString = transMapToString(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapToString;
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
