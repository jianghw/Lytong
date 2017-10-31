package com.zantong.mobilecttx.model;

import android.util.Log;

import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.api.APPHttpClient;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.model.modelinterface.SimpleModel;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class InsurancePayModelImp implements SimpleModel {
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {

        Subscriber mSubscriber = new Subscriber<CTTXInsurancePayBean>() {
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
            public void onNext(CTTXInsurancePayBean mCTTXInsurancePayBean) {
//                Log.e("why", mLoginInfoBean+"aaa");
                listener.onSuccess(mCTTXInsurancePayBean, index);
            }

        };
        LogUtils.i("AddVehicleModelImp - msg : "+msg);
        APPHttpClient.getInstance().getCTTXHttpPOSTInterface().insurancePay(msg)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
//        APPHttpClient.getInstance().sendTrad(msg, mSubscriber, CTTXHttpPOSTInterface.class);
//        APPHttpClient.getCTTXHttpPOSTInterface().loadPost(msg)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
    }
}
