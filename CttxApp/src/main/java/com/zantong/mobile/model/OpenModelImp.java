package com.zantong.mobile.model;

import android.util.Log;

import com.zantong.mobile.api.APPHttpClient;
import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.card.bean.OpenQueryBean;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.model.modelinterface.SimpleModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class OpenModelImp implements SimpleModel {
    private Subscriber mSubscriber;
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {
        switch (index){
            case 1:
                mSubscriber = new Subscriber<OpenQueryBean>() {
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
                    public void onNext(OpenQueryBean mOpenQueryBean) {
                        Log.e("why", mOpenQueryBean+"aaa");
                        listener.onSuccess(mOpenQueryBean, index);
                    }

                };
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadOpenQueryBean(msg)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            case 2:
                mSubscriber = new Subscriber<UpdateInfo>() {
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
                    public void onNext(UpdateInfo mUpdateInfo) {
//                        Log.e("why", mOpenQueryBean+"aaa");
                        listener.onSuccess(mUpdateInfo, index);
                    }

                };
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadUpdateInfo(msg)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;

        }

//        APPHttpClient.getInstance().sendTrad(msg, mSubscriber, CTTXHttpPOSTInterface.class);
//        APPHttpClient.getCTTXHttpPOSTInterface().loadPost(msg)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
    }
}
