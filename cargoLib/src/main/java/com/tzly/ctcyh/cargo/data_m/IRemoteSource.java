package com.tzly.ctcyh.cargo.data_m;


import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.cargo.bean.response.OrderExpressResponse;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreCaptchaResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.active.ActiveConfigResponse;
import com.tzly.ctcyh.java.response.oil.NorOilResponse;
import com.tzly.ctcyh.java.response.oil.OilAccepterInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilCardsResponse;
import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilRemainderResponse;
import com.tzly.ctcyh.java.response.oil.OilShareInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilShareResponse;

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
    Observable<RefuelOrderResponse> createOrder(RefuelOilDTO oilDTO, int i);

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

    Observable<ActiveConfigResponse> getConfig(String channel, String resisterDate);

    /**
     * 加油及充值
     */
    Observable<RefuelOilResponse> findAndSaveCards(String rasUserID, String oilCard);

    /**
     * 办理油卡
     */
    Observable<BidOilResponse> handleOilCard();

    Observable<OrderExpressResponse> getAllAreas();

    /**
     * 加油
     */
    Observable<NorOilResponse> findOilCards(String rasUserID);

    Observable<NorOilResponse> findCaiNiaoCard(String rasUserID);

    /**
     * 2.1 获取验证码
     */
    Observable<ScoreCaptchaResponse> scoresCaptcha(String code);

    /**
     * 2.2 获取驾照扣分
     */
    Observable<ScoreResponse> apiScores(String s, String s1, String s2, String cookie);

    /**
     * 获取加油卡号及商品信息
     */
    Observable<OilCardsResponse> findOilCardsAll(String rasUserID);

    /**
     * 判断余额是否充足
     */
    Observable<OilRemainderResponse> getRemainder(String goodsId, String card);

    /**
     * 获取办卡人数
     */
    Observable<OilEnterResponse> getCounts();

    Observable<OilModuleResponse> getOilModuleList();

    /**
     * 23.获取分享统计信息(新)
     */
    Observable<OilShareInfoResponse> getShareInfo(String rasUserID);

    /**
     * 22.分享人操作(新)
     */
    Observable<OilShareResponse> shareInfo(int configId, String rasUserID);

    /**
     * 被邀请人行为列表
     */
    Observable<OilAccepterInfoResponse> getAccepterInfoList(String rasUserID, int position);

    Observable<OilShareModuleResponse> shareModule();
}
