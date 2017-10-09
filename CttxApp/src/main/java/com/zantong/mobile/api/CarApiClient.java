package com.zantong.mobile.api;

import android.content.Context;
import android.text.TextUtils;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.request.RegisterDTO;
import com.zantong.mobile.base.bean.CouponResult;
import com.zantong.mobile.base.dto.BaseDTO;
import com.zantong.mobile.car.bean.CarLinkageResult;
import com.zantong.mobile.car.dto.CarLinkageDTO;
import com.zantong.mobile.car.dto.CarManagerDTO;
import com.zantong.mobile.car.dto.CarMarnagerDetailDTO;
import com.zantong.mobile.car.dto.LiYingCarManageDTO;
import com.zantong.mobile.card.bean.YingXiaoResult;
import com.zantong.mobile.card.dto.BindCarDTO;
import com.zantong.mobile.chongzhi.bean.RechargeOrderDetailResult;
import com.zantong.mobile.chongzhi.bean.RechargeOrderResult;
import com.zantong.mobile.chongzhi.bean.RechargeResult;
import com.zantong.mobile.chongzhi.dto.RechargeDTO;
import com.zantong.mobile.chongzhi.dto.RechargeOrderDTO;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.daijia.bean.DriverOcrResult;
import com.zantong.mobile.daijia.bean.DrivingOcrResult;
import com.zantong.mobile.home.bean.HomeResult;
import com.zantong.mobile.home.dto.HomeDataDTO;
import com.zantong.mobile.map.bean.GasStationDetailResult;
import com.zantong.mobile.map.bean.GasStationResult;
import com.zantong.mobile.map.bean.WachCarPlaceDetailResult;
import com.zantong.mobile.map.bean.WachCarPlaceResult;
import com.zantong.mobile.map.bean.YearCheckDetailResult;
import com.zantong.mobile.map.bean.YearCheckResult;
import com.zantong.mobile.order.bean.MessageResult;
import com.zantong.mobile.user.bean.BonusResult;
import com.zantong.mobile.user.bean.MessageCountResult;
import com.zantong.mobile.user.bean.MessageTypeResult;
import com.zantong.mobile.user.dto.BonusDTO;
import com.zantong.mobile.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobile.user.dto.CouponDTO;
import com.zantong.mobile.user.dto.MegDTO;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.weizhang.bean.PayOrderResult;
import com.zantong.mobile.weizhang.bean.ViolationHistoryBean;
import com.zantong.mobile.weizhang.bean.ViolationItemBean;
import com.zantong.mobile.weizhang.dto.ViolationOrderDTO;
import com.zantong.mobile.weizhang.dto.ViolationSearchDTO;

import java.io.File;

public class CarApiClient extends BaseApiClient {

    /**
     * 获得洗车地点集合
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getWashCarList(Context context, CarManagerDTO params, CallBack<WachCarPlaceResult> callback) {
        BaseCallBack<WachCarPlaceResult> baseCallBack = new BaseCallBack<WachCarPlaceResult>(
                context, callback, WachCarPlaceResult.class);
        post(context, getUrl("cttx/carWashingList"), params, baseCallBack);
    }

    /**
     * 获得洗车地点详情
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getWashCarDetail(Context context, CarMarnagerDetailDTO params, CallBack<WachCarPlaceDetailResult> callback) {
        BaseCallBack<WachCarPlaceDetailResult> baseCallBack = new BaseCallBack<WachCarPlaceDetailResult>(
                context, callback, WachCarPlaceDetailResult.class);
        get(context, getUrl("cttx/carWashing/" + params.getId()), baseCallBack);
    }

    /**
     * 获得加油站地点集合
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getGasStationList(Context context, CarManagerDTO params, CallBack<GasStationResult> callback) {
        BaseCallBack<GasStationResult> baseCallBack = new BaseCallBack<GasStationResult>(
                context, callback, GasStationResult.class);
        post(context, getUrl("cttx/gasStationList"), params, baseCallBack);
    }

    /**
     * 获得加油站地点详情
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getGasStationDetail(Context context, CarMarnagerDetailDTO params, CallBack<GasStationDetailResult> callback) {
        BaseCallBack<GasStationDetailResult> baseCallBack = new BaseCallBack<>(
                context, callback, GasStationDetailResult.class);
        get(context, getUrl("cttx/gasStation/" + params.getId()), baseCallBack);
    }

    /**
     * 获得年检地点集合
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getYearCheckList(Context context, CarManagerDTO params, CallBack<YearCheckResult> callback) {
        BaseCallBack<YearCheckResult> baseCallBack = new BaseCallBack<YearCheckResult>(
                context, callback, YearCheckResult.class);
        post(context, getUrl("cttx/annualinspectionList"), params, baseCallBack);
    }

    /**
     * 获得年检地点详情
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getYearCheckDetail(Context context, CarMarnagerDetailDTO params, CallBack<YearCheckDetailResult> callback) {
        BaseCallBack<YearCheckDetailResult> baseCallBack = new BaseCallBack<YearCheckDetailResult>(
                context, callback, YearCheckDetailResult.class);
        get(context, getUrl("cttx/annualinspection/" + params.getId()), baseCallBack);
    }

    /**
     * 首页接口
     *
     * @param context
     * @param params
     * @param callback
     */
    public static void getHomeData(Context context, HomeDataDTO params, CallBack<HomeResult> callback) {
        BaseCallBack<HomeResult> homeResult = new BaseCallBack<HomeResult>(
                context, callback, HomeResult.class);
        post(context, getUrl("cttx/homePage"), params, homeResult);
    }

    /**
     * 统计广告点击次数
     */
    public static void commitAdClick(Context context, int id, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        get(context, getUrl("cttx/advertisementStatistics/" + id), baseCallBack);
    }

    public static void commitCar(Context context, BindCarDTO params, CallBack<BaseResult> callback) {

        params.setUsrnum(RSAUtils.strByEncryptionLiYing(MemoryData.getInstance().userID, true));
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
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/bindingVehicle"), params, baseCallBack);
    }

    public static void liYingReg(Context context, RegisterDTO params, CallBack<BaseResult> callback) {
        params.setToken(RSAUtils.strByEncryptionLiYing(MemoryData.getInstance().deviceId, true));
        params.setPushmode("2");
        params.setPushswitch("1");
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/register"), params, baseCallBack);
    }

    public static void liYingCarManage(Context context, LiYingCarManageDTO params, CallBack<BaseResult> callback) {
        params.setUsrnum(RSAUtils.strByEncryptionLiYing(MemoryData.getInstance().userID, true));
        params.setPlateNo(RSAUtils.strByEncryptionLiYing(params.getPlateNo(), true));
        try {
            params.setVehicleType(String.valueOf(Integer.valueOf(params.getVehicleType())));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/userCarManage"), params, baseCallBack);
    }

    public static void liYingCarLinkage(Context context, CarLinkageDTO params, CallBack<CarLinkageResult> callback) {
        BaseCallBack<CarLinkageResult> baseCallBack = new BaseCallBack<CarLinkageResult>(
                context, callback, CarLinkageResult.class);
        post(context, getUrl("cttx/carModel"), params, baseCallBack);
    }

    public static void getBonusInfo(Context context, BonusDTO dto, CallBack<BonusResult> callback) {
        BaseCallBack<BonusResult> result = new BaseCallBack<BonusResult>(
                context, callback, BonusResult.class);
        post(context, getDownUrl("shareRanking"), dto, result);
    }

    public static void createOrder(Context context, RechargeDTO dto, CallBack<RechargeResult> callback) {
        BaseCallBack<RechargeResult> result = new BaseCallBack<>(
                context, callback, RechargeResult.class);
        post(context, getUrl("addOil/createOrder"), dto, result);
    }

    /**
     * 43.生成违章缴费订单
     */
    public static void createOrder(Context context, ViolationOrderDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<>(context, callback, BaseResult.class);
        post(context, getUrl("payment/createOrder"), dto, result);
    }

    public static void queryOrders(Context context, RechargeOrderDTO dto, CallBack<RechargeOrderResult> callback) {
        BaseCallBack<RechargeOrderResult> result = new BaseCallBack<RechargeOrderResult>(
                context, callback, RechargeOrderResult.class);
        post(context, getUrl("addOil/findAllOrder"), dto, result);
    }

    public static void cancelOrder(Context context, CancelRechargeOrderDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("addOil/cancleOrder"), dto, result);
    }

    public static void queryOrderDetail(Context context, CancelRechargeOrderDTO dto, CallBack<RechargeOrderDetailResult> callback) {
        BaseCallBack<RechargeOrderDetailResult> result = new BaseCallBack<RechargeOrderDetailResult>(
                context, callback, RechargeOrderDetailResult.class);
        post(context, "http://139.196.183.121:8081/addOil/queryOrder", dto, result);
        post(context, getUrl("addOil/queryOrder"), dto, result);
    }

    public static void getYingXiaoCode(Context context, CancelRechargeOrderDTO dto, CallBack<YingXiaoResult> callback) {
        BaseCallBack<YingXiaoResult> result = new BaseCallBack<YingXiaoResult>(
                context, callback, YingXiaoResult.class);
        post(context, getUrl("cttx/employeeRate"), dto, result);
    }

    public static void getCouponList(Context context, CouponDTO dto, CallBack<CouponResult> callback) {
        BaseCallBack<CouponResult> result = new BaseCallBack<CouponResult>(
                context, callback, CouponResult.class);
        post(context, getUrl("february/usrCouponInfo"), dto, result);
    }

    public static void getMsgTypeList(Context context, BaseDTO dto, CallBack<MessageTypeResult> callback) {
        BaseCallBack<MessageTypeResult> result = new BaseCallBack<MessageTypeResult>(
                context, callback, MessageTypeResult.class);
        post(context, getUrl("message/findAll"), dto, result);
    }

    public static void getMsgList(Context context, MegDTO dto, CallBack<MessageResult> callback) {
        BaseCallBack<MessageResult> result = new BaseCallBack<MessageResult>(
                context, callback, MessageResult.class);
        post(context, getUrl("message/findMessageDetailByMessageId"), dto, result);
    }

    public static void getUnReadMsgCount(Context context, BaseDTO dto, CallBack<MessageCountResult> callback) {
        BaseCallBack<MessageCountResult> result = new BaseCallBack<>(
                context, callback, MessageCountResult.class);
        post(context, getUrl("message/countMessageDetail"), dto, result);
    }

    public static void commitYingXiaoData(Context context, Object dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<>(
                context, callback, BaseResult.class);
        post(context, getUrl("applyRecord/add"), dto, result);
    }

    public static void getPayOrderSn(Context context, String params, CallBack<PayOrderResult> callback) {
        BaseCallBack<PayOrderResult> result = new BaseCallBack<>(context, callback, PayOrderResult.class);
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
