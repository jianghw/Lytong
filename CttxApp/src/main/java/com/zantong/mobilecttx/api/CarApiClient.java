package com.zantong.mobilecttx.api;

import android.content.Context;
import android.text.TextUtils;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.base.bean.CouponResult;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.bean.ActivitySignNum;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.map.dto.CarManagerDTO;
import com.zantong.mobilecttx.map.dto.CarMarnagerDetailDTO;
import com.zantong.mobilecttx.user.bean.BonusResult;
import com.zantong.mobilecttx.daijia.bean.DJTokenResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaCreateResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderDetailResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderListResult;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.map.bean.GasStationDetailResult;
import com.zantong.mobilecttx.map.bean.GasStationResult;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.user.bean.MessageResult;
import com.zantong.mobilecttx.user.bean.MessageTypeResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderDetailResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.map.bean.WachCarPlaceDetailResult;
import com.zantong.mobilecttx.map.bean.WachCarPlaceResult;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResult;
import com.zantong.mobilecttx.map.bean.YearCheckResult;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.user.dto.BonusDTO;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.user.dto.CouponDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaCreateDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderDetailDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderListDTO;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.huodong.dto.HundredPlanDTO;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.chongzhi.dto.RechargeOrderDTO;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

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



    /**
     * 运营的注册
     *
     * @author Sandy
     * create at 16/10/08 下午4:05
     */
    public static void liYingReg(Context context, LiYingRegDTO params, CallBack<BaseResult> callback) {
        params.setToken(RSAUtils.strByEncryptionLiYing(context, PublicData.getInstance().deviceId, true));
        params.setPushmode("2");
        params.setPushswitch("1");
        BaseCallBack<BaseResult> baseCallBack = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
        post(context, getUrl("cttx/register"), params, baseCallBack);
    }


    /**
     * 获取红包信息
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void getBonusInfo(Context context, BonusDTO dto, CallBack<BonusResult> callback) {
        BaseCallBack<BonusResult> result = new BaseCallBack<BonusResult>(
                context, callback, BonusResult.class);
        post(context, getDownUrl("shareRanking"), dto, result);
    }


    /**
     * 创建油卡充值订单
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void createOrder(Context context, RechargeDTO dto, CallBack<RechargeResult> callback) {
        BaseCallBack<RechargeResult> result = new BaseCallBack<RechargeResult>(
                context, callback, RechargeResult.class);
        post(context, getUrl("addOil/createOrder"), dto, result);
    }

    /**
     * 查询充值订单
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void queryOrders(Context context, RechargeOrderDTO dto, CallBack<RechargeOrderResult> callback) {
        BaseCallBack<RechargeOrderResult> result = new BaseCallBack<RechargeOrderResult>(
                context, callback, RechargeOrderResult.class);
//        post(context, "http://139.196.183.121:8081/addOil/findAllOrder", dto, result);
        post(context, getUrl("addOil/findAllOrder"), dto, result);
    }

    /**
     * 取消充值订单
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void cancelOrder(Context context, CancelRechargeOrderDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
//        post(context, "http://139.196.183.121:8081/addOil/cancleOrder", dto, result);
        post(context, getUrl("addOil/cancleOrder"), dto, result);

    }

    /**
     * 充值订单详情
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void queryOrderDetail(Context context, CancelRechargeOrderDTO dto, CallBack<RechargeOrderDetailResult> callback) {
        BaseCallBack<RechargeOrderDetailResult> result = new BaseCallBack<RechargeOrderDetailResult>(
                context, callback, RechargeOrderDetailResult.class);
        post(context, "http://139.196.183.121:8081/addOil/queryOrder", dto, result);
        post(context, getUrl("addOil/queryOrder"), dto, result);
    }



    /**
     * 百日活动报名
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void commitHundredPlan(Context context, HundredPlanDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
//        post(context, "http://139.196.183.121:8081/february/signUpActivity", dto, result);
        post(context, getUrl("february/signUpActivity"), dto, result);

    }

    /**
     * 获取优惠券list
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void getCouponList(Context context, CouponDTO dto, CallBack<CouponResult> callback) {
        BaseCallBack<CouponResult> result = new BaseCallBack<CouponResult>(
                context, callback, CouponResult.class);
//        post(context, "http://139.196.183.121:8081/february/usrCouponInfo", dto, result);
        post(context, getUrl("february/usrCouponInfo"), dto, result);
    }


    /**
     * 获取活动车辆
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void getActivityCar(Context context, ActivityCarDTO dto, CallBack<ActivityCarResult> callback) {
        BaseCallBack<ActivityCarResult> result = new BaseCallBack<ActivityCarResult>(
                context, callback, ActivityCarResult.class);
//        post(context, "http://139.196.183.121:8081/february/carCheckActivity", dto, result);
        post(context, getUrl("february/carCheckActivity"), dto, result);
    }

    /**
     * 附近司机信息
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
    public static void getDaiJiaToken(Context context, DaiJiaDTO dto, CallBack<DJTokenResult> callback) {
        BaseCallBack<DJTokenResult> result = new BaseCallBack<DJTokenResult>(
                context, callback, DJTokenResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/nearByInfo", dto,result);
        post(context, getUrl("daijia/nearByInfo"), dto, result);
    }

    /**
     * 呼叫代驾
     *
     * @param context
     * @param dto
     * @param callback
     */
    public static void huJiaoDaiJia(Context context, DaiJiaCreateDTO dto, CallBack<DaiJiaCreateResult> callback) {
        BaseCallBack<DaiJiaCreateResult> result = new BaseCallBack<DaiJiaCreateResult>(
                context, callback, DaiJiaCreateResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/addOrder", dto,result);
        post(context, getUrl("daijia/addOrder"), dto, result);
    }


    /**
     * 获取报名数量
     *
     * @author Sandy
     * create at 16/12/27 下午1:56
     */
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

    /**
     * 取消代驾订单
     *
     * @author zyb love lmx 10000 years
     * <p>
     * <p>
     * *  *   *  *
     * *      *      *
     * *             *
     * *           *
     * *     *
     * *
     * <p>
     * <p>
     * create at 17/3/2 下午3:47
     */
    public static void cancelDaiJiaOrderDetail(Context context, DaiJiaOrderDetailDTO dto, CallBack<BaseResult> callback) {
        BaseCallBack<BaseResult> result = new BaseCallBack<BaseResult>(
                context, callback, BaseResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/cancelOrder", dto,result);
        post(context, getUrl("daijia/cancelOrder"), dto, result);

    }

    /**
     * 获取代驾订单列表
     *
     * @param context
     * @param dto
     * @param callback
     */
    public static void getDaiJiaOrderList(Context context, DaiJiaOrderListDTO dto, CallBack<DaiJiaOrderListResult> callback) {
        BaseCallBack<DaiJiaOrderListResult> result = new BaseCallBack<DaiJiaOrderListResult>(
                context, callback, DaiJiaOrderListResult.class);
//        post(context, "http://139.196.183.121:8081/daijia/findAll", dto,result);
        post(context, getUrl("daijia/findAll"), dto, result);
    }

    /**
     * 获取消息类别列表
     *
     * @param context
     * @param dto
     * @param callback
     */
    public static void getMsgTypeList(Context context, BaseDTO dto, CallBack<MessageTypeResult> callback) {
        BaseCallBack<MessageTypeResult> result = new BaseCallBack<MessageTypeResult>(
                context, callback, MessageTypeResult.class);
//        post(context, "http://139.196.183.121:8081/message/findAll", dto,result);
        post(context, getUrl("message/findAll"), dto, result);
    }

    /**
     * 获取消息详情列表
     *
     * @param context
     * @param dto
     * @param callback
     */
    public static void getMsgList(Context context, MegDTO dto, CallBack<MessageResult> callback) {
        BaseCallBack<MessageResult> result = new BaseCallBack<MessageResult>(
                context, callback, MessageResult.class);
//        post(context, "http://139.196.183.121:8081/message/findMessageDetailByMessageId", dto,result);
        post(context, getUrl("message/findMessageDetailByMessageId"), dto, result);
    }

    /**
     * 获取未读消息数量
     *
     * @param context
     * @param dto
     * @param callback
     */
    public static void getUnReadMsgCount(Context context, BaseDTO dto, CallBack<MessageCountResult> callback) {
        BaseCallBack<MessageCountResult> result = new BaseCallBack<MessageCountResult>(
                context, callback, MessageCountResult.class);
//        post(context, "http://139.196.183.121:8081/message/countMessageDetail", dto,result);
        post(context, getUrl("message/countMessageDetail"), dto, result);
    }



    /**
     * 上传行驶证照片
     * @param context
     * @param file
     * @param callback
 */
    public static void uploadDrivingImg(Context context, File file, CallBack<DrivingOcrResult> callback) {
        OcrCallBack<DrivingOcrResult> result = new OcrCallBack<DrivingOcrResult>(
                context, callback, DrivingOcrResult.class);
        Config.OCR_TYPE = 0;
        post(context, "http://liyingtong.com:8080/PIM_DRIVING/SrvXMLAPI", file, result);
    }



}
