package com.zantong.mobilecttx.api;

import android.content.Context;

import com.google.gson.Gson;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.base.MessageFormat;

import com.tzly.ctcyh.java.response.BankResponse;

/**
 * Created by zhoujie on 2017/1/3.
 */

public class HandleCTCardApiClient extends BaseApiClient {

    /**
     * @author zhoujie
     * create at 16/6/15 下午4:05
     */
    public static void htmlLocal(final Context context, String serviceCode, Object jsonParams,
                                 final ResultInterface resultInterface) {

        String reqinfo = new Gson().toJson(jsonParams);

        AsyncCallBack<BankResponse> asyncCallBack =
                new AsyncCallBack<>(context, new CallBack<BankResponse>() {
                    @Override
                    public void onSuccess(BankResponse result) {
                        resultInterface.resultSuccess(result);
                    }

                    @Override
                    public void onError(String errorCode, String msg) {
                        resultInterface.resultError(msg);
                    }
                }, BankResponse.class);

        try {
            MessageFormat.getInstance().setTransServiceCode(serviceCode);
            MessageFormat.getInstance().setHtmlMessageJSONObject(reqinfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        htmlpost(context, BuildConfig.bank_mc_url, MessageFormat.getInstance().getMessageFormat(), asyncCallBack);
    }

    public interface ResultInterface {
        void resultSuccess(BankResponse bankResponse);

        void resultError(String msg);
    }
}
