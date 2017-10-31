package com.zantong.mobilecttx.model;

import android.util.Log;

import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.api.APPHttpClient;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.model.modelinterface.SimpleModel;
import com.zantong.mobilecttx.weizhang.bean.AddVehicleBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class AddVehicleModelImp implements SimpleModel {
    private Subscriber mSubscriber;
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {

        switch (index){
            case 1:
                mSubscriber = new Subscriber<AddVehicleBean>() {
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
                    public void onNext(AddVehicleBean mAddVehicleBean) {
//                Log.e("why", mLoginInfoBean+"aaa");
                        listener.onSuccess(mAddVehicleBean, index);
                    }

                };
                LogUtils.i("AddVehicleModelImp - msg : "+msg);
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadAddVehiclePost(msg)
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
