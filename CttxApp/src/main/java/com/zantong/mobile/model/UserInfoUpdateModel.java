package com.zantong.mobile.model;

import android.util.Log;

import com.zantong.mobile.api.APPHttpClient;
import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.weizhang.bean.IllegalQueryBean;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.model.modelinterface.SimpleModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：王海洋
 * 时间：2016/6/20 10:42
 */
public class UserInfoUpdateModel implements SimpleModel {
    private Subscriber mSubscriber;
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {
        switch (index){
            case 1:
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
                    public void onNext(UpdateInfo mCanPayCar) {
                        Log.e("why", mCanPayCar+"aaa");
                        listener.onSuccess(mCanPayCar, index);
                    }

                };
                APPHttpClient.getInstance().getCTTXHttpPOSTFileInterface().loadUpdateInfo(msg)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            case 2:
                mSubscriber = new Subscriber<IllegalQueryBean>() {
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
                    public void onNext(IllegalQueryBean mIllegalQueryBean) {
//                        Log.e("why", mOpenQueryBean+"aaa");
                        listener.onSuccess(mIllegalQueryBean, index);
                    }

                };
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadOpenIllegalQueryBean(msg)
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
