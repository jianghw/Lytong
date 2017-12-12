package com.tzly.ctcyh.user.pw_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.RequestDTO;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.request.VCodeDTO;
import com.tzly.ctcyh.user.bean.response.VCodeResponse;
import com.tzly.ctcyh.user.data_m.BaseSubscriber;
import com.tzly.ctcyh.user.data_m.UserDataManager;
import com.tzly.ctcyh.user.global.UserGlobal;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class SendCodePresenter implements ISendCodeContract.ISendCodePresenter {

    private final UserDataManager mRepository;
    private final ISendCodeContract.ISendCodeView mContractView;
    private final CompositeSubscription mSubscriptions;

    public SendCodePresenter(@NonNull UserDataManager userDataManager,
                             @NonNull ISendCodeContract.ISendCodeView view) {
        mRepository = userDataManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }


    @Override
    public void sendVerificationCode() {
        Subscription subscription = mRepository
                .bank_u015_01(vCodeDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                        mContractView.codeBtnEnable(false);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<VCodeResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                        mContractView.codeBtnEnable(true);
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.codeBtnEnable(true);

                        mContractView.verificationCodeError(e.getMessage());
                    }

                    @Override
                    public void doNext(VCodeResponse response) {
                        if (response != null && response.getSYS_HEAD().getReturnCode()
                                .equals(UserGlobal.Response.bank_succeed)) {

                            mContractView.verificationCodeSucceed(response);
                        } else {
                            mContractView.verificationCodeError(response != null
                                    ? response.getSYS_HEAD().getReturnMessage() : "未知错误(u015)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    private String vCodeDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.requestHeadDTO("cip.cfc.u015.01");
        dto.setSYS_HEAD(requestHeadDTO);

        VCodeDTO vCodeDTO = new VCodeDTO();
        vCodeDTO.setPhoenum(mContractView.getPhone());
        vCodeDTO.setSmsscene("001");

        dto.setReqInfo(vCodeDTO);
        return new Gson().toJson(dto);
    }

    /**
     * 倒计时
     */
    @Override
    public void startCountDown() {
        Subscription subCount = Observable
                .interval(10, 1000, TimeUnit.MILLISECONDS)
                .take(60)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mContractView.codeBtnEnable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mContractView.codeBtnEnable(true);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mContractView.countDownTextView(60 - aLong);
                    }
                });
        mSubscriptions.add(subCount);
    }

    @Override
    public void v_p002_01() {
        Subscription subscription = mRepository
                .bank_p002_01(sendCodeDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                        mContractView.codeBtnEnable(false);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BankResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.v_p002_01Error(e.getMessage());
                    }

                    @Override
                    public void doNext(BankResponse response) {
                        if (response != null && response.getSYS_HEAD().getReturnCode()
                                .equals(UserGlobal.Response.bank_succeed)) {
                            mContractView.v_p002_01Succeed(response);
                        } else {
                            mContractView.v_p002_01Error(response != null
                                    ? response.getSYS_HEAD().getReturnMessage() : "未知错误(p002)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    private String sendCodeDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.requestHeadDTO("cip.cfc.p002.01");
        dto.setSYS_HEAD(requestHeadDTO);

        VCodeDTO vCodeDTO = new VCodeDTO();
        vCodeDTO.setSmsscene("001");
        vCodeDTO.setPhoenum(mContractView.getPhone());
        vCodeDTO.setCaptcha(mContractView.getCode());
        vCodeDTO.setOnlyflag(mContractView.getFlag());

        dto.setReqInfo(vCodeDTO);
        return new Gson().toJson(dto);
    }
}
