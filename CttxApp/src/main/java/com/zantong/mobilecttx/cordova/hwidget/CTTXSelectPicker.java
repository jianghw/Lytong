package com.zantong.mobilecttx.cordova.hwidget;

import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Window;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.card.bean.ProvinceModel;
import com.zantong.mobilecttx.utils.dialog.CityDialog;
import com.zantong.mobilecttx.utils.xmlparser.XmlParserHandler;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXSelectPicker extends CordovaPlugin {
//    private String temp = "{\"SYS_HEAD\":{\"ConsumerId\":\"04\",\"TransServiceCode\":\"cip.cfc.u001.01\",\"RequestDate\":\"20150504\",\"RequestTime\":\"173343\",\"ConsumerSeqNo\":\"0010230123010\"},\"RequestDTO\":{\"phoenum\":\"13521396353\",\"captcha\":\"123123\",\"onlyflag\":\"12334\",\"devicetoken\":\"2342342345s3\",\"pushswitch\":\"1\",\"pushmode\":\"1\"}}";

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if("selectPicker".equals(action)){

            List<ProvinceModel> provinceList = null;
            AssetManager asset = cordova.getActivity().getAssets();
            try {
                InputStream input = asset.open("province_data.xml");
                // 创建一个解析xml的工厂对象
                SAXParserFactory spf = SAXParserFactory.newInstance();
                // 解析xml
                SAXParser parser = spf.newSAXParser();
                XmlParserHandler handler = new XmlParserHandler();
                parser.parse(input, handler);
                input.close();
                // 获取解析出来的数据
                provinceList = handler.getDataList();
                PublicData.getInstance().provinceModel = provinceList;
            }catch (Throwable e){
                e.printStackTrace();
            }

            CityDialog dialog = new CityDialog(cordova.getActivity(),null,new CityDialog.OnChooseDialogListener() {

                @Override
                public void back(String[] data) {
//                card_get_time.setText(name);
//                        UpdateFlag=true;
                    JSONArray array = new JSONArray();
                    array.put(data[0]);
                    array.put(data[1]);
                    array.put(data[2]);
                    CTTXSelectPicker.this.echo(array, callbackContext);
                    Log.e("why", data[0]+data[1]+data[2]);

                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();


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
