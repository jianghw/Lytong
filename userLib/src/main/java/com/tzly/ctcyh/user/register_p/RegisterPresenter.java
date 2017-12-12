package com.tzly.ctcyh.user.register_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.util.SHATools;
import com.tzly.ctcyh.user.bean.RequestDTO;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.request.LoginDTO;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.data_m.BaseSubscriber;
import com.tzly.ctcyh.user.data_m.UserDataManager;
import com.tzly.ctcyh.user.global.UserGlobal;

import java.security.NoSuchAlgorithmException;

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

public class RegisterPresenter implements IRegisterContract.IRegisterPresenter {

    private final UserDataManager mRepository;
    private final IRegisterContract.IRegisterView mContractView;
    private final CompositeSubscription mSubscriptions;

    public RegisterPresenter(@NonNull UserDataManager userDataManager,
                             @NonNull IRegisterContract.IRegisterView view) {
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

    /**
     * 注册
     */
    @Override
    public void v_u001_01() {
        Subscription subscription = mRepository
                .bank_u001_01(registerDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LoginResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.v_u001_01Error(e.getMessage());
                    }

                    @Override
                    public void doNext(LoginResponse response) {
                        if (response != null && response.getSYS_HEAD().getReturnCode()
                                .equals(UserGlobal.Response.bank_succeed)) {
                            mContractView.v_u001_01Succeed(response);
                        } else {
                            mContractView.v_u001_01Error(response != null
                                    ? response.getSYS_HEAD().getReturnMessage() : "未知错误(u001)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 设备保存device id
     */
    @Override
    public void initPhoneDeviceId() {
        mRepository.savePhoneDeviceId();
    }

    private String registerDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.requestHeadDTO("cip.cfc.u001.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LoginDTO bean = new LoginDTO();
        String token = mRepository.getRASByStr(mRepository.getPushId());
        bean.setToken(token);

        bean.setPushmode("2");
        bean.setPushswitch("0");

        String phone = mRepository.getRASByStr(mContractView.getUserPhone());
        bean.setPhoenum(phone);

        SHATools shaTools = new SHATools();
        try {
            String pwd = mRepository.getRASByStr(
                    SHATools.hexString(shaTools.eccryptSHA1(mContractView.getUserPassword())));
            bean.setPswd(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bean.setDevicetoken(mRepository.getPhoneDeviceId());
        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }
}
