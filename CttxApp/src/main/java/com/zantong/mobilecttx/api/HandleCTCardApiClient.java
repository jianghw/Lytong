package com.zantong.mobilecttx.api;

import android.content.Context;

import com.google.gson.Gson;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.bean.Result;

/**
 * Created by zhoujie on 2017/1/3.
 */

public class HandleCTCardApiClient extends BaseApiClient{

    /**
     * @author zhoujie
     * create at 16/6/15 下午4:05
     */
    public static void htmlLocal(final Context context, String serviceCode, Object jsonParams, final ResultInterface resultInterface) {
        String reqinfo = new Gson().toJson(jsonParams);
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, new CallBack<Result>() {
            @Override
            public void onSuccess(Result result) {
                resultInterface.resultSuccess(result);
            }
            @Override
            public void onError(String errorCode, String msg) {
                resultInterface.resultError(msg);
            }
        }, Result.class);
        MessageFormat.getInstance().setTransServiceCode(serviceCode);
        try {
            MessageFormat.getInstance().setHtmlMessageJSONObject(reqinfo);
        } catch (Exception e) {
        }
        htmlpost(context, BuildConfig.BASE_URL, MessageFormat.getInstance().getMessageFormat(), asyncCallBack);
    }

    public interface ResultInterface{
        void resultSuccess(Result result);
        void resultError(String mesage);
    }
}
