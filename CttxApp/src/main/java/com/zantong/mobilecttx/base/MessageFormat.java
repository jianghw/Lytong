package com.zantong.mobilecttx.base;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/16.
 */
public class MessageFormat {
    private  String msg = "";
    private  String mTransServiceCode = "";


    private static MessageFormat instance;
    private MessageFormat (){}
    private JSONObject jsonObject = new JSONObject();
    public static synchronized MessageFormat getInstance() {
        if (instance == null) {
            instance = new MessageFormat();
        }
        return instance;
    }

    public String getMessageFormat(){
//        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SYS_HEAD",getHeadMSG());
        } catch (Exception e){

        }
//        oMap.put("SYS_HEAD",map);
//        oMap.put("RequestDTO",masp);
//        Log.e("why",jsonObject.toString());
        msg = jsonObject.toString();

//        String temp = JsonArray.fromObject(oMap);
        return msg;
    }
    public String getSimpleMessageFormat(){
        msg = jsonObject.toString();

//        String temp = JsonArray.fromObject(oMap);
        return msg;
    }

    public void setMessageJSONObject(JSONObject map) throws JSONException {
        jsonObject.put("ReqInfo", map);
    }
    public void setSimpleJSONObject(JSONObject map) throws JSONException {
        jsonObject.put("img", map);
    }
    public void setHtmlMessageJSONObject(String str) throws JSONException {
        JSONObject map = new JSONObject(str);
        jsonObject.put("ReqInfo", map);
    }
    public JSONObject getHeadMSG() throws JSONException {
        JSONObject map = new JSONObject();
        map.put("ConsumerId","04");
        map.put("TransServiceCode",mTransServiceCode);
        map.put("RequestDate", Tools.getYearDate());
        map.put("RequestTime",Tools.getTimeDate());
        map.put("DvcToken", PublicData.getInstance().imei);
        map.put("ConsumerSeqNo", PublicData.getInstance().imei+Tools.getYearDate()+Tools.getTimeDateS());
        return map;
    }

    public void setTransServiceCode(String mTransServiceCode){
        this.mTransServiceCode = mTransServiceCode;
    }

}
