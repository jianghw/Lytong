package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.weizhang.bean.ViolationNum;

import java.util.List;

import cn.qqtheme.framework.contract.bean.BaseResult;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IPayService {

    /**
     * 5.获取工行支付页面
     */
    @FormUrlEncoded
    @POST("pay/getBankPayHtml")
    Observable<PayOrderResult> getBankPayHtml(@Field("orderId") String orderId, @Field("amount") String orderPrice);

    /**
     * 46.更新违章缴费状态
     * @param json
     */
    @POST("payment/updateState")
    Observable<BaseResult> updateState(@Body List<ViolationNum> json);
}
