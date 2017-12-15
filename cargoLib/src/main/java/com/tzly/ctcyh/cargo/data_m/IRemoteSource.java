package com.tzly.ctcyh.cargo.data_m;


import com.tzly.ctcyh.cargo.bean.BaseResponse;
import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
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

    /**
     * 驾驶证
     */
    Observable<BaseResponse> bindingDriving(BindDrivingDTO drivingDTO);

    /**
     * 行驶证
     */
    Observable<BaseResponse> addVehicleLicense(BindCarDTO carDTO);

    /**
     * 领券
     */
    Observable<ReceiveCouponResponse> receiveCoupon(String rasUserID, String couponId, String channel);
}
