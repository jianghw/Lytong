package com.zantong.mobile.model;

import android.util.Log;

import com.zantong.mobile.api.APPHttpClient;
import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.card.bean.BindCardBean;
import com.zantong.mobile.weizhang.bean.IllegalQueryBean;
import com.zantong.mobile.model.modelinterface.SimpleModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class BindCardModelImp implements SimpleModel {
    private Subscriber mSubscriber;
    @Override
    public void loadUpdate(final OnLoadServiceBackUI listener, String msg, final int index) {
        switch (index){
            case 1:
                mSubscriber = new Subscriber<BindCardBean>() {
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
                    public void onNext(BindCardBean mBindCardBean) {
                        Log.e("why", mBindCardBean+"aaa");
                        listener.onSuccess(mBindCardBean, index);
                    }

                };
                APPHttpClient.getInstance().getCTTXHttpPOSTInterface().loadBindCardSuccess(msg)
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
    }
}
