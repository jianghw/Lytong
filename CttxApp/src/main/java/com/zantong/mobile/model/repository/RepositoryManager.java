package com.zantong.mobile.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.response.CattleOrderResponse;
import com.tzly.annual.base.bean.response.SubjectGoodsResult;
import com.zantong.mobile.base.bean.Result;
import com.zantong.mobile.base.dto.BaseDTO;
import com.zantong.mobile.base.dto.RequestHeadDTO;
import com.zantong.mobile.car.bean.PayCarResult;
import com.zantong.mobile.car.bean.VehicleLicenseResult;
import com.zantong.mobile.card.dto.BindCarDTO;
import com.zantong.mobile.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobile.chongzhi.bean.RechargeResult;
import com.zantong.mobile.chongzhi.dto.RechargeDTO;
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
import com.zantong.mobile.order.bean.OrderListResult;
import com.zantong.mobile.order.bean.ReceiveInfoResult;
import com.zantong.mobile.order.dto.ExpressDTO;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.user.bean.MessageCountResult;
import com.zantong.mobile.user.bean.MessageDetailResult;
import com.zantong.mobile.user.bean.MessageTypeResult;
import com.zantong.mobile.user.bean.RspInfoBean;
import com.zantong.mobile.user.bean.UserCarsResult;
import com.zantong.mobile.user.dto.MegDTO;
import com.zantong.mobile.user.dto.MessageDetailDTO;
import com.zantong.mobile.weizhang.bean.LicenseResponseBean;
import com.zantong.mobile.weizhang.bean.PayOrderResult;
import com.zantong.mobile.weizhang.bean.ViolationResultParent;
import com.zantong.mobile.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobile.weizhang.dto.ViolationCarDTO;
import com.zantong.mobile.weizhang.dto.ViolationPayDTO;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 仓库管理类
 */

public class RepositoryManager {
    @Nullable
    private static RepositoryManager INSTANCE = null;
    @NonNull
    private final RemoteData mRemoteData;
    @NonNull
    private final LocalData mLocalData;

    public static RepositoryManager getInstance(RemoteData remoteData, LocalData localData) {
        if (INSTANCE == null) {
            INSTANCE = new RepositoryManager(remoteData, localData);
        }
        return INSTANCE;
    }

    private RepositoryManager(@NonNull RemoteData remoteData, @NonNull LocalData localData) {
        mRemoteData = remoteData;
        mLocalData = localData;
    }

    /**
     * 2.4.20.1获取消息类别列表
     *
     * @param bean
     * @return
     */
    public Observable<MessageTypeResult> messageFindAll(BaseDTO bean) {
        return mRemoteData.messageFindAll(bean);
    }

    public BaseDTO initBaseDTO() {
        return mLocalData.initBaseDTO();
    }

    public MegDTO initMegDTO() {
        MegDTO megDTO = new MegDTO();
        megDTO.setUsrId(mLocalData.getRASUserID());
        return megDTO;
    }

    /**
     * 获取用户id~
     *
     * @return
     */
    public String getDefaultUserID() {
        return mLocalData.DefaultUserID();
    }

    /**
     * 加密后的userid
     */
    public String getDefaultRASUserID() {
        return mLocalData.getRASUserID();
    }

    /**
     * 这段加密封装
     *
     * @param string
     * @return
     */
    public String getStrByEncryption(String string) {
        return mLocalData.getStrByEncryption(string);
    }

    /**
     * 手机号码
     */
    public String getDefaultUserPhone() {
        return mLocalData.getDefaultUser() != null
                ? mLocalData.getDefaultUser().getPhoenum() : "";
    }

    /**
     * 2.4.21获取消息详情列表
     *
     * @param bean
     * @return
     */
    public Observable<MessageResult> findMessageDetailByMessageId(MegDTO bean) {
        return mRemoteData.findMessageDetailByMessageId(bean);
    }

    public MessageDetailDTO initMessageDetailDTO() {
        return mLocalData.initMessageDetailDTO();
    }

    public Observable<MessageDetailResult> findMessageDetail(MessageDetailDTO bean) {
        return mRemoteData.findMessageDetail(bean);
    }

    /**
     * 2.4.2查看优惠券信息
     */
    public Observable<CouponFragmentResult> usrCouponInfo(String usrnum, String couponStatus) {
        return mRemoteData.usrCouponInfo(usrnum, couponStatus);
    }

    /**
     * urlCode 构造器
     *
     * @param urlCode cip.cfc.v001.01
     */
    public RequestHeadDTO initLicenseFileNumDTO(String urlCode) {
        RequestHeadDTO dto = mLocalData.initRequestHeadDTO();
        dto.setTransServiceCode(urlCode);
        return dto;
    }

    /**
     * 驾驶证查分 cip.cfc.v001.01
     */
    public Observable<LicenseResponseBean> driverLicenseCheckGrade(String requestDTO) {
        return mRemoteData.driverLicenseCheckGrade(requestDTO);
    }

    /**
     * 2.4.24删除消息
     *
     * @param megDTO
     * @return
     */
    public Observable<MessageResult> deleteMessageDetail(MegDTO megDTO) {
        return mRemoteData.deleteMessageDetail(megDTO);
    }

    public String initDelUsrCouponDTODTO() {
        return mLocalData.getRASUserID();
    }

    /**
     * 2.4.27删除用户优惠券
     */
    public Observable<MessageResult> delUsrCoupon(String couponId, String userId) {
        return mRemoteData.delUsrCoupon(couponId, userId);
    }

    public void saveLicenseFileNumDTO(LicenseFileNumDTO licenseFileNumDTO) {
        mLocalData.saveLicenseFileNumDTO(licenseFileNumDTO);
    }

    /**
     * 获取登录信息
     */
    public RspInfoBean readObjectLoginInfoBean() {
        return mLocalData.readObjectLoginInfoBean();
    }

    /**
     * 初始化用户登录信息
     *
     * @param rspInfoBean
     */
    public void initGlobalLoginInfo(RspInfoBean rspInfoBean) {
        mLocalData.initGlobalLoginInfo(rspInfoBean);
    }

    /**
     * 安盛登录接口
     *
     * @param msg
     * @return
     */
    public Observable<LoginInfoBean> loadLoginPost(String msg) {
        return mRemoteData.loadLoginPost(msg);
    }

    /**
     * 获取登录密码
     *
     * @return
     */
    public String readLoginPassword() {
        return mLocalData.readLoginPassword();
    }

    public void saveLoginInfoRepeat(LoginInfoBean result) {
        mLocalData.saveLoginInfoRepeat(result);
    }

    /**
     * 40.app启动图片获取
     *
     * @param msg
     * @return
     */
    public Observable<StartPicResult> startGetPic(String msg) {
        return mRemoteData.startGetPic(msg);
    }

    /**
     * 57.获取指定类型优惠券
     */
    public Observable<RechargeCouponResult> getCouponByType(String userId, String type) {
        return mRemoteData.getCouponByType(userId, type);
    }

    /**
     * 10.创建加油订单
     */
    public Observable<RechargeResult> addOilCreateOrder(RechargeDTO rechargeDTO) {
        return mRemoteData.addOilCreateOrder(rechargeDTO);
    }

    /**
     * 54.充值接口
     */
    public Observable<PayOrderResult> onPayOrderByCoupon(String payUrl, String orderPrice, String payType) {
        return mRemoteData.onPayOrderByCoupon(payUrl, orderPrice, payType);
    }

    /**
     * 43.生成违章缴费订单
     */
    public Observable<PayOrderResult> paymentCreateOrder(ViolationPayDTO payDTO) {
        return mRemoteData.paymentCreateOrder(payDTO);
    }

    public Observable<LicenseResponseBean> loadLoginPostTest(String msg) {
        return mRemoteData.loadLoginPostTest(msg);
    }

    /**
     * 1.首页信息
     */
    public Observable<HomeResult> homePage(HomeDataDTO id) {
        return mRemoteData.homePage(id);
    }

    /**
     * cip.cfc.c003.01
     */
    public Observable<UserCarsResult> getRemoteCarInfo(String requestDTO) {
        return mRemoteData.getRemoteCarInfo(requestDTO);
    }

    /**
     * 37.获取所有未读消息数量
     */
    public Observable<MessageCountResult> countMessageDetail(BaseDTO baseDTO) {
        return mRemoteData.countMessageDetail(baseDTO);
    }

    /**
     * 58.获取banner图片
     */
    public Observable<BannerResult> getBanner(String type) {
        return mRemoteData.getBanner(type);
    }

    /**
     * 55.行驶证扫描接口
     */
    public Observable<DrivingOcrResult> uploadDrivingImg(MultipartBody.Part part) {
        return mRemoteData.uploadDrivingImg(part);
    }

    /**
     * 3.获取商户区域列表
     */
    public Observable<MerchantAresResult> getMerchantArea() {
        return mRemoteData.getMerchantArea();
    }

    /**
     * 4.获取区域商品列表
     */
    public Observable<AresGoodsResult> getAreaGoods(int areaCode) {
        return mRemoteData.getAreaGoods(areaCode);
    }

    /**
     * 2.创建订单
     */
    public Observable<CreateOrderResult> createOrder(CreateOrderDTO createOrder) {
        return mRemoteData.createOrder(createOrder);
    }

    /**
     * 7.获取用户指定活动的统计总数
     */
    public Observable<RecordCountResult> getRecordCount(String type, String phone) {
        return mRemoteData.getRecordCount(type, phone);
    }

    /**
     * cip.cfc.u005.01
     */
    public Observable<Result> commitCarInfoToOldServer(String msg) {
        return mRemoteData.commitCarInfoToOldServer(msg);
    }

    /**
     * 48.绑定行驶证接口
     */
    public Observable<BaseResult> commitCarInfoToNewServer(BindCarDTO bindCarDTO) {
        return mRemoteData.commitCarInfoToNewServer(bindCarDTO);
    }

    /**
     * N 5.获取工行支付页面
     */
    public Observable<PayOrderResult> getBankPayHtml(String orderId, String orderPrice) {
        return mRemoteData.getBankPayHtml(orderId, orderPrice);
    }

    /**
     * 8.查询订单列表
     *
     * @param userId
     */
    public Observable<OrderListResult> getOrderList(String userId) {
        return mRemoteData.getOrderList(userId);
    }

    /**
     * 9.获取订单详情
     */
    public Observable<OrderDetailResult> getOrderDetail(String orderId) {
        return mRemoteData.getOrderDetail(orderId);
    }

    /**
     * 10.更新订单状态 N
     */
    public Observable<BaseResult> updateOrderStatus(String orderId, int orderStatus) {
        return mRemoteData.updateOrderStatus(orderId, orderStatus);
    }

    /**
     * 6.获取商品详情
     */
    public Observable<GoodsDetailResult> getGoodsDetail(String goodsId) {
        return mRemoteData.getGoodsDetail(goodsId);
    }

    /**
     * 新获取违章信息
     */
    public Observable<HomeCarResult> getTextNoticeInfo(String defaultUserID) {
        return mRemoteData.getTextNoticeInfo(defaultUserID);
    }

    /**
     * 处理违章信息
     */
    public Observable<BaseResult> handleViolations(ViolationCarDTO violationResult) {
        return mRemoteData.handleViolations(violationResult);
    }

    /**
     * 车辆违章查询
     * cip.cfc.v002.01
     */
    public Observable<ViolationResultParent> searchViolation(String msg) {
        return mRemoteData.searchViolation(msg);
    }

    /**
     * cip.cfc.c002.01
     */
    public Observable<PayCarResult> getPayCars(String msg) {
        return mRemoteData.getPayCars(msg);
    }

    /**
     * 19.同步银行车辆
     */
    public Observable<VehicleLicenseResult> addOrUpdateVehicleLicense(List<BindCarDTO> dtoList) {
        return mRemoteData.addOrUpdateVehicleLicense(dtoList);
    }

    /**
     * 13.判断是否为司机
     */
    public Observable<DriverCoachResult> getDriverCoach(String phone) {
        return mRemoteData.getDriverCoach(phone);
    }

    /**
     * 17.新增车辆
     */
    public Observable<BaseResult> addVehicleLicense(BindCarDTO bindCarDTO) {
        return mRemoteData.addVehicleLicense(bindCarDTO);
    }

    /**
     * 18.删除车辆
     */
    public Observable<BaseResult> removeVehicleLicense(BindCarDTO bindCarDTO) {
        return mRemoteData.removeVehicleLicense(bindCarDTO);
    }

    /**
     * 17.编辑车辆
     */
    public Observable<BaseResult> updateVehicleLicense(BindCarDTO bindCarDTO) {
        return mRemoteData.updateVehicleLicense(bindCarDTO);
    }

    /**
     * 22.获取商品
     */
    public Observable<SubjectGoodsResult> getGoods(String type) {
        return mRemoteData.getGoods(type);
    }

    /**
     * 22.获取商品
     */
    public Observable<SparringGoodsResult> getGoodsFive(String type) {
        return mRemoteData.getGoodsFive(type);
    }

    /**
     * 20.新手陪练获取服务地区
     */
    public Observable<SparringAreaResult> getServiceArea() {
        return mRemoteData.getServiceArea();
    }

    /**
     * 21.获取服务器时间
     */
    public Observable<ServerTimeResult> getServerTime() {
        return mRemoteData.getServerTime();
    }

    /**
     * 10.取消订单
     */
    public Observable<BaseResult> cancelOrder(String orderId, String userNum) {
        return mRemoteData.cancelOrder(orderId, userNum);
    }

    /**
     * 24.获取年检网点
     */
    public Observable<YearCheckResult> annualInspectionList(AnnualDTO annualDTO) {
        return mRemoteData.annualInspectionList(annualDTO);
    }

    /**
     * 获取年检一条信息
     */
    public Observable<YearCheckDetailResult> annualInspection(int id) {
        return mRemoteData.annualInspection(id);
    }

    /**
     * 获得加油站地点详情
     */
    public Observable<GasStationDetailResult> gasStation(int id) {
        return mRemoteData.gasStation(id);
    }

    /**
     * 23.获取加油网点
     */
    public Observable<GasStationResult> gasStationList(AnnualDTO annualDTO) {
        return mRemoteData.gasStationList(annualDTO);
    }

    /**
     * 25.模块化接口
     */
    public Observable<ModuleResult> moduleTree() {
        return mRemoteData.moduleTree();
    }

    /**
     * 32.获取地区列表
     */
    public Observable<OrderExpressResult> getAllAreas() {
        return mRemoteData.getAllAreas();
    }

    /**
     * 29.填写快递信息
     */
    public Observable<BaseResult> addExpressInfo(ExpressDTO expressDTO) {
        return mRemoteData.addExpressInfo(expressDTO);
    }

    /**
     * 33.获取收件人信息
     */
    public Observable<ReceiveInfoResult> getReceiveInfo(String orderId) {
        return mRemoteData.getReceiveInfo(orderId);
    }

    /**
     * 2 获取订单列表
     */
    public Observable<CattleOrderResponse> queryOrderList() {
        return mRemoteData.queryOrderList();
    }
}