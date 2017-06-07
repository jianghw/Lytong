package com.zantong.mobilecttx.api;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/4/19.
 */
public class OkHttpUtill {
    private static OkHttpUtill mInstance;
    private OkHttpClient mOkHttpClient;
    private Context mContext;
    private ActBackToUI mActBackToUI;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mActBackToUI.successDoInBack(msg.obj);
//                    Toast.makeText(mContext, "请求成功！！", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    mActBackToUI.failDoInBack(msg.obj);
            }
            super.handleMessage(msg);
        }
    };

    public OkHttpUtill(){
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpUtill getInstance(){
        if(mInstance == null){
            synchronized (OkHttpUtill.class)
            {
                if(mInstance == null){
                    mInstance = new OkHttpUtill();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient(){
        return getInstance().mOkHttpClient;
    }


    public void sengGETOkHttpClient(final Context mContext, ActBackToUI mActBackToUI, String url ){
        this.mActBackToUI = mActBackToUI;
        this.mContext = mContext;
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpUtill.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                message.obj = e;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()) throw new IOException("Unexpected code"+ response);
//                Toast.makeText(context, "请求成功！！！！", Toast.LENGTH_SHORT).show();
                Log.e("why","请求成功！！！");
//                mContext.getApplicationContext().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        text.setText("请求成功");
//
//                    }
//                });
                Message message = new Message();
                message.what=1;
//                Log.e("why",response.body().string());
                String temp = response.body().string();
                message.obj = temp;
                mHandler.sendMessage(message);
            }
        });
    }

//    public interface ActBackToUI{
//        public void doInBack();
//    }


}
