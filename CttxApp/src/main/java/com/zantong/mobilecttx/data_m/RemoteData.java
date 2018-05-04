package com.zantong.mobilecttx.data_m;

import android.text.TextUtils;

import com.tzly.ctcyh.java.request.order.UpdateOrderDTO;
import com.tzly.ctcyh.java.response.BankResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.SubjectGoodsResponse;
import com.tzly.ctcyh.java.response.active.ActiveConfigResponse;
import com.tzly.ctcyh.java.response.module.NewsFlagResponse;
import com.tzly.ctcyh.java.response.news.NewsInfoResponse;
import com.tzly.ctcyh.java.response.order.OrderInfoResponse;
import com.tzly.ctcyh.java.response.order.OrderRefundResponse;
import com.tzly.ctcyh.java.response.order.UpdateOrderResponse;
import com.tzly.ctcyh.java.response.reservation.ReservationResponse;
import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.tzly.ctcyh.java.response.violation.ViolationNum;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.zantong.mobilecttx.api.IAddOilService;
import com.zantong.mobilecttx.api.IBankService;
import com.zantong.mobilecttx.api.IBannerService;
import com.zantong.mobilecttx.api.ICttxService;
import com.zantong.mobilecttx.api.IDriverTrainService;
import com.zantong.mobilecttx.api.IDrivingImageService;
import com.zantong.mobilecttx.api.IFebruaryService;
import com.zantong.mobilecttx.api.IGoodsService;
import com.zantong.mobilecttx.api.IMessageService;
import com.zantong.mobilecttx.api.IModuleService;
import com.zantong.mobilecttx.api.IOrderService;
import com.zantong.mobilecttx.api.IPayService;
import com.zantong.mobilecttx.api.IRegionService;
import com.zantong.mobilecttx.api.ISplashService;
import com.zantong.mobilecttx.api.ITextService;
import com.zantong.mobilecttx.api.IUserService;
import com.zantong.mobilecttx.api.IViolationService;
import com.zantong.mobilecttx.base.bean.ModuleBannerResponse;
import com.zantong.mobilecttx.base.bean.PayWeixinResponse;
import com.zantong.mobilecttx.base.bean.ValidCountResponse;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.bean.VehicleLicenseResponse;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResponse;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.contract.CTTXHttpPOSTInterface;
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
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 远程数据处理
 */


public class RemoteData implements IRemoteSource {
    /**
     * 单例
     */
    public static RemoteData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        static final RemoteData INSTANCE = new RemoteData();
    }

    private Retrofit baseRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(1);
    }

    private Retrofit bankRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(2);
    }

    private Retrofit imageRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(3);
    }

    private Retrofit localRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(4);
    }

    /**
     * 消息模块
     */
    @Override
    public Observable<MessageTypeResponse> messageFindAll(BaseDTO bean) {
        return baseRetrofit().create(IMessageService.class).messageFindAll(bean);
    }

    @Override
    public Observable<MessageResponse> findMessageDetailByMessageId(MegDTO bean) {
        return baseRetrofit().create(IMessageService.class).findMessageDetailByMessageId(bean);
    }

    @Override
    public Observable<MessageDetailResponse> findMessageDetail(MessageDetailDTO bean) {
        return baseRetrofit().create(IMessageService.class).findMessageDetail(bean);
    }

    /**
     * 2.4.24删除消息
     */
    @Override
    public Observable<MessageResponse> deleteMessageDetail(MegDTO megDTO) {
        return baseRetrofit().create(IMessageService.class).deleteMessageDetail(megDTO);
    }

    /**
     * 优惠劵模块
     */
    @Override
    public Observable<CouponFragmentResponse> usrCouponInfo(String usrnum, String couponStatus) {
        return baseRetrofit().create(IFebruaryService.class).usrCouponInfo(usrnum, couponStatus);
    }

    /**
     * 2.4.27删除用户优惠券
     */
    @Override
    public Observable<MessageResponse> delUsrCoupon(String couponId, String userId) {
        return baseRetrofit().create(IFebruaryService.class).delUsrCoupon(couponId, userId);
    }

    /**
     * 查分
     *
     * @param requestDTO
     * @return
     */
    @Override
    public Observable<LicenseResponseBean> driverLicenseCheckGrade(String requestDTO) {
        return bankRetrofit().create(IBankService.class).driverLicenseCheckGrade(requestDTO);
    }

    /**
     * 安盛登录
     * https://ctkapptest.icbc-axa.com/ecip/mobilecall_call
     *
     * @param msg
     * @return
     */
    @Override
    public Observable<LoginInfoBean> loadLoginPost(String msg) {
        return bankRetrofit().create(CTTXHttpPOSTInterface.class).loadPost(msg);
    }

    /**
     * 40.app启动图片获取
     *
     * @param msg 图片类型(0主页图片，1其余图片)
     * @return
     */
    @Override
    public Observable<StartPicResponse> startGetPic(String msg) {
        return baseRetrofit().create(ISplashService.class).startGetPic(msg);
    }

    /**
     * 57.获取指定类型优惠券
     */
    @Override
    public Observable<RechargeCouponResponse> getCouponByType(String userId, String type) {
        return baseRetrofit().create(IFebruaryService.class).getConponByType(userId, type);
    }

    /**
     * 10.创建加油订单
     */
    @Override
    public Observable<RechargeResponse> addOilCreateOrder(RechargeDTO rechargeDTO) {
        return baseRetrofit().create(IAddOilService.class).addOilCreateOrder(rechargeDTO);
    }

    /**
     * 54.充值接口
     */
    @Override
    public Observable<PayOrderResponse> onPayOrderByCoupon(String payUrl, String orderPrice, String payType) {
        return baseRetrofit().create(IAddOilService.class).onPayOrderByCoupon(payUrl, orderPrice, payType);
    }

    /**
     * 43.生成违章缴费订单
     */
    @Override
    public Observable<PayOrderResponse> paymentCreateOrder(ViolationPayDTO payDTO) {
        return baseRetrofit().create(IViolationService.class).paymentCreateOrder(payDTO);
    }

    @Override
    public Observable<ViolationNumBean> numberedQuery(String msg) {
        return bankRetrofit().create(IBankService.class).numberedQuery(msg);
    }

    /**
     * 1.首页信息
     */
    @Override
    public Observable<HomeResponse> homePage(HomeDataDTO id) {
        return baseRetrofit().create(ICttxService.class).homePage(id);
    }

    /**
     * cip.cfc.c003.01
     */
    @Override
    public Observable<UserCarsResult> getRemoteCarInfo(String requestDTO) {
        return bankRetrofit().create(IBankService.class).getRemoteCarInfo(requestDTO);
    }

    /**
     * 37.获取所有未读消息数量
     */
    @Override
    public Observable<MessageCountResponse> countMessageDetail(BaseDTO baseDTO) {
        return baseRetrofit().create(IMessageService.class).countMessageDetail(baseDTO);
    }

    /**
     * 58.获取banner图片
     */
    @Override
    public Observable<BannerResponse> getBanner(String type) {
        return baseRetrofit().create(IBannerService.class).getBanner(type);
    }

    /**
     * 55.行驶证扫描接口
     */
    @Override
    public Observable<DrivingOcrResult> uploadDrivingImg(MultipartBody.Part part) {
        return imageRetrofit().create(IDrivingImageService.class).uploadDrivingImg(part);
    }

    /**
     * 3.获取商户区域列表
     */
    @Override
    public Observable<MerchantAresResponse> getMerchantArea() {
        return baseRetrofit().create(IGoodsService.class).getMerchantArea();
    }

    /**
     * 4.获取区域商品列表
     */
    @Override
    public Observable<AresGoodsResponse> getAreaGoods(int areaCode) {
        return baseRetrofit().create(IGoodsService.class).getAreaGoods(areaCode);
    }

    /**
     * 2.创建订单
     */
    @Override
    public Observable<CreateOrderResponse> createOrder(CreateOrderDTO createOrder) {
        Map<String, String> options = new HashMap<>();
        if (!TextUtils.isEmpty(createOrder.getType()))
            options.put("type", createOrder.getType());

        if (!TextUtils.isEmpty(createOrder.getUserNum()))
            options.put("userNum", createOrder.getUserNum());

        if (!TextUtils.isEmpty(createOrder.getGoodsId()))
            options.put("goodsId", createOrder.getGoodsId());

        if (!TextUtils.isEmpty(createOrder.getPrice()))
            options.put("price", createOrder.getPrice());

        if (!TextUtils.isEmpty(createOrder.getRemark()))
            options.put("remark", createOrder.getRemark());

        if (!TextUtils.isEmpty(createOrder.getOilCardNum()))
            options.put("oilCardNum", createOrder.getOilCardNum());

        if (!TextUtils.isEmpty(createOrder.getPayType()))
            options.put("payType", createOrder.getPayType());

        if (!TextUtils.isEmpty(createOrder.getCouponId()))
            options.put("couponId", createOrder.getCouponId());

        if (!TextUtils.isEmpty(createOrder.getSpeedType()))
            options.put("speedType", createOrder.getSpeedType());

        if (!TextUtils.isEmpty(createOrder.getUserName()))
            options.put("userName", createOrder.getUserName());

        if (!TextUtils.isEmpty(createOrder.getPhone()))
            options.put("phone", createOrder.getPhone());

        if (!TextUtils.isEmpty(createOrder.getIdCard()))
            options.put("idCard", createOrder.getIdCard());

        if (!TextUtils.isEmpty(createOrder.getServiceArea()))
            options.put("serviceArea", createOrder.getServiceArea());

        if (!TextUtils.isEmpty(createOrder.getServiceAddress()))
            options.put("serviceAddress", createOrder.getServiceAddress());

        if (!TextUtils.isEmpty(createOrder.getStartTime()))
            options.put("startTime", createOrder.getStartTime());

        if (!TextUtils.isEmpty(createOrder.getEndTime()))
            options.put("endTime", createOrder.getEndTime());

        if (!TextUtils.isEmpty(createOrder.getDriveNum()))
            options.put("driveNum", createOrder.getDriveNum());

        return baseRetrofit().create(IGoodsService.class).createOrder(options);
    }

    /**
     * 7.获取用户指定活动的统计总数
     */
    @Override
    public Observable<RecordCountResponse> getRecordCount(String type, String phone) {
        return baseRetrofit().create(IFebruaryService.class).getRecordCount(type, phone);
    }

    /**
     * cip.cfc.u005.01
     */
    @Override
    public Observable<BankResponse> commitCarInfoToOldServer(String msg) {
        return bankRetrofit().create(IBankService.class).commitCarInfoToOldServer(msg);
    }

    /**
     * 48.绑定行驶证接口
     */
    @Override
    public Observable<BaseResponse> commitCarInfoToNewServer(BindCarDTO bindCarDTO) {
        return baseRetrofit().create(ICttxService.class).commitCarInfoToNewServer(bindCarDTO);
    }

    /**
     * 5.获取工行支付页面
     */
    @Override
    public Observable<PayOrderResponse> getBankPayHtml(String orderId, String orderPrice) {
        return baseRetrofit().create(IPayService.class).getBankPayHtml(orderId, orderPrice);
    }

    @Override
    public Observable<PayOrderResponse> getBankPayHtml(String orderId, String orderPrice, int coupon) {
        return baseRetrofit().create(IPayService.class).getBankPayHtml(orderId, orderPrice, String.valueOf(coupon));
    }

    /**
     * 8.查询订单列表
     */
    @Override
    public Observable<OrderListResponse> getOrderList(String userId, String pager) {
        return baseRetrofit().create(IOrderService.class).getOrderList(userId, pager);
    }

    /**
     * 9.获取订单详情
     */
    @Override
    public Observable<OrderDetailResponse> getOrderDetail(String orderId) {
        return baseRetrofit().create(IOrderService.class).getOrderDetail(orderId);
    }

    /**
     * 10.更新订单状态
     */
    @Override
    public Observable<BaseResponse> updateOrderStatus(String orderId, int orderStatus) {
        return baseRetrofit().create(IOrderService.class).updateOrderStatus(orderId, String.valueOf(orderStatus));
    }

    /**
     * 6.获取商品详情
     */
    @Override
    public Observable<GoodsDetailResponse> getGoodsDetail(String goodsId) {
        return baseRetrofit().create(IGoodsService.class).getGoodsDetail(goodsId);
    }

    /**
     * 获取违章信息
     */
    @Override
    public Observable<HomeCarResponse> getTextNoticeInfo(String defaultUserID) {
        return baseRetrofit().create(ITextService.class).getTextNoticeInfo(defaultUserID);
    }

    /**
     * 处理违章信息
     */
    @Override
    public Observable<BaseResponse> handleViolations(ViolationCarDTO violationResult) {
        return baseRetrofit().create(ITextService.class).HandleViolationDTO(violationResult);
    }

    /**
     * 车辆违章查询
     * cip.cfc.v002.01
     */
    @Override
    public Observable<ViolationResultParent> searchViolation(String msg) {
        return bankRetrofit().create(IBankService.class).searchViolation(msg);
    }

    /**
     * cip.cfc.c002.01
     */
    @Override
    public Observable<PayCarResult> getPayCars(String msg) {
        return bankRetrofit().create(IBankService.class).getPayCars(msg);
    }

    /**
     * 19.同步银行车辆
     */
    @Override
    public Observable<VehicleLicenseResponse> addOrUpdateVehicleLicense(List<BindCarDTO> dtoList) {
        return baseRetrofit().create(ICttxService.class).addOrUpdateVehicleLicense(dtoList);
    }

    /**
     * 13.判断是否为司机
     */
    @Override
    public Observable<DriverCoachResponse> getDriverCoach(String phone) {
        return baseRetrofit().create(IUserService.class).getDriverCoach(phone);
    }

    /**
     * 16.新增车辆
     */
    @Override
    public Observable<BaseResponse> addVehicleLicense(BindCarDTO bindCarDTO) {
        return baseRetrofit().create(ICttxService.class).addVehicleLicense(bindCarDTO);
    }

    /**
     * 18.删除车辆
     */
    @Override
    public Observable<BaseResponse> removeVehicleLicense(BindCarDTO bindCarDTO) {
        return baseRetrofit().create(ICttxService.class).removeVehicleLicense(bindCarDTO);
    }

    /**
     * 17.编辑车辆
     */
    @Override
    public Observable<BaseResponse> updateVehicleLicense(BindCarDTO bindCarDTO) {
        return baseRetrofit().create(ICttxService.class).updateVehicleLicense(bindCarDTO);
    }

    /**
     * 22.获取商品
     */
    @Override
    public Observable<SubjectGoodsResponse> getGoods(String type) {
        return baseRetrofit().create(IGoodsService.class).getGoods(type);
    }

    @Override
    public Observable<SparringGoodsResponse> getGoodsFive(String type) {
        return baseRetrofit().create(IGoodsService.class).getGoodsFive(type);
    }

    /**
     * 20.新手陪练获取服务地区
     */
    @Override
    public Observable<SparringAreaResponse> getServiceArea() {
        return baseRetrofit().create(IDriverTrainService.class).getServiceArea();
    }

    /**
     * 21.获取服务器时间
     */
    @Override
    public Observable<ServerTimeResponse> getServerTime() {
        return baseRetrofit().create(IDriverTrainService.class).getServerTime();
    }

    /**
     * 10.取消订单
     */
    @Override
    public Observable<BaseResponse> cancelOrder(String orderId, String userNum) {
        return baseRetrofit().create(IOrderService.class).cancelOrder(orderId, userNum);
    }

    /**
     * 24.获取年检网点
     */
    @Override
    public Observable<YearCheckResponse> annualInspectionList(AnnualDTO annualDTO) {
        return baseRetrofit().create(ICttxService.class).annualInspectionList(annualDTO);
    }

    /**
     * 获取年检一条信息
     */
    @Override
    public Observable<YearCheckDetailResponse> annualInspection(int id) {
        return baseRetrofit().create(ICttxService.class).annualInspection(id);
    }

    /**
     * 获得加油站地点详情
     */
    @Override
    public Observable<GasStationDetailResponse> gasStation(int id) {
        return baseRetrofit().create(ICttxService.class).gasStation(id);
    }

    /**
     * 23.获取加油网点
     */
    @Override
    public Observable<GasStationResponse> gasStationList(AnnualDTO annualDTO) {
        return baseRetrofit().create(ICttxService.class).gasStationList(annualDTO);
    }

    /**
     * 25.模块化接口
     */
    @Override
    public Observable<ModuleResponse> moduleTree() {

        return baseRetrofit().create(IModuleService.class).moduleTree();
    }

    /**
     * 32.获取地区列表
     */
    @Override
    public Observable<OrderExpressResponse> getAllAreas() {
        return baseRetrofit().create(IRegionService.class).getAllAreas();
    }

    /**
     * 29.填写快递信息
     */
    @Override
    public Observable<BaseResponse> addExpressInfo(ExpressDTO expressDTO) {
        return baseRetrofit().create(IOrderService.class).addExpressInfo(expressDTO);
    }

    /**
     * 33.获取收件人信息
     */
    @Override
    public Observable<ReceiveInfoResponse> getReceiveInfo(String orderId) {
        return baseRetrofit().create(IOrderService.class).getReceiveInfo(orderId);
    }

    /**
     * 46.更新违章缴费状态
     *
     * @param json
     */
    @Override
    public Observable<BaseResponse> updateState(List<ViolationNum> json) {
        return baseRetrofit().create(IPayService.class).updateState(json);
    }

    /**
     * 是否提供活动
     */
    @Override
    public Observable<IndexLayerResponse> getIndexLayer() {
        return baseRetrofit().create(ICttxService.class).getIndexLayer();
    }

    /**
     * 违章查询详情
     */
    @Override
    public Observable<ViolationDetailsBean> violationDetails_v003(String msg) {
        return bankRetrofit().create(IBankService.class).violationDetails_v003(msg);
    }

    /**
     * 可用的优惠券
     */
    @Override
    public Observable<ValidCountResponse> getValidCount(String rasUserID) {
        return baseRetrofit().create(IFebruaryService.class).getValidCount(rasUserID);
    }

    /**
     * 统计
     */
    @Override
    public Observable<BaseResponse> saveStatisticsCount(String contentId, String rasUserID) {
        return baseRetrofit().create(IBannerService.class).saveStatisticsCount(contentId, rasUserID);
    }

    @Override
    public Observable<PayWeixinResponse> weChatPay(String orderId, String amount, int couponUserId) {
        return couponUserId <= 0 ? baseRetrofit().create(IPayService.class).weChatPay(orderId, amount)
                : baseRetrofit().create(IPayService.class).weChatPay(orderId, amount, String.valueOf(couponUserId));
    }

    /**
     * token
     */
    @Override
    public Observable<BaseResponse> updateToken(String token, String userNum) {
        return baseRetrofit().create(ICttxService.class).updateToken(token, userNum);
    }

    /**
     * 模块配置接口
     */
    @Override
    public Observable<ModuleBannerResponse> getBanner() {
        return baseRetrofit().create(IModuleService.class).getBanner();
    }

    /**
     * 分享统计数据
     */
    @Override
    public Observable<StatistCountResponse> getStatisticsCount(String phone) {
        return baseRetrofit().create(IFebruaryService.class).getStatisticsCount(phone);
    }

    /**
     * 版本更新
     */
    @Override
    public Observable<VersionResponse> versionInfo(VersionDTO versionDTO) {
        return baseRetrofit().create(ICttxService.class).versionInfo(versionDTO);
    }

    /**
     * 修改订单详情
     */
    @Override
    public Observable<UpdateOrderResponse> updateOrderDetail(UpdateOrderDTO updateOrderDTO) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", updateOrderDTO.getOrderId());

        if (!TextUtils.isEmpty(updateOrderDTO.getName()))
            map.put("name", updateOrderDTO.getName());

        if (!TextUtils.isEmpty(updateOrderDTO.getPhone()))
            map.put("phone", updateOrderDTO.getPhone());

        if (!TextUtils.isEmpty(updateOrderDTO.getSheng()))
            map.put("sheng", updateOrderDTO.getSheng());

        if (!TextUtils.isEmpty(updateOrderDTO.getShi()))
            map.put("shi", updateOrderDTO.getShi());

        if (!TextUtils.isEmpty(updateOrderDTO.getXian()))
            map.put("xian", updateOrderDTO.getXian());

        if (!TextUtils.isEmpty(updateOrderDTO.getAddressDetail()))
            map.put("addressDetail", updateOrderDTO.getAddressDetail());

        if (!TextUtils.isEmpty(updateOrderDTO.getSupplement()))
            map.put("supplement", updateOrderDTO.getSupplement());

        if (!TextUtils.isEmpty(updateOrderDTO.getBespeakDate()))
            map.put("bespeakDate", updateOrderDTO.getBespeakDate());

        if (!TextUtils.isEmpty(updateOrderDTO.getExpressTime()))
            map.put("expressTime", updateOrderDTO.getExpressTime());

        if (!TextUtils.isEmpty(updateOrderDTO.getShengCode()))
            map.put("shengCode", updateOrderDTO.getShengCode());

        if (!TextUtils.isEmpty(updateOrderDTO.getShicode()))
            map.put("shiCode", updateOrderDTO.getShicode());

        if (!TextUtils.isEmpty(updateOrderDTO.getXianCode()))
            map.put("xianCode", updateOrderDTO.getXianCode());

        return baseRetrofit().create(IOrderService.class).updateOrderDetail(map);
    }

    /**
     * 催单,退款
     */
    @Override
    public Observable<OrderRefundResponse> info(String channel, String orderId, String remark) {
        return baseRetrofit().create(IOrderService.class).info(channel, orderId, remark);
    }

    /**
     * 反显用户信息
     */
    @Override
    public Observable<OrderInfoResponse> getUserOrderInfo(String orderId) {
        return baseRetrofit().create(IOrderService.class).getUserOrderInfo(orderId);
    }

    /**
     * 查违章小广告
     */
    @Override
    public Observable<ValidAdvResponse> findIsValidAdvert() {
        return baseRetrofit().create(ITextService.class).findIsValidAdvert();
    }

    /**
     * 获取配置接口
     */
    @Override
    public Observable<ActiveConfigResponse> getConfig(String channel, String resisterDate) {
        return channel.equals("1") ? baseRetrofit().create(ITextService.class).getConfig(channel, resisterDate)
                : baseRetrofit().create(ITextService.class).getConfig(channel);
    }

    /**
     * 领券
     */
    @Override
    public Observable<BaseResponse> receiveCoupon(String rasUserID, String couponId, String channel) {
        return baseRetrofit().create(ITextService.class).receiveCoupon(rasUserID, couponId, channel);
    }

    /**
     * 广告统计
     */
    @Override
    public Observable<BaseResponse> advertCount(String keyId, String channel) {
        return baseRetrofit().create(ITextService.class).advertCount(keyId, channel);
    }

    /**
     * 资讯列表news/findByType
     */
    @Override
    public Observable<NewsInfoResponse> findByType(int type) {
        return baseRetrofit().create(IModuleService.class).findByType(type);
    }

    /**
     * 资讯Icons
     */
    @Override
    public Observable<ModuleBannerResponse> getIcons() {
        return baseRetrofit().create(IModuleService.class).getIcons();
    }

    /**
     * 资讯导航
     */
    @Override
    public Observable<ModuleBannerResponse> getNavigations() {
        return baseRetrofit().create(IModuleService.class).getNavigations();
    }

    @Override
    public Observable<NewsFlagResponse> newsFlag() {
        return baseRetrofit().create(IModuleService.class).newsFlag();
    }

    /**
     * 预约列表
     */
    @Override
    public Observable<ReservationResponse> getBespeakList(String rasUserID) {
        return baseRetrofit().create(IOrderService.class).getBespeakList(rasUserID);
    }
}
