package com.tzly.ctcyh.pay.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 仓库管理类
 */

public class RepositoryManager {
    @Nullable
    private static RepositoryManager INSTANCE = null;
    @NonNull
    private final RemoteData mRemoteData;
    @NonNull
    private final LocalData mLocalData;

    /**
     * 懒汉式，线程不安全
     */
    public static RepositoryManager getInstance(RemoteData remoteData, LocalData localData) {
        if (INSTANCE == null) {
            INSTANCE = new RepositoryManager(remoteData, localData);
        }
        return INSTANCE;
    }

    private RepositoryManager(@NonNull RemoteData remoteData, @NonNull LocalData localData) {
        mRemoteData = remoteData;
        mLocalData = localData;
    }

    /**
     * 57.获取指定类型优惠券
     */
    public Observable<CouponResponse> getCouponByType(String userId, String extraType) {
        return mRemoteData.getCouponByType(userId,extraType);
    }
}
