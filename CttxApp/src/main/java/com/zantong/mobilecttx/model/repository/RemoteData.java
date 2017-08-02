package com.zantong.mobilecttx.model.repository;

import com.zantong.mobilecttx.api.IAddOilService;
import com.zantong.mobilecttx.api.IBankService;
import com.zantong.mobilecttx.api.IBannerService;
import com.zantong.mobilecttx.api.ICttxService;
import com.zantong.mobilecttx.api.IDriverTrainService;
import com.zantong.mobilecttx.api.IDrivingImageService;
import com.zantong.mobilecttx.api.IFebruaryService;
import com.zantong.mobilecttx.api.IGoodsService;
import com.zantong.mobilecttx.api.IMessageService;
import com.zantong.mobilecttx.api.IOrderService;
import com.zantong.mobilecttx.api.IPayService;
import com.zantong.mobilecttx.api.ISplashService;
import com.zantong.mobilecttx.api.ITextService;
import com.zantong.mobilecttx.api.IUserService;
import com.zantong.mobilecttx.api.IViolationService;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.bean.VehicleLicenseResult;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.contract.CTTXHttpPOSTInterface;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.bean.GoodsDetailResult;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResult;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResult;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResult;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.SubjectGoodsResult;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.home.bean.BannerResult;
import com.zantong.mobilecttx.home.bean.DriverCoachResult;
import com.zantong.mobilecttx.home.bean.HomeCarResult;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.home.bean.StartPicResult;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.order.bean.CouponFragmentResult;
import com.zantong.mobilecttx.order.bean.MessageResult;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;
import com.zantong.mobilecttx.order.bean.OrderListResult;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.user.bean.MessageDetailResult;
import com.zantong.mobilecttx.user.bean.MessageTypeResult;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

import java.util.List;

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
        return initRetrofit().create(IGoodsService.class).createOrder(
                createOrder.getType(), createOrder.getUserNum(),
                createOrder.getGoodsId(), createOrder.getPrice(),
                createOrder.getUserName(), createOrder.getPhone(),
                createOrder.getIdCard(), createOrder.getPayType());
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
}
