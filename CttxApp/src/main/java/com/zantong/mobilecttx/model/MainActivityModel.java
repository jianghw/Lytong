package com.zantong.mobilecttx.model;

import android.util.Log;

import com.zantong.mobilecttx.api.APPHttpClient;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.home.bean.VersionBean;
import com.zantong.mobilecttx.model.modelinterface.SimpleModel;

import cn.qqtheme.framework.util.log.LogUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class MainActivityModel implements SimpleModel {
    private Subscriber mSubscriber;
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {

        switch (index){
            case 1:
                mSubscriber = new Subscriber<VersionBean>() {
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
                    public void onNext(VersionBean mVersionBean) {
                        Log.e("why", mVersionBean+"version");
                        listener.onSuccess(mVersionBean, index);
                    }

                };
                LogUtils.i("AddVehicleModelImp - msg : "+msg);
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadVersion(msg)
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
