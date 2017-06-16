package com.zantong.mobilecttx.api;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.eventbus.ErrorEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import cn.qqtheme.framework.util.log.LogUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class VersionCallBack<T> implements Callback {
    private Gson gson;


    private Context context;
    private Object tag;
    private Class<T> clazz;
    private CallBack<T> callback;

    private HashMap<String,String> errorList;

    public VersionCallBack() {
    }



    public VersionCallBack(Context context, Object tag, CallBack<T> callback, Class<T> clazz) {
        this.callback = callback;
        this.context = context;
        this.tag = tag;
        this.clazz = clazz;
        this.gson = new Gson();
        this.errorList = new HashMap<String,String>();
    }

    public VersionCallBack(Context context, CallBack<T> callback, Class<T> clazz) {
        this(context, context, callback, clazz);
    }

    @Override
    public void onResponse(Call call,Response response) throws IOException {

        if (response.isSuccessful()) {
            try {
                String reader = response.body().string();
                LogUtils.i("reader===" + reader);
                if (!TextUtils.isEmpty(reader)) {
                    T t = gson.fromJson(reader, clazz);
                    callback.sendSuccessMessage(t);
                } else {
                    EventBus.getDefault().post(
                            new ErrorEvent(Config.ERROR_PARSER,
                                    Config.ERROR_PARSER_MSG, tag, context));
                    callback.sendFailMessage(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG);
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                EventBus.getDefault().post(
                        new ErrorEvent(Config.ERROR_PARSER,
                                Config.ERROR_PARSER_MSG, tag, context));
                callback.sendFailMessage(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG);
            } catch (JsonIOException e) {
                e.printStackTrace();
                EventBus.getDefault().post(
                        new ErrorEvent(Config.ERROR_PARSER,
                                Config.ERROR_PARSER_MSG, tag, context));
                callback.sendFailMessage(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG);
            }
        } else {
            LogUtils.i("response.code:"+response.code());
            EventBus.getDefault().post(
                    new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, tag,
                            context));
            callback.sendFailMessage(Config.ERROR_IO, Config.ERROR_IO_MSG);
        }
    }


    @Override
    public void onFailure(Call call, IOException ex) {
        String msg = ex.getMessage();
        LogUtils.i("failed msg:" + msg);
        if("Canceled".equals(msg)){
            LogUtils.i("msg:" + msg);
            EventBus.getDefault()
                    .post(new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, tag,
                            context));
            callback.sendFailMessage(Config.ERROR_IO, Config.ERROR_IO_MSG);
        }else{
            EventBus.getDefault()
                    .post(new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, tag,
                            context));
            callback.sendFailMessage(Config.ERROR_IO, Config.ERROR_IO_MSG);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public CallBack<T> getCallback() {
        return callback;
    }

    public void setCallback(CallBack<T> callback) {
        this.callback = callback;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }


}
