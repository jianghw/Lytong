package com.zantong.mobilecttx.model.repository;

import com.zantong.mobilecttx.base.dto.BaseDTO;
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
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

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
     *
     * @param requestDTO
     * @return
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
}
