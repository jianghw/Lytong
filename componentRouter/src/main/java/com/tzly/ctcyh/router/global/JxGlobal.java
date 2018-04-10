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
        public static final String recharge_coupon_bean_extra = "recharge_coupon_bean_extra";

        public static final String violation_pay_bean_extra = "violation_pay_bean_extra";
    }
}
