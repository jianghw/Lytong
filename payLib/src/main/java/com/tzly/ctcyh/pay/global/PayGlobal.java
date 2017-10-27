package com.tzly.ctcyh.pay.global;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */

public final class PayGlobal {

    public static final class requestCode {
        public static final int coupon_list_choice = 1000;
        public static final int pay_type_price = 900;
    }

    public static final class resultCode {
        public static final int coupon_used = 2000;
        public static final int coupon_unused = 4000;
    }

    public static final class putExtra {
        public static final String coupon_list_type = "coupon_list_type";
        public static final String coupon_list_bean = "coupon_list_bean";
        public static final String pay_type_order = "pay_type_order";
        public static final String web_title_extra = "pay_type_order";
        public static final String web_url_extra = "web_url_extra";
        public static final String web_orderId_extra = "web_orderId_extra";
        public static final String web_pay_type_extra="web_pay_type_extra";
    }

    public static final class Host {
        public static final String pay_host = "pay_host";
        public static final String coupon_list_host = "coupon_list_host";
        public static final String pay_type_host = "pay_type_host";
        public static final String html_5_host = "html_5_host";
    }

    public static final class Scheme {
        public static final String pay_scheme = "pay_scheme";
        public static final String coupon_list_scheme = "coupon_list_scheme";
        public static final String pay_type_scheme = "pay_type_scheme";
    }
}
