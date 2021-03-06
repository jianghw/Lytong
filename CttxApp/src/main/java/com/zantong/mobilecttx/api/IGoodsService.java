package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.bean.GoodsDetailResult;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IGoodsService {

    /**
     * 3.获取商户区域列表
     */
    @POST("goods/getMerchantArea")
    Observable<MerchantAresResult> getMerchantArea();

    /**
     * 4.获取区域商品列表
     */
    @FormUrlEncoded
    @POST("goods/getAreaGoods")
    Observable<AresGoodsResult> getAreaGoods(@Field("code") int code);

    /**
     * 2.创建订单
     *
     * @param type
     * @param userNum
     * @param goodsId
     * @param price
     * @param userName
     * @param phone
     * @param idCard
     * @return
     */
    @FormUrlEncoded
    @POST("order/createOrder")
    Observable<CreateOrderResult> createOrder(
            @Field("type") String type, @Field("userNum") String userNum,
            @Field("goodsId") String goodsId, @Field("price") String price,
            @Field("userName") String userName, @Field("phone") String phone,
            @Field("idCard") String idCard, @Field("payType") String payType);

    /**
     * 6.获取商品详情
     */
    @FormUrlEncoded
    @POST("goods/getGoodsDetail")
    Observable<GoodsDetailResult> getGoodsDetail(@Field("goodsId") String goodsId);
}
