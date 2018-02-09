package com.zantong.mobile.model.repository;

import com.tzly.annual.base.bean.BaseResponse;
import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.request.RegisterDTO;
import com.tzly.annual.base.bean.response.AnnouncementResult;
import com.tzly.annual.base.bean.response.CattleOrderResponse;
import com.tzly.annual.base.bean.response.LoginResult;
import com.tzly.annual.base.bean.response.StatistCountResult;
import com.tzly.annual.base.bean.response.SubjectGoodsResult;
import com.tzly.annual.base.bean.Result;
import com.zantong.mobile.base.dto.BaseDTO;
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
import com.tzly.annual.base.bean.response.OrderListResult;
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
    Observable<MessageTypeResult> messageFindAll(BaseDTO bean);

    /**
     * 2.4.21获取消息详情列表
     *
     * @param bean
     * @return
     */
    Observable<MessageResult> findMessageDetailByMessageId(MegDTO bean);

    /**
     * 2.4.22获取消息详情
     *
     * @param bean
     * @return
     */
    Observable<MessageDetailResult> findMessageDetail(MessageDetailDTO bean);

    /**
     * 2.4.2查看优惠券信息
     */
    Observable<CouponFragmentResult> usrCouponInfo(String usrnum, String couponStatus);

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
    Observable<MessageResult> deleteMessageDetail(MegDTO megDTO);

    /**
     * 2.4.27删除用户优惠券
     */
    Observable<MessageResult> delUsrCoupon(String couponId, String userId);

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
    Observable<StartPicResult> startGetPic(String msg);

    /**
     * 57.获取指定类型优惠券
     */
    Observable<RechargeCouponResult> getCouponByType(String userId, String type);

    /**
     * 10.创建加油订单
     */
    Observable<RechargeResult> addOilCreateOrder(RechargeDTO rechargeDTO);

    /**
     * 加油充值
     */
    Observable<PayOrderResult> onPayOrderByCoupon(String payUrl, String orderPrice, String payType);

    /**
     * 43.生成违章缴费订单
     */
    Observable<PayOrderResult> paymentCreateOrder(ViolationPayDTO payDTO);

    Observable<LicenseResponseBean> loadLoginPostTest(String msg);

    /**
     * 1.首页信息
     */
    Observable<HomeResult> homePage(HomeDataDTO id);

    /**
     * cip.cfc.c003.01
     */
    Observable<UserCarsResult> getRemoteCarInfo(String requestDTO);

    /**
     * 37.获取所有未读消息数量
     */
    Observable<MessageCountResult> countMessageDetail(BaseDTO baseDTO);

    /**
     * 58.获取banner图片
     */
    Observable<BannerResult> getBanner(String type);

    /**
     * 55.行驶证扫描接口
     */
    Observable<DrivingOcrResult> uploadDrivingImg(MultipartBody.Part part);

    /**
     * 3.获取商户区域列表
     */
    Observable<MerchantAresResult> getMerchantArea();

    /**
     * 4.获取区域商品列表
     */
    Observable<AresGoodsResult> getAreaGoods(int areaCode);

    /**
     * 2.创建订单
     */
    Observable<CreateOrderResult> createOrder(CreateOrderDTO createOrder);

    /**
     * 7.获取用户指定活动的统计总数
     */
    Observable<RecordCountResult> getRecordCount(String type, String phone);

    /**
     * 48.绑定行驶证接口
     */
    Observable<BaseResult> commitCarInfoToNewServer(BindCarDTO bindCarDTO);

    /**
     * cip.cfc.u005.01
     */
    Observable<Result> commitCarInfoToOldServer(String msg);

    /**
     * 5.获取工行支付页面
     */
    Observable<PayOrderResult> getBankPayHtml(String orderId, String orderPrice);

    /**
     * 8.查询订单列表
     */
    Observable<OrderListResult> getOrderList(String userId);

    /**
     * 9.获取订单详情
     */
    Observable<OrderDetailResult> getOrderDetail(String orderId);

    /**
     * 10.更新订单状态
     */
    Observable<BaseResult> updateOrderStatus(String orderId, int orderStatus);

    /**
     * 6.获取商品详情
     */
    Observable<GoodsDetailResult> getGoodsDetail(String goodsId);

    /**
     * 获取违章信息
     */
    Observable<HomeCarResult> getTextNoticeInfo(String defaultUserID);

    /**
     * 处理违章信息
     */
    Observable<BaseResult> handleViolations(ViolationCarDTO violationResult);

    /**
     * 车辆违章查询
     * cip.cfc.v002.01
     */
    Observable<ViolationResultParent> searchViolation(String msg);

    /**
     * cip.cfc.c002.01
     */
    Observable<PayCarResult> getPayCars(String msg);

    Observable<VehicleLicenseResult> addOrUpdateVehicleLicense(List<BindCarDTO> dtoList);

    /**
     * 13.判断是否为司机
     */
    Observable<DriverCoachResult> getDriverCoach(String phone);

    /**
     * 16.新增车辆
     */
    Observable<BaseResult> addVehicleLicense(BindCarDTO bindCarDTO);

    /**
     * 18.删除车辆
     */
    Observable<BaseResult> removeVehicleLicense(BindCarDTO bindCarDTO);

    /**
     * 17.编辑车辆
     */
    Observable<BaseResult> updateVehicleLicense(BindCarDTO bindCarDTO);

    /**
     * 22.获取商品
     */
    Observable<SubjectGoodsResult> getGoods(String type);

    Observable<SparringGoodsResult> getGoodsFive(String type);

    /**
     * 20.新手陪练获取服务地区
     */
    Observable<SparringAreaResult> getServiceArea();

    /**
     * 21.获取服务器时间
     */
    Observable<ServerTimeResult> getServerTime();

    /**
     * 10.取消订单
     */
    Observable<BaseResult> cancelOrder(String orderId, String userNum);

    /**
     * 24.获取年检网点
     */
    Observable<YearCheckResult> annualInspectionList(AnnualDTO annualDTO);

    /**
     * 获取年检一条信息
     */
    Observable<YearCheckDetailResult> annualInspection(int id);

    /**
     * 获得加油站地点详情
     */
    Observable<GasStationDetailResult> gasStation(int id);

    /**
     * 23.获取加油网点
     */
    Observable<GasStationResult> gasStationList(AnnualDTO annualDTO);

    /**
     * 25.模块化接口
     */
    Observable<ModuleResult> moduleTree();

    /**
     * 32.获取地区列表
     */
    Observable<OrderExpressResult> getAllAreas();

    /**
     * 29.填写快递信息
     */
    Observable<BaseResult> addExpressInfo(ExpressDTO expressDTO);

    /**
     * 33.获取收件人信息
     */
    Observable<ReceiveInfoResult> getReceiveInfo(String orderId);

    /**
     * 2.获取订单列表
     */
    Observable<CattleOrderResponse> queryOrderList();

    /**
     * 8.用户注册修改接口
     */
    Observable<BaseResult> register(RegisterDTO registerDTO);

    /**
     * cip.cfc.v004.01
     */
    Observable<Result> loginV004(String msg);

    /**
     * 订单
     */
    Observable<OrderListResult> getAnnualInspectionOrders(String id);

    Observable<BaseResult> getAnnualInspectionOrderTargetState(String orderId, String status, String remark, String userNum);

    /**
     * 输入快递单号
     */
    Observable<BaseResult> addBackExpressInfo(String orderId, String expressNo, String userNum);

    Observable<BaseResult> updateState(List<ViolationNum> json);

    Observable<ViolationNumBean> numberedQuery(String msg);

    /**
     * 登录
     */
    Observable<LoginResult> innerUserLogin(String userPhone, String userPassword);

    /**
     * 获取内部通知
     */
    Observable<AnnouncementResult> findAnnouncements();
    /**
     * 分享  新
     */
    Observable<StatistCountResult> getStatisticsCount(String phone);
}