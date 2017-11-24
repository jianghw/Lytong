package com.tzly.ctcyh.cargo.data_m;


import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IRemoteSource {
    /**
     * 加油
     */
    Observable<RefuelOilResponse> getGoods();

    /**
     * 创建订单
     */
    Observable<RefuelOrderResponse> createOrder(RefuelOilDTO oilDTO);
}
