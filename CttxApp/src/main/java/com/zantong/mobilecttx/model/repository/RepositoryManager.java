package com.zantong.mobilecttx.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResult;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResult;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.home.bean.BannerResult;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.home.bean.StartPicResult;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;
import com.zantong.mobilecttx.order.bean.OrderListResult;
import com.zantong.mobilecttx.user.bean.CouponFragmentResult;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.user.bean.MessageDetailResult;
import com.zantong.mobilecttx.user.bean.MessageResult;
import com.zantong.mobilecttx.user.bean.MessageTypeResult;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

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
     * 驾驶证查分
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
     *
     * @return LoginInfoBean.RspInfoBean
     */
    public LoginInfoBean.RspInfoBean readObjectLoginInfoBean() {
        return mLocalData.readObjectLoginInfoBean();
    }

    /**
     * 初始化用户登录信息
     *
     * @param rspInfoBean
     */
    public void initGlobalLoginInfo(LoginInfoBean.RspInfoBean rspInfoBean) {
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
}
