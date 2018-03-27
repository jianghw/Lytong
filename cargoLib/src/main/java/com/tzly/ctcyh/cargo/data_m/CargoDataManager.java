package com.tzly.ctcyh.cargo.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.ActiveConfigResponse;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.java.response.oil.NorOilResponse;
import com.tzly.ctcyh.cargo.bean.response.OrderExpressResponse;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreCaptchaResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.oil.OilCardsResponse;
import com.tzly.ctcyh.java.response.oil.OilRemainderResponse;
import com.tzly.ctcyh.router.custom.rea.RSAUtils;

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
    public Observable<RefuelOrderResponse> createOrder(RefuelOilDTO oilDTO, int i) {
        return mRemoteData.createOrder(oilDTO, i);
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

    /**
     * 领券
     */
    public Observable<ReceiveCouponResponse> receiveCoupon(String rasUserID, String couponId,
                                                           String channel) {
        return mRemoteData.receiveCoupon(rasUserID, couponId, channel);
    }

    /**
     * 获取配置接口
     */
    public Observable<ActiveConfigResponse> getConfig(String channel, String resisterDate) {
        return mRemoteData.getConfig(channel, resisterDate);
    }

    /**
     * 加油及充值
     */
    public Observable<RefuelOilResponse> findAndSaveCards(String rasUserID, String oilCard) {
        return mRemoteData.findAndSaveCards(rasUserID, oilCard);
    }

    /**
     * 办理油卡
     */
    public Observable<BidOilResponse> handleOilCard() {
        return mRemoteData.handleOilCard();
    }

    /**
     * 32.获取地区列表
     */
    public Observable<OrderExpressResponse> getAllAreas() {
        return mRemoteData.getAllAreas();
    }

    /**
     * 加油
     */
    public Observable<NorOilResponse> findOilCards(String rasUserID) {
        return mRemoteData.findOilCards(rasUserID);
    }

    public Observable<NorOilResponse> findCaiNiaoCard(String rasUserID) {
        return mRemoteData.findCaiNiaoCard(rasUserID);
    }

    /**
     * 2.1 获取验证码
     */
    public Observable<ScoreCaptchaResponse> scoresCaptcha(String code) {
        return mRemoteData.scoresCaptcha(code);
    }

    /**
     * 2.2 获取驾照扣分
     */
    public Observable<ScoreResponse> apiScores(String s, String s1, String s2, String cookie) {
        return mRemoteData.apiScores(s, s1, s2, cookie);
    }

    /**
     * 获取加油卡号及商品信息
     */
    public Observable<OilCardsResponse> findOilCardsAll(String rasUserID) {
        return mRemoteData.findOilCardsAll(rasUserID);
    }

    /**
     * 判断余额是否充足
     */
    public Observable<OilRemainderResponse> getRemainder(String goodsId, String card) {
        return mRemoteData.getRemainder(goodsId, card);
    }
}
