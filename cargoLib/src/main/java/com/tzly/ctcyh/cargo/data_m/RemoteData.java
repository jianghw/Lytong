package com.tzly.ctcyh.cargo.data_m;

import android.support.annotation.Nullable;

import com.tzly.ctcyh.cargo.api.IActivityService;
import com.tzly.ctcyh.cargo.api.IAddOilService;
import com.tzly.ctcyh.cargo.api.ILienseService;
import com.tzly.ctcyh.cargo.api.IRegionService;
import com.tzly.ctcyh.cargo.bean.BaseResponse;
import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.ActiveConfigResponse;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.cargo.bean.response.NorOilResponse;
import com.tzly.ctcyh.cargo.bean.response.OrderExpressResponse;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreCaptchaResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreResponse;

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

    private Retrofit xiaoFengRetrofit() {
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
    public Observable<RefuelOrderResponse> createOrder(RefuelOilDTO oilDTO, int i) {

        return i == 1
                ? baseRetrofit().create(IAddOilService.class).createOrder(
                oilDTO.getUserNum(), oilDTO.getGoodsId(),
                oilDTO.getPrice(), oilDTO.getOilCard(), oilDTO.getType())
                : baseRetrofit().create(IAddOilService.class).createOrder(
                oilDTO.getUserNum(), oilDTO.getGoodsId(),
                oilDTO.getPrice(), oilDTO.getType(),
                oilDTO.getName(), oilDTO.getPhone(),
                oilDTO.getSheng(), oilDTO.getShi(), oilDTO.getXian(), oilDTO.getAddressDetail()
        );
    }

    /**
     * 驾驶证
     */
    @Override
    public Observable<BaseResponse> bindingDriving(BindDrivingDTO drivingDTO) {
        return baseRetrofit().create(IAddOilService.class).bindingDriving(drivingDTO);
    }

    /**
     * 行驶证
     */
    @Override
    public Observable<BaseResponse> addVehicleLicense(BindCarDTO carDTO) {
        return baseRetrofit().create(IAddOilService.class).addVehicleLicense(carDTO);
    }

    /**
     * 领券
     */
    @Override
    public Observable<ReceiveCouponResponse> receiveCoupon(String rasUserID, String couponId, String channel) {
        return baseRetrofit().create(IActivityService.class).receiveCoupon(rasUserID, couponId, channel);
    }

    /**
     * 获取配置接口
     */
    @Override
    public Observable<ActiveConfigResponse> getConfig(String channel, String resisterDate) {
        return channel.equals("1") ? baseRetrofit().create(IActivityService.class).getConfig(channel, resisterDate)
                : baseRetrofit().create(IActivityService.class).getConfig(channel);
    }

    /**
     * 加油及充值
     */
    @Override
    public Observable<RefuelOilResponse> findAndSaveCards(String rasUserID, String oilCard) {
        return baseRetrofit().create(IAddOilService.class).findAndSaveCards(rasUserID, oilCard);
    }

    /**
     * 办理油卡
     */
    @Override
    public Observable<BidOilResponse> handleOilCard() {
        return baseRetrofit().create(IAddOilService.class).handleOilCard();
    }

    /**
     * 32.获取地区列表
     */
    @Override
    public Observable<OrderExpressResponse> getAllAreas() {
        return baseRetrofit().create(IRegionService.class).getAllAreas();
    }

    /**
     * 993加油
     */
    @Override
    public Observable<NorOilResponse> findOilCards(String rasUserID) {
        return baseRetrofit().create(IAddOilService.class).findOilCards(rasUserID);
    }

    /**
     * 997
     */
    @Override
    public Observable<NorOilResponse> findCaiNiaoCard(String rasUserID) {
        return baseRetrofit().create(IAddOilService.class).findCaiNiaoCard(rasUserID);
    }

    /**
     * 2.1 获取验证码
     */
    @Override
    public Observable<ScoreCaptchaResponse> scoresCaptcha(String code) {
        return xiaoFengRetrofit().create(ILienseService.class).scoresCaptcha(code);
    }

    /**
     * 2.2 获取驾照扣分
     */
    @Override
    public Observable<ScoreResponse> apiScores(String s, String s1, String s2, String cookie) {
        return xiaoFengRetrofit().create(ILienseService.class).apiScores(s, s1, s2, cookie);
    }


}
