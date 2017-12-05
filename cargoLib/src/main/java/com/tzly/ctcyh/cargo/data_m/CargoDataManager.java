package com.tzly.ctcyh.cargo.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.ctcyh.cargo.bean.BaseResponse;
import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.router.util.rea.RSAUtils;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 仓库管理类
 */

public class CargoDataManager {
    @Nullable
    private static CargoDataManager INSTANCE = null;
    @NonNull
    private final RemoteData mRemoteData;
    @NonNull
    private final LocalData mLocalData;

    /**
     * 懒汉式，线程不安全
     */
    public static CargoDataManager getInstance(RemoteData remoteData, LocalData localData) {
        if (INSTANCE == null) {
            INSTANCE = new CargoDataManager(remoteData, localData);
        }
        return INSTANCE;
    }

    private CargoDataManager(@NonNull RemoteData remoteData, @NonNull LocalData localData) {
        mRemoteData = remoteData;
        mLocalData = localData;
    }

    public String getRASUserID() {
        return RSAUtils.strByEncryption(getUserID(), true);
    }

    /**
     * 加密
     */
    public String getRAStr(String msg) {
        return RSAUtils.strByEncryption(msg, true);
    }

    /**
     * 用户id
     */
    public String getUserID() {
        return mLocalData.getUserID();
    }

    /**
     * 加油
     */
    public Observable<RefuelOilResponse> getGoods() {
        return mRemoteData.getGoods();
    }

    /**
     * 创建订单
     */
    public Observable<RefuelOrderResponse> createOrder(RefuelOilDTO oilDTO) {
        return mRemoteData.createOrder(oilDTO);
    }

    /**
     * 驾驶证
     */
    public Observable<BaseResponse> bindingDriving(BindDrivingDTO drivingDTO) {
        return mRemoteData.bindingDriving(drivingDTO);
    }

    /**
     * 行驶证
     */
    public Observable<BaseResponse> addVehicleLicense(BindCarDTO carDTO) {
        return mRemoteData.addVehicleLicense(carDTO);
    }
}
