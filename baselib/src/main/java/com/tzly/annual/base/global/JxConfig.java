package com.tzly.annual.base.global;

import android.support.v4.util.SimpleArrayMap;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */

public final class JxConfig {

    private SimpleArrayMap<Integer, String> simpleArrayMap;

    private static class Singleton {
        public static final JxConfig INSTANCE = new JxConfig();
    }

    public static JxConfig getInstance() {
        return Singleton.INSTANCE;
    }

    private JxConfig() {
        if (simpleArrayMap == null)
            simpleArrayMap = new SimpleArrayMap<>();

        initHashMapData();
    }

    private void initHashMapData() {
        simpleArrayMap.put(1, "home_unblocked_banner_localActivity");//首页_畅_Banner_活动百日无违章
        simpleArrayMap.put(2, "home_unblocked_rechargeOilCard");//首页_畅_加油充值_油卡充值
        simpleArrayMap.put(3, "oilCard_oilDiscount_oilDiscountMap");//油卡充值_加油优惠_加油优惠地图
        simpleArrayMap.put(4, "home_unblocked_vehicleInspection_inspectionMap");//首页_畅_年检_年检地图
        simpleArrayMap.put(5, "inspection_mapExemption_inspectionMap");//年检_免检领标_免检地图
        simpleArrayMap.put(6, "inspection_mapInspection_location_inspection_map");//年检_年检站点_年检站点地图
        simpleArrayMap.put(7, "home_unblocked_violationInquiry");//首页_畅_驾驶证查分_违章查询
        simpleArrayMap.put(8, "violationInquiry_inquiry");//驾驶证查分_查询
        simpleArrayMap.put(9, "home_unblocked_car_washCar_washWeb");// 首页_畅_洗车_洗车页面
        simpleArrayMap.put(11, "home_unblocked_query_car");// 添加车辆卡片点击量
        simpleArrayMap.put(12, "queryCar_peccancy_inquiries_illegal");//违章查询_车辆违法查询_车辆违法查询页面
        simpleArrayMap.put(13, "queryCar_peccancy_submit");//车辆违法查询
        simpleArrayMap.put(14, "queryCar_scanBar_codeScan");//违章查询_扫罚单查找_扫罚单页面
        simpleArrayMap.put(15, "home_unblocked_message_messageWap");//首页_畅_消息_消息页面
        simpleArrayMap.put(16, "home_unblocked_message_fty");//消息页面浏览量
        simpleArrayMap.put(17, "home_unblocked_scanBar_code2_scan");//首页_畅_扫一扫
        simpleArrayMap.put(18, "home_unblocked");//首页“畅”的点击量

        simpleArrayMap.put(19, "home_discount_discount");//首页_优惠_优惠页面
        simpleArrayMap.put(20, "discount_a_discountBanner");// 优惠页面_优惠Banner
        simpleArrayMap.put(21, "discount_oilCard_discountOil_discountMap");//优惠_油卡充值_加油优惠_加油优惠地图
        simpleArrayMap.put(22, "oilCardOil_discountOil_discountMap");//进入“油卡充值”页面浏览量
        simpleArrayMap.put(23, "discount_maintenance_maintenanceA");//优惠页面_汽车嗨修_汽车嗨修页面
        simpleArrayMap.put(24, "maintenanceA_bannera");//汽车嗨修_Bannera_供应商H5页面
        simpleArrayMap.put(25, "discount_driving_instead_drivingInsteadA");//优惠页面_代驾_代驾页面
        simpleArrayMap.put(26, "driving_insteadA_call_driver");//代驾页面_呼叫代驾
        simpleArrayMap.put(27, "discount_washCar_washCarA");//优惠页面_洗车美容_洗车页面
        simpleArrayMap.put(28, "discount_driverSchool_signUp");//优惠_驾校报名_报名页面
        simpleArrayMap.put(29, "signUp_immediatelySignUp_order");//报名页面_立即报名_订单页面
        simpleArrayMap.put(30, "order_payment_share");// 订单_支付_分享
        simpleArrayMap.put(31, "share_shareA");// 分享_朋友圈或好友
        simpleArrayMap.put(32, "shareA_shareNumber");//分享_分享数量
        simpleArrayMap.put(33, "shareNumber_money");//分享数据_返还现金

        simpleArrayMap.put(34, "home_unblocked_guzhi");//首页_畅_爱车估值
        simpleArrayMap.put(35, "home_unblocked_IDL");//首页_畅_国际驾照
        simpleArrayMap.put(36, "discount_gouchedaikuan");//优惠_购车贷款
        simpleArrayMap.put(37, "discount_kemuqianghua");//优惠_科目强化
        simpleArrayMap.put(38, "discount_peilian");//优惠_陪练
    }

    public String getUMengEventID(int position) {
        if (simpleArrayMap != null && simpleArrayMap.size() >= position) {
            return simpleArrayMap.get(position);
        }
        return "unKnow_tab_" + position;
    }

}
