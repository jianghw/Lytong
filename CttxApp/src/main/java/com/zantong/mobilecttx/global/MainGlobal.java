package com.zantong.mobilecttx.global;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */

public final class MainGlobal {

    public static final class requestCode {
        public static final int coupon_list_choice = 1000;
        public static final int pay_type_price = 900;
    }

    public static final class resultCode {
        public static final int coupon_used = 2000;
        public static final int coupon_unused = 4000;
        public static int web_pay_succeed = 2001;
        public static int web_pay_error = 4001;
    }

    public static final class putExtra {
        public static final String home_position_extra = "home_position_extra";
    }

    public static final class Host {
        public static final String home_host = "home_host";
        public static final String guide_host = "guide_host";
        public static final String html_5_host = "html_5_host";
    }

    public static final class Scheme {
        public static final String main_scheme = "main_scheme";
        public static final String coupon_list_scheme = "coupon_list_scheme";
        public static final String pay_type_scheme = "pay_type_scheme";
    }
}
