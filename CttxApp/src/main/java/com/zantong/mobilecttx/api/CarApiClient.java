package com.zantong.mobilecttx.api;

import android.content.Context;
import android.text.TextUtils;

import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.base.bean.CouponResponse;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.car.bean.CarLinkageResponse;
import com.zantong.mobilecttx.car.dto.CarLinkageDTO;
import com.zantong.mobilecttx.car.dto.CarManagerDTO;
import com.zantong.mobilecttx.car.dto.CarMarnagerDetailDTO;
import com.zantong.mobilecttx.car.dto.LiYingCarManageDTO;
import com.zantong.mobilecttx.card.bean.YingXiaoResponse;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.card.dto.BindDrivingDTO;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderDetailResponse;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderResponse;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResponse;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.chongzhi.dto.RechargeOrderDTO;
import com.zantong.mobilecttx.daijia.bean.DJTokenResponse;
import com.zantong.mobilecttx.daijia.bean.DaiJiaCreateResponse;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderDetailResponse;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderListResponse;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.daijia.dto.DaiJiaCreateDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderDetailDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderListDTO;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResponse;
import com.zantong.mobilecttx.huodong.bean.ActivitySignNum;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.huodong.dto.HundredPlanDTO;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.GasStationResponse;
import com.zantong.mobilecttx.map.bean.WachCarPlaceDetailResponse;
import com.zantong.mobilecttx.map.bean.WachCarPlaceResponse;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.BonusResponse;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;
import com.zantong.mobilecttx.user.bean.MessageTypeResponse;
import com.zantong.mobilecttx.user.dto.BonusDTO;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.user.dto.CouponDTO;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;
import com.zantong.mobilecttx.weizhang.bean.ViolationHistoryBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationItemBean;
import com.zantong.mobilecttx.weizhang.dto.ViolationOrderDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationSearchDTO;

import java.io.File;

import cn.qqtheme.framework.bean.BaseResponse;

public class CarApiClient extends BaseApiClient {

    /**
     * 获得洗车地点集合
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getWashCarList(Context context, CarManagerDTO params, CallBack<WachCarPlaceResponse> callback) {
        BaseCallBack<WachCarPlaceResponse> baseCallBack = new BaseCallBack<WachCarPlaceResponse>(
                context, callback, WachCarPlaceResponse.class);
        post(context, getUrl("cttx/carWashingList"), params, baseCallBack);
    }

    /**
     * 获得洗车地点详情
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getWashCarDetail(Context context, CarMarnagerDetailDTO params, CallBack<WachCarPlaceDetailResponse> callback) {
        BaseCallBack<WachCarPlaceDetailResponse> baseCallBack = new BaseCallBack<WachCarPlaceDetailResponse>(
                context, callback, WachCarPlaceDetailResponse.class);
        get(context, getUrl("cttx/carWashing/" + params.getId()), baseCallBack);
    }

    /**
     * 获得加油站地点集合
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getGasStationList(Context context, CarManagerDTO params, CallBack<GasStationResponse> callback) {
        BaseCallBack<GasStationResponse> baseCallBack = new BaseCallBack<GasStationResponse>(
                context, callback, GasStationResponse.class);
        post(context, getUrl("cttx/gasStationList"), params, baseCallBack);
    }

    /**
     * 获得加油站地点详情
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getGasStationDetail(Context context, CarMarnagerDetailDTO params, CallBack<GasStationDetailResponse> callback) {
        BaseCallBack<GasStationDetailResponse> baseCallBack = new BaseCallBack<>(
                context, callback, GasStationDetailResponse.class);
        get(context, getUrl("cttx/gasStation/" + params.getId()), baseCallBack);
    }

    /**
     * 获得年检地点集合
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getYearCheckList(Context context, CarManagerDTO params, CallBack<YearCheckResponse> callback) {
        BaseCallBack<YearCheckResponse> baseCallBack = new BaseCallBack<YearCheckResponse>(
                context, callback, YearCheckResponse.class);
        post(context, getUrl("cttx/annualinspectionList"), params, baseCallBack);
    }

    /**
     * 获得年检地点详情
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getYearCheckDetail(Context context, CarMarnagerDetailDTO params, CallBack<YearCheckDetailResponse> callback) {
        BaseCallBack<YearCheckDetailResponse> baseCallBack = new BaseCallBack<YearCheckDetailResponse>(
                context, callback, YearCheckDetailResponse.class);
        get(context, getUrl("cttx/annualinspection/" + params.getId()), baseCallBack);
    }

    /**
     * 首页接口
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getHomeData(Context context, HomeDataDTO params, CallBack<HomeResponse> callback) {
        BaseCallBack<HomeResponse> homeResult = new BaseCallBack<HomeResponse>(
                context, callback, HomeResponse.class);
        post(context, getUrl("cttx/homePage"), params, homeResult);
    }

    /**
     * 统计广告点击次数
     * type=1 主页广告
     * type=2 优惠小部件
     */
    public static void commitAdClick(Context context, int id, String type, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> baseCallBack = new BaseCallBack<>(
                context, callback, BaseResponse.class);
        get(context, getUrl("cttx/advertisementStatistics/" + id + "?type=" + type), baseCallBack);
    }

    public static void commitAdClick(Context context, int id, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> baseCallBack = new BaseCallBack<>(
                context, callback, BaseResponse.class);
        get(context, getUrl("cttx/advertisementStatistics/" + id), baseCallBack);
    }

    public static void commitCar(Context context, BindCarDTO params, CallBack<BaseResponse> callback) {

        params.setUsrnum(MainRouter.getRASUserID());
        params.setEngineNo(RSAUtils.strByEncryptionLiYing(params.getEngineNo(), true));
        params.setPlateNo(RSAUtils.strByEncryptionLiYing(params.getPlateNo(), true));

        if (TextUtils.isEmpty(params.getFileNum())) {
            params.setFileNum("");
        } else {
            params.setFileNum(RSAUtils.strByEncryptionLiYing(params.getFileNum(), true));
        }
        if (TextUtils.isEmpty(params.getVin())) {
            params.setVin("");
        } else {
            params.setVin(RSAUtils.strByEncryptionLiYing(params.getVin(), true));
        }
        BaseCallBack<BaseResponse> baseCallBack = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("cttx/bindingVehicle"), params, baseCallBack);
    }

    public static void commitDriving(Context context, BindDrivingDTO params, CallBack<BaseResponse> callback) {
        params.setUserId(MainRouter.getRASUserID());
        params.setFileNum(RSAUtils.strByEncryptionLiYing(params.getFileNum(), true));
        params.setLicenseno(RSAUtils.strByEncryptionLiYing(params.getLicenseno(), true));
        BaseCallBack<BaseResponse> baseCallBack = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("cttx/bindingDriving"), params, baseCallBack);
    }

    public static void liYingReg(Context context, LiYingRegDTO params, CallBack<BaseResponse> callback) {
        params.setToken(RSAUtils.strByEncryptionLiYing(MainRouter.getPushId(), true));
        params.setPushmode("2");
        params.setPushswitch("1");
        BaseCallBack<BaseResponse> baseCallBack = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("cttx/register"), params, baseCallBack);
    }

    public static void liYingCarManage(Context context, LiYingCarManageDTO params, CallBack<BaseResponse> callback) {
        params.setUsrnum(MainRouter.getRASUserID());
        params.setPlateNo(RSAUtils.strByEncryptionLiYing(params.getPlateNo(), true));
        try {
            params.setVehicleType(String.valueOf(Integer.valueOf(params.getVehicleType())));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        BaseCallBack<BaseResponse> baseCallBack = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("cttx/userCarManage"), params, baseCallBack);
    }

    public static void liYingCarLinkage(Context context, CarLinkageDTO params, CallBack<CarLinkageResponse> callback) {
        BaseCallBack<CarLinkageResponse> baseCallBack = new BaseCallBack<CarLinkageResponse>(
                context, callback, CarLinkageResponse.class);
        post(context, getUrl("cttx/carModel"), params, baseCallBack);
    }

    public static void getBonusInfo(Context context, BonusDTO dto, CallBack<BonusResponse> callback) {
        BaseCallBack<BonusResponse> result = new BaseCallBack<BonusResponse>(
                context, callback, BonusResponse.class);
        post(context, getDownUrl("shareRanking"), dto, result);
    }

    public static void createOrder(Context context, RechargeDTO dto, CallBack<RechargeResponse> callback) {
        BaseCallBack<RechargeResponse> result = new BaseCallBack<>(
                context, callback, RechargeResponse.class);
        post(context, getUrl("addOil/createOrder"), dto, result);
    }

    /**
     * 43.生成违章缴费订单
     */
    public static void createOrder(Context context, ViolationOrderDTO dto, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> result = new BaseCallBack<>(context, callback, BaseResponse.class);
        post(context, getUrl("payment/createOrder"), dto, result);
    }

    public static void queryOrders(Context context, RechargeOrderDTO dto, CallBack<RechargeOrderResponse> callback) {
        BaseCallBack<RechargeOrderResponse> result = new BaseCallBack<RechargeOrderResponse>(
                context, callback, RechargeOrderResponse.class);
        post(context, getUrl("addOil/findAllOrder"), dto, result);
    }

    public static void cancelOrder(Context context, CancelRechargeOrderDTO dto, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> result = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("addOil/cancleOrder"), dto, result);
    }

    public static void queryOrderDetail(Context context, CancelRechargeOrderDTO dto, CallBack<RechargeOrderDetailResponse> callback) {
        BaseCallBack<RechargeOrderDetailResponse> result = new BaseCallBack<RechargeOrderDetailResponse>(
                context, callback, RechargeOrderDetailResponse.class);
        post(context, "http://139.196.183.121:8081/addOil/queryOrder", dto, result);
        post(context, getUrl("addOil/queryOrder"), dto, result);
    }

    public static void getYingXiaoCode(Context context, CancelRechargeOrderDTO dto, CallBack<YingXiaoResponse> callback) {
        BaseCallBack<YingXiaoResponse> result = new BaseCallBack<YingXiaoResponse>(
                context, callback, YingXiaoResponse.class);
        post(context, getUrl("cttx/employeeRate"), dto, result);
    }

    public static void commitHundredPlan(Context context, HundredPlanDTO dto, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> result = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("february/signUpActivity"), dto, result);
    }

    public static void getCouponList(Context context, CouponDTO dto, CallBack<CouponResponse> callback) {
        BaseCallBack<CouponResponse> result = new BaseCallBack<CouponResponse>(
                context, callback, CouponResponse.class);
        post(context, getUrl("february/usrCouponInfo"), dto, result);
    }

    public static void checkCtk(Context context, CheckCtkDTO dto, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> result = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("february/applyCardCheck"), dto, result);
    }

    public static void getActivityCar(Context context, ActivityCarDTO dto, CallBack<ActivityCarResponse> callback) {
        BaseCallBack<ActivityCarResponse> result = new BaseCallBack<ActivityCarResponse>(
                context, callback, ActivityCarResponse.class);
        post(context, getUrl("february/carCheckActivity"), dto, result);
    }

    public static void getDaiJiaToken(Context context, DaiJiaDTO dto, CallBack<DJTokenResponse> callback) {
        BaseCallBack<DJTokenResponse> result = new BaseCallBack<DJTokenResponse>(
                context, callback, DJTokenResponse.class);
        post(context, getUrl("daijia/nearByInfo"), dto, result);
    }

    public static void huJiaoDaiJia(Context context, DaiJiaCreateDTO dto, CallBack<DaiJiaCreateResponse> callback) {
        BaseCallBack<DaiJiaCreateResponse> result = new BaseCallBack<DaiJiaCreateResponse>(
                context, callback, DaiJiaCreateResponse.class);
        post(context, getUrl("daijia/addOrder"), dto, result);
    }

    public static void getSignNum(Context context, CallBack<ActivitySignNum> callback) {
        BaseCallBack<ActivitySignNum> result = new BaseCallBack<ActivitySignNum>(
                context, callback, ActivitySignNum.class);
        post(context, getUrl("february/activityCount"), new Object(), result);
    }

    /**
     * 代驾中详情
     */
    public static void getDaiJiaOrderDetail(Context context, DaiJiaOrderDetailDTO dto, CallBack<DaiJiaOrderDetailResponse> callback) {
        BaseCallBack<DaiJiaOrderDetailResponse> result = new BaseCallBack<DaiJiaOrderDetailResponse>(
                context, callback, DaiJiaOrderDetailResponse.class);
        post(context, getUrl("daijia/queryOrderDetail"), dto, result);
    }

    public static void cancelDaiJiaOrderDetail(Context context, DaiJiaOrderDetailDTO dto, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> result = new BaseCallBack<BaseResponse>(
                context, callback, BaseResponse.class);
        post(context, getUrl("daijia/cancelOrder"), dto, result);

    }

    public static void getDaiJiaOrderList(Context context, DaiJiaOrderListDTO dto, CallBack<DaiJiaOrderListResponse> callback) {
        BaseCallBack<DaiJiaOrderListResponse> result = new BaseCallBack<DaiJiaOrderListResponse>(
                context, callback, DaiJiaOrderListResponse.class);
        post(context, getUrl("daijia/findAll"), dto, result);
    }

    public static void getMsgTypeList(Context context, BaseDTO dto, CallBack<MessageTypeResponse> callback) {
        BaseCallBack<MessageTypeResponse> result = new BaseCallBack<MessageTypeResponse>(
                context, callback, MessageTypeResponse.class);
        post(context, getUrl("message/findAll"), dto, result);
    }

    public static void getMsgList(Context context, MegDTO dto, CallBack<MessageResponse> callback) {
        BaseCallBack<MessageResponse> result = new BaseCallBack<MessageResponse>(
                context, callback, MessageResponse.class);
        post(context, getUrl("message/findMessageDetailByMessageId"), dto, result);
    }

    public static void getUnReadMsgCount(Context context, BaseDTO dto, CallBack<MessageCountResponse> callback) {
        BaseCallBack<MessageCountResponse> result = new BaseCallBack<>(
                context, callback, MessageCountResponse.class);
        post(context, getUrl("message/countMessageDetail"), dto, result);
    }

    public static void commitYingXiaoData(Context context, Object dto, CallBack<BaseResponse> callback) {
        BaseCallBack<BaseResponse> result = new BaseCallBack<>(
                context, callback, BaseResponse.class);
        post(context, getUrl("applyRecord/add"), dto, result);
    }

    public static void getPayOrderSn(Context context, String params, CallBack<PayOrderResponse> callback) {
        BaseCallBack<PayOrderResponse> result = new BaseCallBack<>(context, callback, PayOrderResponse.class);
        get(context, params, result);
    }

    public static void uploadDrivingImg(Context context, File file, CallBack<DrivingOcrResult> callback) {
        OcrCallBack<DrivingOcrResult> result = new OcrCallBack<DrivingOcrResult>(
                context, callback, DrivingOcrResult.class);
        Config.OCR_TYPE = 0;
        post(context, "http://liyingtong.com:8080/PIM_DRIVING/SrvXMLAPI", file, result);
    }

    public static void uploadDriverImg(Context context, File file, CallBack<DriverOcrResult> callback) {
        OcrCallBack<DriverOcrResult> result = new OcrCallBack<>(context, callback, DriverOcrResult.class);
        Config.OCR_TYPE = 1;
        post(context, "http://liyingtong.com:8080/PIM_DRIVER/SrvXMLAPI", file, result);
    }

    public static void getViolationHistory(Context context, ViolationSearchDTO params, CallBack<ViolationHistoryBean> callback) {
        BaseCallBack<ViolationHistoryBean> result = new BaseCallBack<ViolationHistoryBean>(
                context, callback, ViolationHistoryBean.class);
        post(context, getUrl("payment/getPayCar"), params, result);
    }

    public static void getViolationHistoryByCar(Context context, ViolationSearchDTO params, CallBack<ViolationItemBean> callback) {
        BaseCallBack<ViolationItemBean> result = new BaseCallBack<ViolationItemBean>(
                context, callback, ViolationItemBean.class);
        post(context, getUrl("payment/getPayRecord"), params, result);
    }


}
