package com.zantong.mobile.model.repository;

import android.text.TextUtils;

import com.tzly.annual.base.bean.BaseResponse;
import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.Result;
import com.tzly.annual.base.bean.request.RegisterDTO;
import com.tzly.annual.base.bean.response.AnnouncementResult;
import com.tzly.annual.base.bean.response.CattleOrderResponse;
import com.tzly.annual.base.bean.response.OrderListResult;
import com.tzly.annual.base.bean.response.StatistCountResult;
import com.tzly.annual.base.bean.response.SubjectGoodsResult;
import com.zantong.mobile.api.IAddOilService;
import com.zantong.mobile.api.IBankService;
import com.zantong.mobile.api.IBannerService;
import com.zantong.mobile.api.ICttxService;
import com.zantong.mobile.api.IDriverTrainService;
import com.zantong.mobile.api.IDrivingImageService;
import com.zantong.mobile.api.IFebruaryService;
import com.zantong.mobile.api.IGoodsService;
import com.zantong.mobile.api.IMessageService;
import com.zantong.mobile.api.IModuleService;
import com.zantong.mobile.api.IOrderService;
import com.zantong.mobile.api.IPayService;
import com.zantong.mobile.api.IRegionService;
import com.zantong.mobile.api.ISplashService;
import com.zantong.mobile.api.ITextService;
import com.zantong.mobile.api.IUserService;
import com.zantong.mobile.api.IViolationService;
import com.zantong.mobile.base.dto.BaseDTO;
import com.zantong.mobile.car.bean.PayCarResult;
import com.zantong.mobile.car.bean.VehicleLicenseResult;
import com.zantong.mobile.card.dto.BindCarDTO;
import com.zantong.mobile.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobile.chongzhi.bean.RechargeResult;
import com.zantong.mobile.chongzhi.dto.RechargeDTO;
import com.zantong.mobile.contract.CTTXHttpPOSTInterface;
import com.zantong.mobile.daijia.bean.DrivingOcrResult;
import com.zantong.mobile.fahrschule.bean.AresGoodsResult;
import com.zantong.mobile.fahrschule.bean.CreateOrderResult;
import com.zantong.mobile.fahrschule.bean.GoodsDetailResult;
import com.zantong.mobile.fahrschule.bean.MerchantAresResult;
import com.zantong.mobile.fahrschule.bean.RecordCountResult;
import com.zantong.mobile.fahrschule.bean.ServerTimeResult;
import com.zantong.mobile.fahrschule.bean.SparringAreaResult;
import com.zantong.mobile.fahrschule.bean.SparringGoodsResult;
import com.zantong.mobile.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobile.home.bean.BannerResult;
import com.zantong.mobile.home.bean.DriverCoachResult;
import com.zantong.mobile.home.bean.HomeCarResult;
import com.zantong.mobile.home.bean.HomeResult;
import com.zantong.mobile.home.bean.ModuleResult;
import com.zantong.mobile.home.bean.StartPicResult;
import com.zantong.mobile.home.dto.HomeDataDTO;
import com.zantong.mobile.map.bean.GasStationDetailResult;
import com.zantong.mobile.map.bean.GasStationResult;
import com.zantong.mobile.map.bean.YearCheckDetailResult;
import com.zantong.mobile.map.bean.YearCheckResult;
import com.zantong.mobile.map.dto.AnnualDTO;
import com.zantong.mobile.order.bean.CouponFragmentResult;
import com.zantong.mobile.order.bean.MessageResult;
import com.zantong.mobile.order.bean.OrderDetailResult;
import com.zantong.mobile.order.bean.OrderExpressResult;
import com.zantong.mobile.order.bean.ReceiveInfoResult;
import com.zantong.mobile.order.dto.ExpressDTO;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.user.bean.MessageCountResult;
import com.zantong.mobile.user.bean.MessageDetailResult;
import com.zantong.mobile.user.bean.MessageTypeResult;
import com.zantong.mobile.user.bean.UserCarsResult;
import com.zantong.mobile.user.dto.MegDTO;
import com.zantong.mobile.user.dto.MessageDetailDTO;
import com.zantong.mobile.weizhang.bean.LicenseResponseBean;
import com.zantong.mobile.weizhang.bean.PayOrderResult;
import com.zantong.mobile.weizhang.bean.ViolationNum;
import com.zantong.mobile.weizhang.bean.ViolationNumBean;
import com.zantong.mobile.weizhang.bean.ViolationResultParent;
import com.zantong.mobile.weizhang.dto.ViolationCarDTO;
import com.zantong.mobile.weizhang.dto.ViolationPayDTO;

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

    private Retrofit initRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(1);
    }

    private Retrofit initAppUrlRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(2);
    }

    private Retrofit initBaseUrlRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(3);
    }

    private Retrofit initTestRetrofit(int type) {
        return RetrofitFactory.getInstance().createRetrofit(type);
    }

    private Retrofit initImageRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(5);
    }

    /**
     * 消息模块
     */
    @Override
    public Observable<MessageTypeResult> messageFindAll(BaseDTO bean) {
        return initRetrofit().create(IMessageService.class).messageFindAll(bean);
    }

    @Override
    public Observable<MessageResult> findMessageDetailByMessageId(MegDTO bean) {
        return initRetrofit().create(IMessageService.class).findMessageDetailByMessageId(bean);
    }

    @Override
    public Observable<MessageDetailResult> findMessageDetail(MessageDetailDTO bean) {
        return initRetrofit().create(IMessageService.class).findMessageDetail(bean);
    }

    /**
     * 2.4.24删除消息
     */
    @Override
    public Observable<MessageResult> deleteMessageDetail(MegDTO megDTO) {
        return initRetrofit().create(IMessageService.class).deleteMessageDetail(megDTO);
    }

    /**
     * 优惠劵模块
     */
    @Override
    public Observable<CouponFragmentResult> usrCouponInfo(String usrnum, String couponStatus) {
        return initRetrofit().create(IFebruaryService.class).usrCouponInfo(usrnum, couponStatus);
    }

    /**
     * 2.4.27删除用户优惠券
     */
    @Override
    public Observable<MessageResult> delUsrCoupon(String couponId, String userId) {
        return initRetrofit().create(IFebruaryService.class).delUsrCoupon(couponId, userId);
    }

    /**
     * 查分
     *
     * @param requestDTO
     * @return
     */
    @Override
    public Observable<LicenseResponseBean> driverLicenseCheckGrade(String requestDTO) {
        return initAppUrlRetrofit().create(IBankService.class).driverLicenseCheckGrade(requestDTO);
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
        return initAppUrlRetrofit().create(CTTXHttpPOSTInterface.class).loadPost(msg);
    }

    /**
     * 40.app启动图片获取
     *
     * @param msg 图片类型(0主页图片，1其余图片)
     * @return
     */
    @Override
    public Observable<StartPicResult> startGetPic(String msg) {
        return initRetrofit().create(ISplashService.class).startGetPic(msg);
    }

    /**
     * 57.获取指定类型优惠券
     */
    @Override
    public Observable<RechargeCouponResult> getCouponByType(String userId, String type) {
        return initRetrofit().create(IFebruaryService.class).getConponByType(userId, type);
    }

    /**
     * 10.创建加油订单
     */
    @Override
    public Observable<RechargeResult> addOilCreateOrder(RechargeDTO rechargeDTO) {
        return initRetrofit().create(IAddOilService.class).addOilCreateOrder(rechargeDTO);
    }

    /**
     * 54.充值接口
     */
    @Override
    public Observable<PayOrderResult> onPayOrderByCoupon(String payUrl, String orderPrice, String payType) {
        return initRetrofit().create(IAddOilService.class).onPayOrderByCoupon(payUrl, orderPrice, payType);
    }

    /**
     * 43.生成违章缴费订单
     */
    @Override
    public Observable<PayOrderResult> paymentCreateOrder(ViolationPayDTO payDTO) {
        return initRetrofit().create(IViolationService.class).paymentCreateOrder(payDTO);
    }

    @Override
    public Observable<LicenseResponseBean> loadLoginPostTest(String msg) {
        return initAppUrlRetrofit().create(IBankService.class).loadLoginPostTest(msg);
    }

    /**
     * 1.首页信息
     */
    @Override
    public Observable<HomeResult> homePage(HomeDataDTO id) {
        return initRetrofit().create(ICttxService.class).homePage(id);
    }

    /**
     * cip.cfc.c003.01
     */
    @Override
    public Observable<UserCarsResult> getRemoteCarInfo(String requestDTO) {
        return initAppUrlRetrofit().create(IBankService.class).getRemoteCarInfo(requestDTO);
    }

    /**
     * 37.获取所有未读消息数量
     */
    @Override
    public Observable<MessageCountResult> countMessageDetail(BaseDTO baseDTO) {
        return initRetrofit().create(IMessageService.class).countMessageDetail(baseDTO);
    }

    /**
     * 58.获取banner图片
     */
    @Override
    public Observable<BannerResult> getBanner(String type) {
        return initRetrofit().create(IBannerService.class).getBanner(type);
    }

    /**
     * 55.行驶证扫描接口
     */
    @Override
    public Observable<DrivingOcrResult> uploadDrivingImg(MultipartBody.Part part) {
        return initImageRetrofit().create(IDrivingImageService.class).uploadDrivingImg(part);
    }

    /**
     * 3.获取商户区域列表
     */
    @Override
    public Observable<MerchantAresResult> getMerchantArea() {
        return initRetrofit().create(IGoodsService.class).getMerchantArea();
    }

    /**
     * 4.获取区域商品列表
     */
    @Override
    public Observable<AresGoodsResult> getAreaGoods(int areaCode) {
        return initRetrofit().create(IGoodsService.class).getAreaGoods(areaCode);
    }

    /**
     * 2.创建订单
     */
    @Override
    public Observable<CreateOrderResult> createOrder(CreateOrderDTO createOrder) {
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

        return initRetrofit().create(IGoodsService.class).createOrder(options);
    }

    /**
     * 7.获取用户指定活动的统计总数
     */
    @Override
    public Observable<RecordCountResult> getRecordCount(String type, String phone) {
        return initRetrofit().create(IFebruaryService.class).getRecordCount(type, phone);
    }

    /**
     * cip.cfc.u005.01
     */
    @Override
    public Observable<Result> commitCarInfoToOldServer(String msg) {
        return initAppUrlRetrofit().create(IBankService.class).commitCarInfoToOldServer(msg);
    }

    /**
     * 48.绑定行驶证接口
     */
    @Override
    public Observable<BaseResult> commitCarInfoToNewServer(BindCarDTO bindCarDTO) {
        return initRetrofit().create(ICttxService.class).commitCarInfoToNewServer(bindCarDTO);
    }

    /**
     * 5.获取工行支付页面
     */
    @Override
    public Observable<PayOrderResult> getBankPayHtml(String orderId, String orderPrice) {
        return initRetrofit().create(IPayService.class).getBankPayHtml(orderId, orderPrice);
    }

    /**
     * 8.查询订单列表
     */
    @Override
    public Observable<OrderListResult> getOrderList(String userId) {
        return initRetrofit().create(IOrderService.class).getOrderList(userId);
    }

    /**
     * 9.获取订单详情
     */
    @Override
    public Observable<OrderDetailResult> getOrderDetail(String orderId) {
        return initRetrofit().create(IOrderService.class).getOrderDetail(orderId);
    }

    /**
     * 10.更新订单状态
     */
    @Override
    public Observable<BaseResult> updateOrderStatus(String orderId, int orderStatus) {
        return initRetrofit().create(IOrderService.class).updateOrderStatus(orderId, String.valueOf(orderStatus));
    }

    /**
     * 6.获取商品详情
     */
    @Override
    public Observable<GoodsDetailResult> getGoodsDetail(String goodsId) {
        return initRetrofit().create(IGoodsService.class).getGoodsDetail(goodsId);
    }

    /**
     * 获取违章信息
     */
    @Override
    public Observable<HomeCarResult> getTextNoticeInfo(String defaultUserID) {
        return initRetrofit().create(ITextService.class).getTextNoticeInfo(defaultUserID);
    }

    /**
     * 处理违章信息
     */
    @Override
    public Observable<BaseResult> handleViolations(ViolationCarDTO violationResult) {
        return initRetrofit().create(ITextService.class).HandleViolationDTO(violationResult);
    }

    /**
     * 车辆违章查询
     * cip.cfc.v002.01
     */
    @Override
    public Observable<ViolationResultParent> searchViolation(String msg) {
        return initAppUrlRetrofit().create(IBankService.class).searchViolation(msg);
    }

    /**
     * cip.cfc.c002.01
     */
    @Override
    public Observable<PayCarResult> getPayCars(String msg) {
        return initAppUrlRetrofit().create(IBankService.class).getPayCars(msg);
    }

    /**
     * 19.同步银行车辆
     */
    @Override
    public Observable<VehicleLicenseResult> addOrUpdateVehicleLicense(List<BindCarDTO> dtoList) {
        return initRetrofit().create(ICttxService.class).addOrUpdateVehicleLicense(dtoList);
    }

    /**
     * 13.判断是否为司机
     */
    @Override
    public Observable<DriverCoachResult> getDriverCoach(String phone) {
        return initRetrofit().create(IUserService.class).getDriverCoach(phone);
    }

    /**
     * 16.新增车辆
     */
    @Override
    public Observable<BaseResult> addVehicleLicense(BindCarDTO bindCarDTO) {
        return initRetrofit().create(ICttxService.class).addVehicleLicense(bindCarDTO);
    }

    /**
     * 18.删除车辆
     */
    @Override
    public Observable<BaseResult> removeVehicleLicense(BindCarDTO bindCarDTO) {
        return initRetrofit().create(ICttxService.class).removeVehicleLicense(bindCarDTO);
    }

    /**
     * 17.编辑车辆
     */
    @Override
    public Observable<BaseResult> updateVehicleLicense(BindCarDTO bindCarDTO) {
        return initRetrofit().create(ICttxService.class).updateVehicleLicense(bindCarDTO);
    }

    /**
     * 22.获取商品
     */
    @Override
    public Observable<SubjectGoodsResult> getGoods(String type) {
        return initRetrofit().create(IGoodsService.class).getGoods(type);
    }

    @Override
    public Observable<SparringGoodsResult> getGoodsFive(String type) {
        return initRetrofit().create(IGoodsService.class).getGoodsFive(type);
    }

    /**
     * 20.新手陪练获取服务地区
     */
    @Override
    public Observable<SparringAreaResult> getServiceArea() {
        return initRetrofit().create(IDriverTrainService.class).getServiceArea();
    }

    /**
     * 21.获取服务器时间
     */
    @Override
    public Observable<ServerTimeResult> getServerTime() {
        return initRetrofit().create(IDriverTrainService.class).getServerTime();
    }

    /**
     * 10.取消订单
     */
    @Override
    public Observable<BaseResult> cancelOrder(String orderId, String userNum) {
        return initRetrofit().create(IOrderService.class).cancelOrder(orderId, userNum);
    }

    /**
     * 24.获取年检网点
     */
    @Override
    public Observable<YearCheckResult> annualInspectionList(AnnualDTO annualDTO) {
        return initRetrofit().create(ICttxService.class).annualInspectionList(annualDTO);
    }

    /**
     * 获取年检一条信息
     */
    @Override
    public Observable<YearCheckDetailResult> annualInspection(int id) {
        return initRetrofit().create(ICttxService.class).annualInspection(id);
    }

    /**
     * 获得加油站地点详情
     */
    @Override
    public Observable<GasStationDetailResult> gasStation(int id) {
        return initRetrofit().create(ICttxService.class).gasStation(id);
    }

    /**
     * 23.获取加油网点
     */
    @Override
    public Observable<GasStationResult> gasStationList(AnnualDTO annualDTO) {
        return initRetrofit().create(ICttxService.class).gasStationList(annualDTO);
    }

    /**
     * 25.模块化接口
     */
    @Override
    public Observable<ModuleResult> moduleTree() {

        return initRetrofit().create(IModuleService.class).moduleTree();
    }

    /**
     * 32.获取地区列表
     */
    @Override
    public Observable<OrderExpressResult> getAllAreas() {
        return initRetrofit().create(IRegionService.class).getAllAreas();
    }

    /**
     * 29.填写快递信息
     */
    @Override
    public Observable<BaseResult> addExpressInfo(ExpressDTO expressDTO) {
        return initRetrofit().create(IOrderService.class).addExpressInfo(expressDTO);
    }

    /**
     * 33.获取收件人信息
     */
    @Override
    public Observable<ReceiveInfoResult> getReceiveInfo(String orderId) {
        return initRetrofit().create(IOrderService.class).getReceiveInfo(orderId);
    }

    /**
     * 获取订单列表
     */
    @Override
    public Observable<CattleOrderResponse> queryOrderList() {
        return initRetrofit().create(IOrderService.class).queryOrderList();
    }

    /**
     * 8.用户注册修改接口
     */
    @Override
    public Observable<BaseResult> register(RegisterDTO registerDTO) {
        return initRetrofit().create(ICttxService.class).register(registerDTO);
    }

    @Override
    public Observable<Result> loginV004(String msg) {
        return initAppUrlRetrofit().create(IBankService.class).loginV004(msg);
    }

    /**
     * 订单
     */
    @Override
    public Observable<OrderListResult> getAnnualInspectionOrders(String id) {
        return initRetrofit().create(IOrderService.class).getAnnualInspectionOrders(id);
    }

    /**
     * 资料审核中
     */
    @Override
    public Observable<BaseResult> getAnnualInspectionOrderTargetState(String orderId, String status, String remark, String userNum) {
        return initRetrofit().create(IOrderService.class).getAnnualInspectionOrderTargetState(orderId, status, remark, userNum);
    }

    /**
     * 输入快递单号
     */
    @Override
    public Observable<BaseResult> addBackExpressInfo(String orderId, String expressNo, String userNum) {
        return initRetrofit().create(IOrderService.class).addBackExpressInfo(orderId, expressNo, userNum);
    }

    /**
     * 46.更新违章缴费状态
     */
    @Override
    public Observable<BaseResult> updateState(List<ViolationNum> json) {
        return initRetrofit().create(IPayService.class).updateState(json);
    }

    @Override
    public Observable<ViolationNumBean> numberedQuery(String msg) {
        return initAppUrlRetrofit().create(IBankService.class).numberedQuery(msg);
    }

    /**
     * 登录
     */
    @Override
    public Observable<BaseResponse> innerUserLogin(String userPhone, String userPassword) {
        return initRetrofit().create(IUserService.class).innerUserLogin(userPhone, userPassword);
    }

    /**
     * 获取内部通知
     */
    @Override
    public Observable<AnnouncementResult> findAnnouncements() {
        return initRetrofit().create(IUserService.class).findAnnouncements();
    }

    /**
     * 分享  新
     */
    @Override
    public Observable<StatistCountResult> getStatisticsCount(String phone) {
        return initRetrofit().create(IUserService.class).getStatisticsCount(phone);
    }
}
