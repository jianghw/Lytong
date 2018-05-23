package com.tzly.ctcyh.cargo.api;


import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.oil.NorOilResponse;
import com.tzly.ctcyh.java.response.oil.OilAccepterInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilCardsResponse;
import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilRemainderResponse;
import com.tzly.ctcyh.java.response.oil.OilShareInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilShareResponse;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠卷服务器接口
 */

public interface IAddOilService {

    /**
     * 加油
     */
    @GET("addOil/getGoods")
    Observable<RefuelOilResponse> getGoods();

    /**
     * 创建加油订单
     */
    @FormUrlEncoded
    @POST("order/createOrder")
    Observable<RefuelOrderResponse> createOrder(
            @Field("userNum") String userNum,
            @Field("goodsId") String goodsId,
            @Field("price") String price,
            @Field("oilCard") String oilCard,
            @Field("type") String type
    );

    /**
     * 办卡
     */
    @FormUrlEncoded
    @POST("order/createOrder")
    Observable<RefuelOrderResponse> createOrder(
            @Field("userNum") String userNum,
            @Field("goodsId") String goodsId,
            @Field("price") String price,
            @Field("type") String type,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("sheng") String sheng,
            @Field("shi") String shi,
            @Field("xian") String xian,
            @Field("addressDetail") String addressDetail
    );

    /**
     * 驾驶证
     */
    @POST("cttx/bindingDriving")
    Observable<BaseResponse> bindingDriving(@Body BindDrivingDTO drivingDTO);

    /**
     * 16.新增车辆
     */
    @POST("cttx/addVehiclelicense")
    Observable<BaseResponse> addVehicleLicense(@Body BindCarDTO bindCarDTO);

    /**
     * 加油及充值
     */
    @FormUrlEncoded
    @POST("oil/findAndSaveCards")
    Observable<RefuelOilResponse> findAndSaveCards(@Field("userId") String rasUserID,
                                                   @Field("oilCard") String oilCard);

    /**
     * 办理油卡
     */
    @GET("handleOilCard/buyCard")
    Observable<BidOilResponse> handleOilCard();

    /**
     * 993
     */
    @GET("oil/findOilCards")
    Observable<NorOilResponse> findOilCards(@Query("userId") String rasUserID);

    @GET("oil/findCaiNiaoCard")
    Observable<NorOilResponse> findCaiNiaoCard(@Query("userId") String rasUserID);

    /**
     * 获取加油卡号及商品信息
     */
    @GET("oil/findOilCards")
    Observable<OilCardsResponse> findOilCardsAll(@Query("userId") String rasUserID);

    /**
     * 判断余额是否充足
     */
    @FormUrlEncoded
    @POST("oil/getRemainder")
    Observable<OilRemainderResponse> getRemainder(@Field("goodsId") String goodsId,
                                                  @Field("oilCard") String card);

    /**
     * 获取办卡人数
     */
    @GET("handleOilCard/getCounts")
    Observable<OilEnterResponse> getCounts();

    /**
     * 加油活动页
     */
    @GET("module/getOilModuleList")
    Observable<OilModuleResponse> getOilModuleList();

    /**
     * 23.获取分享统计信息(新)
     */
    @FormUrlEncoded
    @POST("shareApi/getShareInfo")
    Observable<OilShareInfoResponse> getShareInfo(@Field("usernum") String rasUserID);

    /**
     * 22.分享人操作(新)
     */
    @FormUrlEncoded
    @POST("shareApi/shareInfo/{configId}")
    Observable<OilShareResponse> shareInfo(@Path("configId") String configId, @Field("usernum") String rasUserID);

    /**
     * 被邀请人行为列表
     */
    @FormUrlEncoded
    @POST("shareApi/getAccepterInfoList")
    Observable<OilAccepterInfoResponse> getAccepterInfoList(@Field("usernum") String rasUserID, @Field("id") int position);

    /**
     * 1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练 6 年检，7 保养，8 海外驾驶培训，
     * 9 换电瓶，10 一元购，11 电影券，12 ETC，13 新加油充值，14 菜鸟加油充值，15 菜鸟购卡
     */
    @GET("module/shareModuleInfo")
    Observable<OilShareModuleResponse> shareModule(@Query("businessType") String businessType);
}
