package com.tzly.ctcyh.router.global;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */

public final class JxGlobal {

    public static final class requestCode {
        public static final int violation_query_plate = 1000;
        public static final int add_car_aty = 1001;
        public static final int fahrschule_order_num_web = 1003;
        public static final int recharge_coupon_list = 1004;
    }

    public static final class resultCode {
        public static final int common_list_fty = 2000;
        public static final int recharge_coupon_choice = 2004;
        public static final int recharge_coupon_unchoice = 2005;
    }

    public static final class putExtra {
        public static final String common_extra = "common_extra";
        public static final String common_list_extra = "common_list_extra";
        public static final String share_position_extra = "share_position_extra";
        public static final String fahrschule_position_extra = "fahrschule_position_extra";
        public static final String recharge_coupon_extra = "recharge_coupon_extra";
        public static final String recharge_coupon_bean_extra = "recharge_coupon_bean_extra";

        public static final String violation_pay_bean_extra = "violation_pay_bean_extra";
        public static final String map_type_extra = "map_type_extra";
    }

    public static final class MapType {
        public static final int annual_inspection_map = -1;
        public static final int annual_oil_map = -2;
        /**
         * 免检领标
         */
        public static final int annual_led_service = 2;
        /**
         * 年检站点
         */
        public static final int annual_site_service = 1;
        /**
         * 外牌代办点
         */
        public static final int annual_agent_service = 3;

        public static final int annual_0_service = 1;
        public static final int annual_92_service = 5;
        public static final int annual_95_service = 6;
    }
}