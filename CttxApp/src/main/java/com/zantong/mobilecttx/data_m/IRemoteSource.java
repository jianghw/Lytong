package com.zantong.mobilecttx.data_m;

import com.tzly.ctcyh.java.response.BankResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.SubjectGoodsResponse;
import com.zantong.mobilecttx.base.bean.PayWeixinResponse;
import com.zantong.mobilecttx.base.bean.UnimpededBannerResponse;
import com.zantong.mobilecttx.base.bean.ValidCountResponse;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.bean.VehicleLicenseResponse;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResponse;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResponse;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.bean.GoodsDetailResponse;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResponse;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;
import com.zantong.mobilecttx.fahrschule.bean.ServerTimeResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResponse;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.home.bean.BannerResponse;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerResponse;
import com.zantong.mobilecttx.home.bean.ModuleResponse;
import com.zantong.mobilecttx.home.bean.StartPicResponse;
import com.zantong.mobilecttx.home.bean.VersionResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.home.dto.VersionDTO;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.GasStationResponse;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.map.dto.AnnualDTO;
import com.zantong.mobilecttx.order.bean.CouponFragmentResponse;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.order.bean.OrderDetailResponse;
import com.zantong.mobilecttx.order.bean.OrderExpressResponse;
import com.zantong.mobilecttx.order.bean.OrderListResponse;
import com.zantong.mobilecttx.order.bean.ReceiveInfoResponse;
import com.zantong.mobilecttx.order.dto.ExpressDTO;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;
import com.zantong.mobilecttx.user.bean.MessageDetailResponse;
import com.zantong.mobilecttx.user.bean.MessageTypeResponse;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;
import com.tzly.ctcyh.java.response.violation.ViolationNum;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IRemoteSource {
    /**
     * 查询所有消息
     *
     * @param bean
     * @return
     */
    Observable<MessageTypeResponse> messageFindAll(BaseDTO bean);

    /**
     * 2.4.21获取消息详情列表
     *
     * @param bean
     * @return
     */
    Observable<MessageResponse> findMessageDetailByMessageId(MegDTO bean);

    /**
     * 2.4.22获取消息详情
     *
     * @param bean
     * @return
     */
    Observable<MessageDetailResponse> findMessageDetail(MessageDetailDTO bean);

    /**
     * 2.4.2查看优惠券信息
     */
    Observable<CouponFragmentResponse> usrCouponInfo(String usrnum, String couponStatus);

    /**
     * 驾驶证查分 cip.cfc.v001.01
     */
    Observable<LicenseResponseBean> driverLicenseCheckGrade(String requestDTO);

    /**
     * 2.4.24删除消息
     *
     * @param megDTO
     * @return
     */
    Observable<MessageResponse> deleteMessageDetail(MegDTO megDTO);

    /**
     * 2.4.27删除用户优惠券
     */
    Observable<MessageResponse> delUsrCoupon(String couponId, String userId);

    /**
     * https://ctkapptest.icbc-axa.com/ecip/mobilecall_call
     *
     * @param msg
     * @return
     */
    Observable<LoginInfoBean> loadLoginPost(String msg);

    /**
     * 40.app启动图片获取
     *
     * @param msg
     * @return
     */
    Observable<StartPicResponse> startGetPic(String msg);

    /**
     * 57.获取指定类型优惠券
     */
    Observable<RechargeCouponResponse> getCouponByType(String userId, String type);

    /**
     * 10.创建加油订单
     */
    Observable<RechargeResponse> addOilCreateOrder(RechargeDTO rechargeDTO);

    /**
     * 加油充值
     */
    Observable<PayOrderResponse> onPayOrderByCoupon(String payUrl, String orderPrice, String payType);

    /**
     * 43.生成违章缴费订单
     */
    Observable<PayOrderResponse> paymentCreateOrder(ViolationPayDTO payDTO);

    /**
     * cip.cfc.v003.01
     */
    Observable<ViolationNumBean> numberedQuery(String msg);

    /**
     * 1.首页信息
     */
    Observable<HomeResponse> homePage(HomeDataDTO id);

    /**
     * cip.cfc.c003.01
     */
    Observable<UserCarsResult> getRemoteCarInfo(String requestDTO);

    /**
     * 37.获取所有未读消息数量
     */
    Observable<MessageCountResponse> countMessageDetail(BaseDTO baseDTO);

    /**
     * 58.获取banner图片
     */
    Observable<BannerResponse> getBanner(String type);

    /**
     * 55.行驶证扫描接口
     */
    Observable<DrivingOcrResult> uploadDrivingImg(MultipartBody.Part part);

    /**
     * 3.获取商户区域列表
     */
    Observable<MerchantAresResponse> getMerchantArea();

    /**
     * 4.获取区域商品列表
     */
    Observable<AresGoodsResponse> getAreaGoods(int areaCode);

    /**
     * 2.创建订单
     */
    Observable<CreateOrderResponse> createOrder(CreateOrderDTO createOrder);

    /**
     * 7.获取用户指定活动的统计总数
     */
    Observable<RecordCountResponse> getRecordCount(String type, String phone);

    /**
     * 48.绑定行驶证接口
     */
    Observable<BaseResponse> commitCarInfoToNewServer(BindCarDTO bindCarDTO);

    /**
     * cip.cfc.u005.01
     */
    Observable<BankResponse> commitCarInfoToOldServer(String msg);

    /**
     * 5.获取工行支付页面
     */
    Observable<PayOrderResponse> getBankPayHtml(String orderId, String orderPrice);

    Observable<PayOrderResponse> getBankPayHtml(String orderId, String orderPrice, int coupon);

    /**
     * 8.查询订单列表
     */
    Observable<OrderListResponse> getOrderList(String userId, String pager);

    /**
     * 9.获取订单详情
     */
    Observable<OrderDetailResponse> getOrderDetail(String orderId);

    /**
     * 10.更新订单状态
     */
    Observable<BaseResponse> updateOrderStatus(String orderId, int orderStatus);

    /**
     * 6.获取商品详情
     */
    Observable<GoodsDetailResponse> getGoodsDetail(String goodsId);

    /**
     * 获取违章信息
     */
    Observable<HomeCarResponse> getTextNoticeInfo(String defaultUserID);

    /**
     * 处理违章信息
     */
    Observable<BaseResponse> handleViolations(ViolationCarDTO violationResult);

    /**
     * 车辆违章查询
     * cip.cfc.v002.01
     */
    Observable<ViolationResultParent> searchViolation(String msg);

    /**
     * cip.cfc.c002.01
     */
    Observable<PayCarResult> getPayCars(String msg);

    Observable<VehicleLicenseResponse> addOrUpdateVehicleLicense(List<BindCarDTO> dtoList);

    /**
     * 13.判断是否为司机
     */
    Observable<DriverCoachResponse> getDriverCoach(String phone);

    /**
     * 16.新增车辆
     */
    Observable<BaseResponse> addVehicleLicense(BindCarDTO bindCarDTO);

    /**
     * 18.删除车辆
     */
    Observable<BaseResponse> removeVehicleLicense(BindCarDTO bindCarDTO);

    /**
     * 17.编辑车辆
     */
    Observable<BaseResponse> updateVehicleLicense(BindCarDTO bindCarDTO);

    /**
     * 22.获取商品
     */
    Observable<SubjectGoodsResponse> getGoods(String type);

    Observable<SparringGoodsResponse> getGoodsFive(String type);

    /**
     * 20.新手陪练获取服务地区
     */
    Observable<SparringAreaResponse> getServiceArea();

    /**
     * 21.获取服务器时间
     */
    Observable<ServerTimeResponse> getServerTime();

    /**
     * 10.取消订单
     */
    Observable<BaseResponse> cancelOrder(String orderId, String userNum);

    /**
     * 24.获取年检网点
     */
    Observable<YearCheckResponse> annualInspectionList(AnnualDTO annualDTO);

    /**
     * 获取年检一条信息
     */
    Observable<YearCheckDetailResponse> annualInspection(int id);

    /**
     * 获得加油站地点详情
     */
    Observable<GasStationDetailResponse> gasStation(int id);

    /**
     * 23.获取加油网点
     */
    Observable<GasStationResponse> gasStationList(AnnualDTO annualDTO);

    /**
     * 25.模块化接口
     */
    Observable<ModuleResponse> moduleTree();

    /**
     * 32.获取地区列表
     */
    Observable<OrderExpressResponse> getAllAreas();

    /**
     * 29.填写快递信息
     */
    Observable<BaseResponse> addExpressInfo(ExpressDTO expressDTO);

    /**
     * 33.获取收件人信息
     */
    Observable<ReceiveInfoResponse> getReceiveInfo(String orderId);

    /**
     * 46.更新违章缴费状态
     *
     * @param json
     */
    Observable<BaseResponse> updateState(List<ViolationNum> json);

    /**
     * 是否提供活动
     */
    Observable<IndexLayerResponse> getIndexLayer();

    Observable<ViolationDetailsBean> violationDetails_v003(String msg);

    /**
     * 可用的优惠券
     */
    Observable<ValidCountResponse> getValidCount(String rasUserID);

    /**
     * 统计
     */
    Observable<BaseResponse> saveStatisticsCount(String contentId, String rasUserID);

    /**
     * 微信支付
     */
    Observable<PayWeixinResponse> weChatPay(String orderId, String amount, int couponUserId);

    Observable<BaseResponse> updateToken(String token, String userNum);

    /**
     * 模块配置接口
     */
    Observable<UnimpededBannerResponse> getBanner();

    /**
     * 分享统计数据
     */
    Observable<StatistCountResponse> getStatisticsCount(String phone);

    /**
     * 版本更新
     */
    Observable<VersionResponse> versionInfo(VersionDTO versionDTO);
}
