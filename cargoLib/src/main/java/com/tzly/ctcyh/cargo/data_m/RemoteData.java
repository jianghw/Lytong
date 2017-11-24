package com.tzly.ctcyh.cargo.data_m;

import android.support.annotation.Nullable;

import com.tzly.ctcyh.cargo.api.IAddOilService;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 远程数据处理
 */

public class RemoteData implements IRemoteSource {

    @Nullable
    private static RemoteData INSTANCE = null;

    /**
     * 懒汉式，线程不安全
     */
    public static RemoteData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteData();
        }
        return INSTANCE;
    }

    private Retrofit baseRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(1);
    }

    private Retrofit bankRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(2);
    }

    private Retrofit initBaseUrlRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(3);
    }

    private Retrofit initTestRetrofit(int type) {
        return RetrofitFactory.getInstance().createRetrofit(type);
    }

    private Retrofit initImageRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(5);
    }

    /**
     * 加油
     */
    @Override
    public Observable<RefuelOilResponse> getGoods() {
        return baseRetrofit().create(IAddOilService.class).getGoods();
    }

    /**
     * 创建订单
     */
    @Override
    public Observable<RefuelOrderResponse> createOrder(RefuelOilDTO oilDTO) {
        return baseRetrofit().create(IAddOilService.class).createOrder(oilDTO);
    }
}
