package com.zantong.mobilecttx.model;

import android.util.Log;

import com.zantong.mobilecttx.api.APPHttpClient;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.weizhang.bean.IllegalQueryBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;
import com.zantong.mobilecttx.model.modelinterface.SimpleModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ViolationDetailsModelImp implements SimpleModel {
    private Subscriber mSubscriber;
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {
        switch (index){
            case 1:
                mSubscriber = new Subscriber<ViolationDetailsBean>() {
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
                    public void onNext(ViolationDetailsBean mViolationDetailsBean) {
                        Log.e("why", mViolationDetailsBean+"aaa");
                        listener.onSuccess(mViolationDetailsBean, index);
                    }

                };
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadViolationDetails(msg)
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
