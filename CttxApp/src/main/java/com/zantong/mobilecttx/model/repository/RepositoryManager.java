package com.zantong.mobilecttx.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import cn.qqtheme.framework.bean.BankResponse;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
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
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.home.bean.BannerResponse;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.ModuleResponse;
import com.zantong.mobilecttx.home.bean.StartPicResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
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
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;
import com.zantong.mobilecttx.weizhang.bean.ViolationNum;
import com.zantong.mobilecttx.weizhang.bean.ViolationNumBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.bean.response.SubjectGoodsResponse;
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
    public Observable<MessageTypeResponse> messageFindAll(BaseDTO bean) {
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
    public Observable<MessageResponse> findMessageDetailByMessageId(MegDTO bean) {
        return mRemoteData.findMessageDetailByMessageId(bean);
    }

    public MessageDetailDTO initMessageDetailDTO() {
        return mLocalData.initMessageDetailDTO();
    }

    public Observable<MessageDetailResponse> findMessageDetail(MessageDetailDTO bean) {
        return mRemoteData.findMessageDetail(bean);
    }

    /**
     * 2.4.2查看优惠券信息
     */
    public Observable<CouponFragmentResponse> usrCouponInfo(String usrnum, String couponStatus) {
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
    public Observable<MessageResponse> deleteMessageDetail(MegDTO megDTO) {
        return mRemoteData.deleteMessageDetail(megDTO);
    }

    public String initDelUsrCouponDTODTO() {
        return mLocalData.getRASUserID();
    }

    /**
     * 2.4.27删除用户优惠券
     */
    public Observable<MessageResponse> delUsrCoupon(String couponId, String userId) {
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
    public Observable<StartPicResponse> startGetPic(String msg) {
        return mRemoteData.startGetPic(msg);
    }

    /**
     * 57.获取指定类型优惠券
     */
    public Observable<RechargeCouponResponse> getCouponByType(String userId, String type) {
        return mRemoteData.getCouponByType(userId, type);
    }

    /**
     * 10.创建加油订单
     */
    public Observable<RechargeResponse> addOilCreateOrder(RechargeDTO rechargeDTO) {
        return mRemoteData.addOilCreateOrder(rechargeDTO);
    }

    /**
     * 54.充值接口
     */
    public Observable<PayOrderResponse> onPayOrderByCoupon(String payUrl, String orderPrice, String payType) {
        return mRemoteData.onPayOrderByCoupon(payUrl, orderPrice, payType);
    }

    /**
     * 43.生成违章缴费订单
     */
    public Observable<PayOrderResponse> paymentCreateOrder(ViolationPayDTO payDTO) {
        return mRemoteData.paymentCreateOrder(payDTO);
    }

    /**
     * cip.cfc.v003.01
     */
    public Observable<ViolationNumBean> numberedQuery(String msg) {
        return mRemoteData.numberedQuery(msg);
    }

    /**
     * 1.首页信息
     */
    public Observable<HomeResponse> homePage(HomeDataDTO id) {
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
    public Observable<MessageCountResponse> countMessageDetail(BaseDTO baseDTO) {
        return mRemoteData.countMessageDetail(baseDTO);
    }

    /**
     * 58.获取banner图片
     */
    public Observable<BannerResponse> getBanner(String type) {
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
    public Observable<MerchantAresResponse> getMerchantArea() {
        return mRemoteData.getMerchantArea();
    }

    /**
     * 4.获取区域商品列表
     */
    public Observable<AresGoodsResponse> getAreaGoods(int areaCode) {
        return mRemoteData.getAreaGoods(areaCode);
    }

    /**
     * 2.创建订单
     */
    public Observable<CreateOrderResponse> createOrder(CreateOrderDTO createOrder) {
        return mRemoteData.createOrder(createOrder);
    }

    /**
     * 7.获取用户指定活动的统计总数
     */
    public Observable<RecordCountResponse> getRecordCount(String type, String phone) {
        return mRemoteData.getRecordCount(type, phone);
    }

    /**
     * cip.cfc.u005.01
     */
    public Observable<BankResponse> commitCarInfoToOldServer(String msg) {
        return mRemoteData.commitCarInfoToOldServer(msg);
    }

    /**
     * 48.绑定行驶证接口
     */
    public Observable<BaseResponse> commitCarInfoToNewServer(BindCarDTO bindCarDTO) {
        return mRemoteData.commitCarInfoToNewServer(bindCarDTO);
    }

    /**
     * N 5.获取工行支付页面
     */
    public Observable<PayOrderResponse> getBankPayHtml(String orderId, String orderPrice) {
        return mRemoteData.getBankPayHtml(orderId, orderPrice);
    }

    /**
     * 8.查询订单列表
     *
     * @param userId
     */
    public Observable<OrderListResponse> getOrderList(String userId) {
        return mRemoteData.getOrderList(userId);
    }

    /**
     * 9.获取订单详情
     */
    public Observable<OrderDetailResponse> getOrderDetail(String orderId) {
        return mRemoteData.getOrderDetail(orderId);
    }

    /**
     * 10.更新订单状态 N
     */
    public Observable<BaseResponse> updateOrderStatus(String orderId, int orderStatus) {
        return mRemoteData.updateOrderStatus(orderId, orderStatus);
    }

    /**
     * 6.获取商品详情
     */
    public Observable<GoodsDetailResponse> getGoodsDetail(String goodsId) {
        return mRemoteData.getGoodsDetail(goodsId);
    }

    /**
     * 新获取违章信息
     */
    public Observable<HomeCarResponse> getTextNoticeInfo(String defaultUserID) {
        return mRemoteData.getTextNoticeInfo(defaultUserID);
    }

    /**
     * 处理违章信息
     */
    public Observable<BaseResponse> handleViolations(ViolationCarDTO violationResult) {
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
    public Observable<VehicleLicenseResponse> addOrUpdateVehicleLicense(List<BindCarDTO> dtoList) {
        return mRemoteData.addOrUpdateVehicleLicense(dtoList);
    }

    /**
     * 13.判断是否为司机
     */
    public Observable<DriverCoachResponse> getDriverCoach(String phone) {
        return mRemoteData.getDriverCoach(phone);
    }

    /**
     * 17.新增车辆
     */
    public Observable<BaseResponse> addVehicleLicense(BindCarDTO bindCarDTO) {
        return mRemoteData.addVehicleLicense(bindCarDTO);
    }

    /**
     * 18.删除车辆
     */
    public Observable<BaseResponse> removeVehicleLicense(BindCarDTO bindCarDTO) {
        return mRemoteData.removeVehicleLicense(bindCarDTO);
    }

    /**
     * 17.编辑车辆
     */
    public Observable<BaseResponse> updateVehicleLicense(BindCarDTO bindCarDTO) {
        return mRemoteData.updateVehicleLicense(bindCarDTO);
    }

    /**
     * 22.获取商品
     */
    public Observable<SubjectGoodsResponse> getGoods(String type) {
        return mRemoteData.getGoods(type);
    }

    /**
     * 22.获取商品
     */
    public Observable<SparringGoodsResponse> getGoodsFive(String type) {
        return mRemoteData.getGoodsFive(type);
    }

    /**
     * 20.新手陪练获取服务地区
     */
    public Observable<SparringAreaResponse> getServiceArea() {
        return mRemoteData.getServiceArea();
    }

    /**
     * 21.获取服务器时间
     */
    public Observable<ServerTimeResponse> getServerTime() {
        return mRemoteData.getServerTime();
    }

    /**
     * 10.取消订单
     */
    public Observable<BaseResponse> cancelOrder(String orderId, String userNum) {
        return mRemoteData.cancelOrder(orderId, userNum);
    }

    /**
     * 24.获取年检网点
     */
    public Observable<YearCheckResponse> annualInspectionList(AnnualDTO annualDTO) {
        return mRemoteData.annualInspectionList(annualDTO);
    }

    /**
     * 获取年检一条信息
     */
    public Observable<YearCheckDetailResponse> annualInspection(int id) {
        return mRemoteData.annualInspection(id);
    }

    /**
     * 获得加油站地点详情
     */
    public Observable<GasStationDetailResponse> gasStation(int id) {
        return mRemoteData.gasStation(id);
    }

    /**
     * 23.获取加油网点
     */
    public Observable<GasStationResponse> gasStationList(AnnualDTO annualDTO) {
        return mRemoteData.gasStationList(annualDTO);
    }

    /**
     * 25.模块化接口
     */
    public Observable<ModuleResponse> moduleTree() {
        return mRemoteData.moduleTree();
    }

    /**
     * 32.获取地区列表
     */
    public Observable<OrderExpressResponse> getAllAreas() {
        return mRemoteData.getAllAreas();
    }

    /**
     * 29.填写快递信息
     */
    public Observable<BaseResponse> addExpressInfo(ExpressDTO expressDTO) {
        return mRemoteData.addExpressInfo(expressDTO);
    }

    /**
     * 33.获取收件人信息
     */
    public Observable<ReceiveInfoResponse> getReceiveInfo(String orderId) {
        return mRemoteData.getReceiveInfo(orderId);
    }

    /**
     * 46.更新违章缴费状态
     * @param json
     */
    public Observable<BaseResponse> updateState(List<ViolationNum> json) {
        return mRemoteData.updateState(json);
    }
}
