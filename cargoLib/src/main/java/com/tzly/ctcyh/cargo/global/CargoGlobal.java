package com.tzly.ctcyh.cargo.global;

/**
 * Created by jianghw on 2017/7/5.
 * Description: 1x00--3
 * Update by:
 * Update day:
 */

public final class CargoGlobal {

    public static final class requestCode {
        public static final int coupon_list_choice = 1210;
        public static final int pay_type_choice = 1220;
        public static final int pay_html_price = 1230;
    }

    public static final class resultCode {
        public static final int coupon_used = 2220;
        public static final int coupon_unused = 2221;
        public static int web_pay_succeed = 2230;
        public static int web_pay_error = 2231;
        public static int pay_type_back = 2240;
    }

    public static final class putExtra {
        public static final String coupon_list_type = "coupon_list_type";
        public static final String coupon_list_bean = "coupon_list_bean";
        public static final String pay_type_order = "pay_type_order";
        public static final String web_title_extra = "pay_type_order";
        public static final String web_url_extra = "web_url_extra";
        public static final String web_orderId_extra = "web_orderId_extra";
        public static final String web_pay_type_extra = "web_pay_type_extra";
        public static final String license_score_extra = "license_score_extra";
        public static final String oil_share_banner_extra = "oil_share_banner_extra";
        public static final String oil_share_img_extra = "oil_share_img_extra";
        public static final String oil_share_json_extra = "oil_share_json_extra";
    }

    public static final class Response {
        public static final String bank_succeed = "000000";
        public static final int base_succeed = 2000;
    }

    public static final class Host {
        public static final String coupon_list_host = "coupon_list_host";
        public static final String pay_type_host = "pay_type_host";
    }
}
