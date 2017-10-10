package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResponse;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.bean.GoodsDetailResponse;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResponse;
import cn.qqtheme.framework.bean.response.SubjectGoodsResponse;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
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
    Observable<MerchantAresResponse> getMerchantArea();

    /**
     * 4.获取区域商品列表
     */
    @FormUrlEncoded
    @POST("goods/getAreaGoods")
    Observable<AresGoodsResponse> getAreaGoods(@Field("code") int code);

    /**
     * 2.创建订单
     */
    @FormUrlEncoded
    @POST("order/createOrder")
    Observable<CreateOrderResponse> createOrder(@FieldMap Map<String, String> map);

    /**
     * 6.获取商品详情
     */
    @FormUrlEncoded
    @POST("goods/getGoodsDetail")
    Observable<GoodsDetailResponse> getGoodsDetail(@Field("goodsId") String goodsId);

    /**
     * 22.获取商品
     */
    @FormUrlEncoded
    @POST("goods/getGoodsByType")
    Observable<SubjectGoodsResponse> getGoods(@Field("type") String type);

    @FormUrlEncoded
    @POST("goods/getGoodsByType")
    Observable<SparringGoodsResponse> getGoodsFive(@Field("type") String type);
}
