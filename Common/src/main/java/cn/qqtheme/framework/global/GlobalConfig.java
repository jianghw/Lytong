package cn.qqtheme.framework.global;

import android.support.v4.util.SimpleArrayMap;

import com.umeng.analytics.MobclickAgent;

import cn.qqtheme.framework.util.ContextUtils;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */

public final class GlobalConfig {

    private SimpleArrayMap<Integer, String> simpleArrayMap;

    private static class Singleton {
        public static final GlobalConfig INSTANCE = new GlobalConfig();
    }

    public static GlobalConfig getInstance() {
        return Singleton.INSTANCE;
    }

    private GlobalConfig() {
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
    }

    public String getgetUMengEventID(int position) {
        if (simpleArrayMap != null && simpleArrayMap.size() >= position) {
            return simpleArrayMap.get(position);
        }
        return "unKnow_tab_" + position;
    }

    /**
     * 自定义友盟事件
     */
    public void eventIdByUMeng(int position) {
        MobclickAgent.onEvent(ContextUtils.getContext(), getgetUMengEventID(position));
    }
}
