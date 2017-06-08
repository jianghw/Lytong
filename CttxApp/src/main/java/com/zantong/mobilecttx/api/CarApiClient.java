package com.zantong.mobilecttx.api;

import android.content.Context;
import android.text.TextUtils;

import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.bean.CouponResult;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.car.bean.CarLinkageResult;
import com.zantong.mobilecttx.car.dto.CarLinkageDTO;
import com.zantong.mobilecttx.car.dto.CarManagerDTO;
import com.zantong.mobilecttx.car.dto.CarMarnagerDetailDTO;
import com.zantong.mobilecttx.car.dto.LiYingCarManageDTO;
import com.zantong.mobilecttx.card.bean.YingXiaoResult;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.card.dto.BindDrivingDTO;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.card.dto.YingXiaoDataDTO;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderDetailResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.chongzhi.dto.RechargeOrderDTO;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.daijia.bean.DJTokenResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaCreateResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderDetailResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderListResult;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.daijia.dto.DaiJiaCreateDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderDetailDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderListDTO;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.bean.ActivitySignNum;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.huodong.dto.HundredPlanDTO;
import com.zantong.mobilecttx.map.bean.GasStationDetailResult;
import com.zantong.mobilecttx.map.bean.GasStationResult;
import com.zantong.mobilecttx.map.bean.WachCarPlaceDetailResult;
import com.zantong.mobilecttx.map.bean.WachCarPlaceResult;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResult;
import com.zantong.mobilecttx.map.bean.YearCheckResult;
import com.zantong.mobilecttx.user.bean.BonusResult;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.user.bean.MessageResult;
import com.zantong.mobilecttx.user.bean.MessageTypeResult;
import com.zantong.mobilecttx.user.dto.BonusDTO;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.user.dto.CouponDTO;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.weizhang.bean.ViolationHistoryBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationItemBean;
import com.zantong.mobilecttx.weizhang.dto.ViolationSearchDTO;

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
        BaseCallBack<GasStationDetailResult> baseCallBack = new BaseCallBack<GasStationDetailResult>(
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
//    /**
//     * 获得年检地点详情
//     * @param context
//     * @param params
//     * @param callback
//     */
//    public static void getLatAndLng(Context context, String params, CallBack<YearCheckDetailResult> callback){
//        BaseCallBack<YearCheckDetailResult> baseCallBack = new BaseCallBack<YearCheckDetailResult>(
//                context, callback, YearCheckDetailResult.class);
//        get(context, getAUrl(params+"&type=1"), baseCallBack);
//    }

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
     * /**
     * 统计广告点击次数
     *
     * @param context
     * @param id
     * @param callback
     */
    public static void commitAdClick(Context context, int id, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        get(context, getUrl("cttx/advertisementStatistics/" + id), baseCallBack);
    }

    public static void commitCar(Context context, BindCarDTO params, CallBack<BaseResult> callback) {
        params.setUsrnum(RSAUtils.strByEncryptionLiYing(context, PublicData.getInstance().userID, true));
        params.setEngineNo(RSAUtils.strByEncryptionLiYing(context, params.getEngineNo(), true));
        params.setPlateNo(RSAUtils.strByEncryptionLiYing(context, params.getPlateNo(), true));
        if (TextUtils.isEmpty(params.getFileNum())) {
            params.setFileNum("");
        } else {
            params.setFileNum(RSAUtils.strByEncryptionLiYing(context, params.getFileNum(), true));
        }
        if (TextUtils.isEmpty(params.getVin())) {
            params.setVin("");
        } else {
            params.setVin(RSAUtils.strByEncryptionLiYing(context, params.getVin(), true));
        }
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/bindingVehicle"), params, baseCallBack);
    }

    public static void commitDriving(Context context, BindDrivingDTO params, CallBack<BaseResult> callback) {
        params.setUserId(RSAUtils.strByEncryptionLiYing(context, PublicData.getInstance().userID, true));
        params.setFileNum(RSAUtils.strByEncryptionLiYing(context, params.getFileNum(), true));
        params.setLicenseno(RSAUtils.strByEncryptionLiYing(context, params.getLicenseno(), true));
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/bindingDriving"), params, baseCallBack);
    }

    public static void liYingReg(Context context, LiYingRegDTO params, CallBack<BaseResult> callback) {
        params.setToken(RSAUtils.strByEncryptionLiYing(context, PublicData.getInstance().deviceId, true));
        params.setPushmode("2");
        params.setPushswitch("1");
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/register"), params, baseCallBack);
    }

    public static void liYingCarManage(Context context, LiYingCarManageDTO params, CallBack<BaseResult> callback) {
        params.setUsrnum(RSAUtils.strByEncryptionLiYing(context, PublicData.getInstance().userID, true));
        params.setPlateNo(RSAUtils.strByEncryptionLiYing(context, params.getPlateNo(), true));
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
        BaseCallBack<RechargeResult> result = new BaseCallBack<RechargeResult>(
                context, callback, RechargeResult.class);
        post(context, getUrl("addOil/createOrder"), dto, result);
    }

    public static void queryOrders(Context context, RechargeOrderDTO dto, CallBack<RechargeOrderResult> callback) {
        BaseCallBack<RechargeOrderResult> result = new BaseCallBack<RechargeOrderResult>(
                context, callback, RechargeOrderResult.class);
//        post(context, "http://139.196.183.121:8081/addOil/findAllOrder", dto, result);
        post(context, getUrl("addOil/findAllOrder"), dto, result);
    }

    public static void cancelOrder(Context context, CancelRechargeOrderDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
//        post(context, "http://139.196.183.121:8081/addOil/cancleOrder", dto, result);
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
//        post(context, "http://139.196.183.121:8081/cttx/employeeRate", dto, result);
        post(context, getUrl("cttx/employeeRate"), dto, result);
    }

    public static void commitHundredPlan(Context context, HundredPlanDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
//        post(context, "http://139.196.183.121:8081/february/signUpActivity", dto, result);
        post(context, getUrl("february/signUpActivity"), dto, result);

    }

    public static void getCouponList(Context context, CouponDTO dto, CallBack<CouponResult> callback) {
        BaseCallBack<CouponResult> result = new BaseCallBack<CouponResult>(
                context, callback, CouponResult.class);
//        post(context, "http://139.196.183.121:8081/february/usrCouponInfo", dto, result);
        post(context, getUrl("february/usrCouponInfo"), dto, result);
    }

    public static void checkCtk(Context context, CheckCtkDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
//        post(context, "http://139.196.183.121:8081/february/applyCardCheck", dto, result);
        post(context, getUrl("february/applyCardCheck"), dto, result);
    }

    public static void getActivityCar(Context context, ActivityCarDTO dto, CallBack<ActivityCarResult> callback) {
        BaseCallBack<ActivityCarResult> result = new BaseCallBack<ActivityCarResult>(
                context, callback, ActivityCarResult.class);
//        post(context, "http://139.196.183.121:8081/february/carCheckActivity", dto, result);
        post(context, getUrl("february/carCheckActivity"), dto, result);
    }

    public static void getDaiJiaToken(Context context, DaiJiaDTO dto, CallBack<DJTokenResult> callback) {
        BaseCallBack<DJTokenResult> result = new BaseCallBack<DJTokenResult>(
                context, callback, DJTokenResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/nearByInfo", dto,result);
        post(context, getUrl("daijia/nearByInfo"), dto, result);
    }
    public static void huJiaoDaiJia(Context context, DaiJiaCreateDTO dto, CallBack<DaiJiaCreateResult> callback) {
        BaseCallBack<DaiJiaCreateResult> result = new BaseCallBack<DaiJiaCreateResult>(
                context, callback, DaiJiaCreateResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/addOrder", dto,result);
        post(context, getUrl("daijia/addOrder"), dto, result);
    }

    public static void getSignNum(Context context, CallBack<ActivitySignNum> callback) {
        BaseCallBack<ActivitySignNum> result = new BaseCallBack<ActivitySignNum>(
                context, callback, ActivitySignNum.class);
        post(context, getUrl("february/activityCount"), new Object(), result);
    }

    /**
     * 代驾中详情
     *
     * @param context
     * @param dto
     * @param callback
     */
    public static void getDaiJiaOrderDetail(Context context, DaiJiaOrderDetailDTO dto, CallBack<DaiJiaOrderDetailResult> callback) {
        BaseCallBack<DaiJiaOrderDetailResult> result = new BaseCallBack<DaiJiaOrderDetailResult>(
                context, callback, DaiJiaOrderDetailResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/queryOrderDetail", dto,result);
        post(context, getUrl("daijia/queryOrderDetail"), dto, result);
    }

    public static void cancelDaiJiaOrderDetail(Context context, DaiJiaOrderDetailDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/cancelOrder", dto,result);
        post(context, getUrl("daijia/cancelOrder"), dto, result);

    }

    public static void getDaiJiaOrderList(Context context, DaiJiaOrderListDTO dto, CallBack<DaiJiaOrderListResult> callback) {
        BaseCallBack<DaiJiaOrderListResult> result = new BaseCallBack<DaiJiaOrderListResult>(
                context, callback, DaiJiaOrderListResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/findAll", dto,result);
        post(context, getUrl("daijia/findAll"), dto, result);
    }

    public static void getMsgTypeList(Context context, BaseDTO dto, CallBack<MessageTypeResult> callback) {
        BaseCallBack<MessageTypeResult> result = new BaseCallBack<MessageTypeResult>(
                context, callback, MessageTypeResult.class);
//        post(context, "http://139.196.183.121:8081/message/findAll", dto,result);
        post(context, getUrl("message/findAll"), dto, result);
    }

    public static void getMsgList(Context context, MegDTO dto, CallBack<MessageResult> callback) {
        BaseCallBack<MessageResult> result = new BaseCallBack<MessageResult>(
                context, callback, MessageResult.class);
//        post(context, "http://139.196.183.121:8081/message/findMessageDetailByMessageId", dto,result);
        post(context, getUrl("message/findMessageDetailByMessageId"), dto, result);
    }
    public static void getUnReadMsgCount(Context context, BaseDTO dto, CallBack<MessageCountResult> callback) {
        BaseCallBack<MessageCountResult> result = new BaseCallBack<MessageCountResult>(
                context, callback, MessageCountResult.class);
//        post(context, "http://139.196.183.121:8081/message/countMessageDetail", dto,result);
        post(context, getUrl("message/countMessageDetail"), dto, result);
    }
    public static void commitYingXiaoData(Context context, YingXiaoDataDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/employeeRateUser"), dto, result);
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
        OcrCallBack<DriverOcrResult> result = new OcrCallBack<DriverOcrResult>(
                context, callback, DriverOcrResult.class);
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
