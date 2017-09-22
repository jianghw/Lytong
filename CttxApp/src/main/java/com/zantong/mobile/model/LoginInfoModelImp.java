package com.zantong.mobile.model;

import android.util.Log;

import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobile.api.APPHttpClient;
import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.model.modelinterface.SimpleModel;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.user.bean.SmsBean;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class LoginInfoModelImp implements SimpleModel {
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {
        switch (index){
            case 1:
                Subscriber mSubscriber = new Subscriber<SmsBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("why","到这儿了！！！！");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error",e.toString());
                        listener.onFailed();
                    }

                    @Override
                    public void onNext(SmsBean mSmsBean) {
                        listener.onSuccess(mSmsBean, index);
                    }

                };
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadSmsPost(msg)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            case 2:
                Subscriber mSubscriber1 = new Subscriber<LoginInfoBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("why","到这儿了！！！！");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error",e.toString());
                        listener.onFailed();
                    }

                    @Override
                    public void onNext(LoginInfoBean mLoginInfoBean) {
                        Log.e("why", mLoginInfoBean+"aaa");
                        listener.onSuccess(mLoginInfoBean, index);
                    }

                };
                LogUtils.i("LModelImp msg : "+msg);
                Subscription subscribe = APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadPost(msg)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber1);
                break;
        }

    }
}
