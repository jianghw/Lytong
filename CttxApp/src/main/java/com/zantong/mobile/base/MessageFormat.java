package com.zantong.mobile.base;

import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/16.
 */
public class MessageFormat {
    private String msg = "";
    private String mTransServiceCode = "";
    private static MessageFormat instance;

    private MessageFormat() {
    }

    private JSONObject jsonObject = new JSONObject();

    public static synchronized MessageFormat getInstance() {
        if (instance == null) {
            instance = new MessageFormat();
        }
        return instance;
    }

    public String getMessageFormat() {
        try {
            jsonObject.put("SYS_HEAD", getHeadMSG());
        } catch (Exception e) {

        }
        msg = jsonObject.toString();

        return msg;
    }

    public String getSimpleMessageFormat() {
        msg = jsonObject.toString();

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
        map.put("ConsumerId", "04");
        map.put("TransServiceCode", mTransServiceCode);
        map.put("RequestDate", Tools.getYearDate());
        map.put("RequestTime", Tools.getTimeDate());
        map.put("DvcToken", MemoryData.getInstance().imei);
        map.put("ConsumerSeqNo", MemoryData.getInstance().imei + Tools.getYearDate() + Tools.getTimeDateS());
        return map;
    }

    public void setTransServiceCode(String mTransServiceCode) {
        this.mTransServiceCode = mTransServiceCode;
    }

}
