package com.zantong.mobilecttx.application;

import com.zantong.mobilecttx.BuildConfig;
import com.tzly.ctcyh.router.util.Tools;

import java.util.HashMap;

/**
 * 全局配置文件以及常量
 *
 * @author Sandy
 *         create at 16/6/1 下午6:05
 */
public class Config {

    public static final int PAGE_SIZE = 20;
    /**
     * 退出应用标记
     */
    public static final String TAG_EXIT_APP = "exit_app";
    /**
     * 错误信息
     */
    public static final String TAG_ERROR = "tag_error";
    /**
     * 消息ID
     */
    public static final String MESSAGE_ID = "message_id";
    /**
     * OCR扫描类型
     * 0 行驶证
     * 1 驾照
     */
    public static int OCR_TYPE = 0;
    /**
     * 加油支付类型
     * 0 畅通卡
     * 1 工行其他卡
     */
    public static int PAY_TYPE = 0;

    public static final String OK = "000000";
    public static final String RESULT_OK = "2000";
    public static final String ERROR_PARSER = "1001";
    public static final String ERROR_IO = "1002";
    public static final String ERROR_NET = "1003";
    public static final String ERROR_PARSER_MSG = "服务器繁忙,请您稍后再试";
    public static final String ERROR_IO_MSG = "系统繁忙，请您稍后再试";
    public static final String ERROR_NET_MSG = "请检查您的网络设置";

    public static final String HOME_CAR_WASH_URL = BuildConfig.base_url+"h5/car_wash/car_wash.html";

    public static final String HUNDRED_PLAN_HOME = BuildConfig.base_url + "h5/build/index.html";
    public static final String HUNDRED_PLAN_DEADLINE = BuildConfig.base_url + "h5/build/pages/deadline.html";

    public static String getErrMsg(String errorCode) {
        String errorMsg = "出错了...请稍后重试";
        HashMap<String, String> errors = new HashMap<>();
        errors.put("000000", "成功");
        errors.put("CFB001", "推荐人不存在");
        errors.put("CFB002", "不能重复获取赠险");
        errors.put("CFB003", "档案号不存在");
        errors.put("CFB004", "该手机号已注册");
        errors.put("CFB005", "该用户名不存在");
        errors.put("CFB006", "密码不正确");
        errors.put("CFB007", "该手机号未注册");
        errors.put("CFB008", "重试次数超限");
        errors.put("CFB009", "该车牌号已存在");
        errors.put("P601", "非牡丹卡用户不能办理该项业务");
        errors.put("P602", "你的驾驶证已经失效,请通过公安交通管理部门查询");
        errors.put("P603", "您的牡丹交通卡状态不正常，不能办理此项业务");
        errors.put("P604", "您所输入违章记录编号不存在，请通过公安交通管理部门查询");
        errors.put("P605", "您输入的车牌号码不存在违章记录，请通过公安交通管理部门查询");
        errors.put("P606", "该笔违章已处理");
        errors.put("P607", "该笔违章已在交巡警总队接受处理，请通过公安交通管理部门查询");
        errors.put("P608", "您所持的驾驶证不能缴付运营车辆交通罚款");
        errors.put("P609", "您所持的驾驶证的准驾类型与电子警察记录的违章车辆类型不符，不能缴付该笔交通违章罚款");
        errors.put("P610", "本次违章处理后您的驾驶证记分将达到或超过12分，根据交巡警总队规定，您必须到上海市公安局交巡警总队机动支队大柏树处理点接受违章处理");
        errors.put("P611", "您所持的驾驶证已失效，不能进行交通违章罚款缴付。您所持的驾照失效原因，可通过公安交通管理部门查询");
        errors.put("P612", "交易未能处理，请用违章记录编号查询缴费结果");
        errors.put("P613", "交易未能处理");
        errors.put("P614", "非法交易代码");
        errors.put("P615", "通讯错误");
        errors.put("P616", "违法记录入库失败");
        errors.put("P617", "该档案编号没有对应的处罚决定书记录");
        errors.put("P618", "游标关闭失败");
        errors.put("P619", "无此交易代码");
        errors.put("P620", "您所输入通知书编号不存在，请通过公安交通管理部门查询");
        errors.put("P621", "您所输入处罚决定书不存在，请通过公安交通管理部门查询");
        errors.put("P622", "查询批量控制表无记录");
        errors.put("P623", "查询批量控制表出错");
        errors.put("P624", "正在进行处理，不能缴费");
        errors.put("P625", "查询驾驶员几本信息出错");
        errors.put("P626", "该笔不是你的违法记录，如有疑问请致电56317000交警总队查询");
        errors.put("P627", "牡丹交通卡最多可缴纳2辆不同号牌的机动车交通违法罚款，如需对可缴费罚款车辆重新设定，请选择人工服务");
        errors.put("P628", "查询车辆表失败");
        errors.put("P629", "车牌修改好后12小时后生效");
        errors.put("P630", "指定档案号不存在");
        errors.put("P631", "违章号长度不正确");
        errors.put("P632", "请使用处罚决定书的编号进行缴纳");
        errors.put("P633", "查询缴费明细表失败");
        errors.put("P634", "该笔缴费处于当日未知状态，请明日再试");
        errors.put("P635", "超过每日可缴决定书笔数");
        errors.put("P636", "您将处于超分状态，系统不能处理本起交通违法。请您至相关交警支（大）队处理");
        errors.put("P637", "该笔违法信息处理机关在参数表中不存在");
        errors.put("P638", "因准驾车型不符，系统不能处理本起交通违法，请您至相关交警支（大）队处理");
        errors.put("P639", "驾证初领证日期晚与违法日起，不能处理");
        errors.put("P640", "该罚单为异地驾驶员接受交警处罚所开罚单，请通过跨省异地交通违法罚款项目交纳");
        errors.put("P666", "交易未能处理");
        errors.put("P670", "卡号日累计支付金额超限");
        errors.put("P673", "信用卡余额不足");
        errors.put("P699", "未知错误");
        if (!Tools.isStrEmpty(errors.get(errorCode))) {
            errorMsg = errors.get(errorCode);
        }
        return errorMsg;
    }

    public static String getUMengID(int id) {
        HashMap<Integer, String> umengIDs = new HashMap<>();
        umengIDs.put(0, "root");//首页
        umengIDs.put(1, "carvioltation_root");//首页-车辆违章页面
        umengIDs.put(2, "checkviolation_root");//首页-违章查询页面
        umengIDs.put(3, "card_root");//首页-畅通卡界面
        umengIDs.put(4, "washcar_root");//首页-洗车界面
        umengIDs.put(5, "yearcheck_root");//首页-年检界面
        umengIDs.put(6, "addoil_root");//首页-加油充值界面
        umengIDs.put(7, "behalfdrive_root");//首页-代驾界面
        umengIDs.put(8, "activity_root");//首页-活动界面
        umengIDs.put(9, "usercenter_root");//首页-我的界面
        umengIDs.put(10, "banner_root");//首页-banner网页
        umengIDs.put(11, "payviolation");//违章缴费
        umengIDs.put(12, "applycard");//申办畅通卡
        umengIDs.put(13, "bundlecard");//绑定畅通卡
        umengIDs.put(14, "yearpiolt");//年检导航
        umengIDs.put(15, "addoilpay");//加油充值按钮
        umengIDs.put(16, "addoilpiolt");//加油导航
        umengIDs.put(17, "behalfdriveaction");//呼叫代驾
        umengIDs.put(18, "behalfdrivepoilt");//代驾地址选择
        umengIDs.put(19, "activitysignup");//活动报名界面
        umengIDs.put(20, "activitysignupaction");//活动报名按钮
        umengIDs.put(21, "activityshare");//活动分享             ??
        umengIDs.put(22, "activitymain");//活动界面
        umengIDs.put(23, "headerimage");//头像
        umengIDs.put(24, "message_my");//我的-h5消息界面
        umengIDs.put(25, "card_my");//我的-畅通卡界面
        umengIDs.put(26, "set_my");//我的-设置界面
        umengIDs.put(27, "coupns_my");//我的-优惠券界面
        umengIDs.put(28, "managecar_my");//我的-车辆管理界面
        umengIDs.put(29, "insuranceorder_my");//我的-保险订单界面
        umengIDs.put(30, "addoilorder_my");//我的-加油订单界面
        umengIDs.put(31, "share_my");//我的-推荐领积分界面
        umengIDs.put(32, "question_my");//我的-常见问题界面
        umengIDs.put(33, "feedback_my");//我的-问题反馈界面
        umengIDs.put(34, "vio_history");//我的缴费记录
        umengIDs.put(35, "vio_driverliscence");//驾驶证查分
        return umengIDs.get(id);
    }

}
